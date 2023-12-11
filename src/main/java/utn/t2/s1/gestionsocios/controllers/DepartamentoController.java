package utn.t2.s1.gestionsocios.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import utn.t2.s1.gestionsocios.dtos.AutoridadDTO;
import utn.t2.s1.gestionsocios.dtos.DepartamentoDTO;
import utn.t2.s1.gestionsocios.excepciones.DepartamentoException;
import utn.t2.s1.gestionsocios.modelos.*;
import utn.t2.s1.gestionsocios.servicios.AutoridadDepartamentoServicio;
import utn.t2.s1.gestionsocios.servicios.DepartamentoServicio;
import org.springframework.web.multipart.MultipartFile;
import utn.t2.s1.gestionsocios.servicios.LogoServicio;


import java.io.File;

@Tag(name = "Operaciones de Departamento", description = "Api para realizar las operaciones de Departamento")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@Validated
@RequestMapping("/departamento")
@CrossOrigin
public class DepartamentoController {

    @Autowired
    DepartamentoServicio departamentoServicio;

    @Autowired
    AutoridadDepartamentoServicio autoridadDepartamentoServicio;

    @Autowired
    LogoServicio logoServicio;


    @GetMapping()
    @Operation(summary = "Retorna todos los Departamento de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departamentos encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Socio.class)) }),
    })
    public ResponseEntity<Page<Departamento>> verDepartamentos(Pageable pageable){
        Page<Departamento> departamentos = departamentoServicio.traerDepartamentos(pageable);
        return new ResponseEntity<>(departamentos , HttpStatus.OK);
    }


    @GetMapping("/{idDepartamento}")
    @Operation(summary = "Retorna un departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departamento encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Socio.class))})
    })
    public ResponseEntity<Departamento> verDepartamentoPorId(@PathVariable Long idDepartamento) throws DepartamentoException {
        Departamento departamento = departamentoServicio.buscarPorId(idDepartamento);
        return new ResponseEntity<>(departamento , HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Agrega un departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departamento encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "El Departamento no fue encontrado", content = {@Content(schema = @Schema())}),
    })
    public ResponseEntity<?> agregarDepartamento( @RequestParam(value = "logo", required = false) MultipartFile logo,@Valid @RequestParam("departamento") String departamentoString){

//        public ResponseEntity<?> agregarDepartamento( @RequestParam("logo") MultipartFile logo,@Valid @RequestParam("departamento") String departamentoDTO)


        File file = null;

        try {
            String url = null;
            if( logo != null && !logo.isEmpty() ){ // si hay un logo, entonces hace esto
                //return new ResponseEntity<>("Logo vacio", HttpStatus.UNPROCESSABLE_ENTITY);
                url = logoServicio.save(logo);
                // Encontrar el índice de "logo/"
                int i = url.indexOf("logo/");
                // Extraer la subcadena desde el índice i + 5 (para saltar el "logo/")
                String sub = url.substring(i + 5);

                file = new File("./uploads/" + sub);

            }else {
                    // Si no existe, asignar la url por defecto
                    url = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Placeholder_view_vector.svg/310px-Placeholder_view_vector.svg.png";
            }



            ObjectMapper objectMapper = new ObjectMapper();
            DepartamentoDTO departamentoDTO = objectMapper.readValue(departamentoString, DepartamentoDTO.class);

            departamentoDTO.setLogo(url);


            Departamento departamento = departamentoServicio.agregar(departamentoDTO);
            return new ResponseEntity<>(departamento, HttpStatus.CREATED);

        }catch (ConstraintViolationException e) {
            boolean borrado = file.delete();
            String mensaje = "";
            // Recorremos el conjunto de violaciones y mostramos solo el mensaje
            for (ConstraintViolation violation : e.getConstraintViolations()) {
                mensaje += violation.getPropertyPath() + " " + violation.getMessage() + "\n";
            }
            return new ResponseEntity<>(mensaje, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (NullPointerException e) {
            if (file != null) {
                boolean borrado = file.delete();
            }
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            if (file != null) {
                boolean borrado = file.delete();
            }
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }

















//        Optional<Departamento> _opcionalDepartamento = departamentoServicio.buscarPorNombre(departamentoDTO.getNombre());
//
//        if (_opcionalDepartamento.isEmpty()) {
//            return new ResponseEntity<>(departamentoServicio.agregar(departamentoDTO), HttpStatus.CREATED);
//        }

  //      return new ResponseEntity<>("El nombre de departamento ya existe", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/{idDepartamento}")
    @Operation(summary = "Eliminar Departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Departamento eliminado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
    })
    public ResponseEntity<Object> eliminarAutoridad(@PathVariable Long idDepartamento)throws DepartamentoException {
        departamentoServicio.eliminarDepartamento(idDepartamento);
        return new ResponseEntity<>("Departamento eliminado", HttpStatus.OK);
    }


    @PutMapping("/{idDepartamento}")
    @Operation(summary = "Modifica una Departamento en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Departamento modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El Usuario no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> actualizarDepartamento(@Valid @PathVariable Long idDepartamento,  @RequestParam(value = "logo", required = false) MultipartFile logo,@Valid @RequestParam("departamento") String departamentoString){

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            DepartamentoDTO departamentoDTO = objectMapper.readValue(departamentoString, DepartamentoDTO.class);


            String url = departamentoServicio.buscarPorId(idDepartamento).getLogo();

            if( logo != null && !logo.isEmpty()){ // si hay un logo, entonces hace esto
                // Borra el logo viejo a partir del userID
                logoServicio.deletePorDepartamento(idDepartamento);

                url = logoServicio.save(logo);

            }

            departamentoDTO.setLogo(url);
            Departamento departamento = departamentoServicio.actualizar(idDepartamento, departamentoDTO);
            return new ResponseEntity<>(departamento, HttpStatus.CREATED);

        } catch (ConstraintViolationException e) {
            String mensaje = "";
            // Recorremos el conjunto de violaciones y mostramos solo el mensaje
            for (ConstraintViolation violation : e.getConstraintViolations()) {
                mensaje += violation.getPropertyPath() + " " + violation.getMessage() + "\n";

            }
            return new ResponseEntity<>(mensaje, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }



//        try {
//            Departamento departamento = departamentoServicio.actualizar(idDepartamento, departamentoDTO);
//            return new ResponseEntity<>(departamento, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
//        }
    }


    // ------------------------------------------------------------------------------------------------

    @GetMapping("/{idDepartamento}/autoridades")
    @Operation(summary = "Retorna todos las autoridades de un departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autoridades encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Socio.class))})
    })
    public ResponseEntity<Page<AutoridadDepartamento>> verAutoridadesPorDepartamento(Pageable pageable, @PathVariable Long idDepartamento){
        Page<AutoridadDepartamento> autoridades = autoridadDepartamentoServicio.traerAutoridadesPorDepartamento(pageable, idDepartamento);
        return new ResponseEntity<>(autoridades , HttpStatus.OK);
    }

    @PostMapping("/{idDepartamento}/autoridades")
    @Operation(summary = "Agrega autoridades en un departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autoridad encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "La autoridad no fue encontrado", content = {@Content(schema = @Schema())}),
    })
    public ResponseEntity<?> agregarAutoridad(@PathVariable Long idDepartamento, @RequestBody AutoridadDTO autoridadDTO){

        AutoridadDepartamento autoridadDepartamento = autoridadDepartamentoServicio.agregar(idDepartamento, autoridadDTO);
        return new ResponseEntity<>(autoridadDepartamento, HttpStatus.CREATED);
    }



}

