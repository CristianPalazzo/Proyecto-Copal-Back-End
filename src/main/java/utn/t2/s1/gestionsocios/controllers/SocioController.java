package utn.t2.s1.gestionsocios.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utn.t2.s1.gestionsocios.converters.SocioConverter;
import utn.t2.s1.gestionsocios.dtos.SocioDTO;
import utn.t2.s1.gestionsocios.excepciones.CategoriaException;
import utn.t2.s1.gestionsocios.modelos.*;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.servicios.CategoriaServicio;
import utn.t2.s1.gestionsocios.servicios.LogoServicio;
import utn.t2.s1.gestionsocios.servicios.SocioServicio;
import utn.t2.s1.gestionsocios.servicios.TipoSocioServicio;
import java.io.File;
import java.util.Optional;
import java.util.Set;

@Tag(name = "Operaciones para los socios", description = "Api para realizar las operaciones de alta, baja y modificacion de un socio")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@RequestMapping("/socios")
@Validated
@CrossOrigin
public class SocioController {

    @Autowired
    SocioServicio servicio;
    @Autowired
    CategoriaServicio categoriaServicio;
    @Autowired
    TipoSocioServicio tipoServicio;
    @Autowired
    SocioConverter socioConverter;
    @Autowired
    LogoServicio logoServicio;


    @GetMapping()
    @Operation(summary = "Retorna todos los socios de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socios encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Socio.class)) }),
    })
    public ResponseEntity<Page<Socio>> verSocios(Pageable pageable){
        return new ResponseEntity<>(servicio.buscarTodos(pageable), HttpStatus.OK);
    }



    @GetMapping(value = {"/search", "/search/"})
    @Operation(summary = "Retorna los socios filtrados y buscados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socios Filtrados encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Socio.class)) }),
    })
    public ResponseEntity<Page<Socio>> verSociosFiltradoBuscado(Pageable pageable, @RequestParam(required = false) String denominacion, @RequestParam(required = false) String tipo){

        Page<Socio> socios = servicio.buscarPorDenominacionYFiltrado(pageable, denominacion, tipo);
        return new ResponseEntity<>(socios, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    @Operation(summary = "Retorna el socio correspondiente al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio encontrado" ,content = { @Content(mediaType = "application/json",schema = @Schema( implementation = Socio.class)) }),
            @ApiResponse(responseCode = "404", description = "El socio no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<Socio> verSocio(@PathVariable Long id) {

        if (servicio.buscarPorId(id) == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Socio _socio = servicio.buscarPorId(id);
        return new ResponseEntity<>(_socio, HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Inserta un socio en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Socio creado" ,content = { @Content(mediaType = "application/json",schema = @Schema( implementation = Socio.class)) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "La categoria no fue encontrada",content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El tipo no fue encontrada",content = { @Content(schema = @Schema()) }),

    })
    public ResponseEntity<Object> agregarSocio(@RequestParam(value = "logo", required = false) MultipartFile logo, @Valid @RequestParam("socio") String socioString) {
        //TODO DTO socio
        File file = null;
        try {
            // Guardar el logo si existe y obtener la url
            String url = null;
            if (logo != null && !logo.isEmpty()) {
                url = logoServicio.save(logo);
                // Encontrar el índice de "logo/"
                int i = url.indexOf("logo/");
                // Extraer la subcadena desde el índice i + 5 (para saltar el "logo/")
                String sub = url.substring(i + 5);
                // Crear un archivo con la ruta del logo
                file = new File("./uploads/" + sub);
            } else {
                // Si no existe, asignar la url por defecto
                url = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Placeholder_view_vector.svg/310px-Placeholder_view_vector.svg.png";
            }
            // Convertir el socioString a un SocioDTO
            SocioDTO socioDTO = convertirSocioString(socioString);
            // Asignar la url del logo al SocioDTO
            socioDTO.setLogo(url);
            // Validar y convertir el SocioDTO a un Socio
            Socio socio = validarYConvertirSocioDTO(socioDTO);
            // Buscar si existe un socio con la misma denominación
            Optional<Socio> socioOpcional = servicio.buscarPorNombre(socioDTO.getDenominacion());
            // Si no existe, agregar el socio y devolver el resultado
            if (socioOpcional.isEmpty()) {
                return new ResponseEntity<>(servicio.agregar(socio), HttpStatus.CREATED);
            }
            // Si existe, borrar el logo y devolver un error
            borrarLogo(file);
            return new ResponseEntity<>("La denominacion de socio ya existe", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Si ocurre alguna excepción, borrar el logo y devolver el mensaje de error
            borrarLogo(file);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // Método auxiliar para convertir el socioString a un SocioDTO
    private SocioDTO convertirSocioString(String socioString) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(socioString, SocioDTO.class);
    }

    // Método auxiliar para validar y convertir el SocioDTO a un Socio
    private Socio validarYConvertirSocioDTO(SocioDTO socioDTO) throws Exception {
        // Obtener las categorías y el tipo del SocioDTO
        Set<Categoria> categorias = categoriaServicio.stringSetToCategoriaSet(socioDTO.getCategorias());
        TipoSocio tipo = tipoServicio.buscarPorNombre(socioDTO.getTipo());
        // Si alguna categoría o el tipo no existe, lanzar una excepción
        if (categorias == null || tipo == null) {
            throw new Exception("Una de las categorias o el tipo no existe en la base de datos");
        }
        // Convertir el SocioDTO a un Socio con el estado activo
        Socio socio = socioConverter.toSocio(socioDTO, categorias, tipo);
        socio.setEstado(Estado.ACTIVO);
        return socio;
    }

    // Método auxiliar para borrar el logo si existe
    private void borrarLogo(File file) {
        if (file != null) {
            boolean borrado = file.delete();
        }
    }


    @PutMapping("/{idSocio}")
    @Operation(summary = "Modifica un socio en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Socio modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El socio no fue encontrada",content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "La categoria no fue encontrada",content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El tipo no fue encontrada",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<Object> modificarSocio(@Valid @PathVariable Long idSocio, @RequestParam(value = "logo", required = false) MultipartFile logo, @Valid @RequestParam("socio") String socioString) {

        try {
            // Convertir el string socio a un objeto SocioDTO
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            SocioDTO socioDTO = objectMapper.readValue(socioString, SocioDTO.class);

            // Obtener el logo actual del socio
            String url = servicio.buscarPorId(idSocio).getLogo();

            // Si se envió un nuevo logo, borrar el anterior y guardar el nuevo
            if (logo != null && !logo.isEmpty()) {
                logoServicio.deletePorSocio(idSocio);
                url = logoServicio.save(logo);
            }

            // Actualizar el logo del socioDTO
            socioDTO.setLogo(url);

            // Buscar el socio por id
            Socio socio = servicio.buscarPorId(idSocio);
            if (socio == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            // Convertir el set de categorias y el tipo de socio a objetos
            Set<Categoria> categorias = categoriaServicio.stringSetToCategoriaSet(socioDTO.getCategorias());
            TipoSocio tipo = tipoServicio.buscarPorNombre(socioDTO.getTipo());

            // Validar que las categorias y el tipo existan en la base de datos
            if (categorias == null || tipo == null) {
                return new ResponseEntity<>("Una de las categorias o el tipo no existe en la base de datos", HttpStatus.NOT_FOUND);
            }

            // Convertir el socioDTO a un objeto Socio
            Socio _socio = socioConverter.toSocio(socioDTO, categorias, tipo);
            _socio.setEstado(Estado.ACTIVO);

            // Modificar el socio en la base de datos
            servicio.modificar(idSocio, _socio);

            // Retornar el socio modificado con el código 201 (Created)
            return new ResponseEntity<>(_socio, HttpStatus.CREATED);

        } catch (ConstraintViolationException e) {
            // Manejar las violaciones de las restricciones de validación
            String mensaje = "";
            for (ConstraintViolation violation : e.getConstraintViolations()) {
                mensaje += violation.getPropertyPath() + " " + violation.getMessage() + "\n";
            }
            return new ResponseEntity<>(mensaje, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // Manejar cualquier otra excepción
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un socio en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio eliminado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El socio no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<String> eliminarSocio(@PathVariable Long id){
        servicio.borrar(id);
        return new ResponseEntity<>("Socio eliminado", HttpStatus.OK);
    }


}
