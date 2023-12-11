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
import org.springframework.web.multipart.MultipartFile;
import utn.t2.s1.gestionsocios.dtos.AutoridadDTO;
import utn.t2.s1.gestionsocios.dtos.SubDepartamentoDTO;
import utn.t2.s1.gestionsocios.excepciones.SubDepartamentoException;
import utn.t2.s1.gestionsocios.modelos.*;
import utn.t2.s1.gestionsocios.servicios.AutoridadSubDepartamentoServicio;
import utn.t2.s1.gestionsocios.servicios.LogoServicio;
import utn.t2.s1.gestionsocios.servicios.ReservaServicio;
import utn.t2.s1.gestionsocios.servicios.SubDepartamentoServicio;

import java.io.File;

@Tag(name = "Operaciones de Sub Departamento", description = "Api para realizar las operaciones de Sub Departamento")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@Validated
@RequestMapping("/subdepartamento")
@CrossOrigin
public class SubDepartamentoController {

    @Autowired
    SubDepartamentoServicio subDepartamentoServicio;

    @Autowired
    AutoridadSubDepartamentoServicio autoridadSubDepartamentoServicio;

    @Autowired
    LogoServicio logoServicio;

    @Autowired
    ReservaServicio reservaServicio;



    @GetMapping("/{idSubDepartamento}")
    @Operation(summary = "Retorna todos los SubDepartamentos de un departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autoridades encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Socio.class))})
    })
    public ResponseEntity<SubDepartamento> verAutoridadesPorDepartamento(@PathVariable Long idSubDepartamento) throws SubDepartamentoException {
        SubDepartamento subDepartamento = subDepartamentoServicio.buscarPorId(idSubDepartamento);
        return new ResponseEntity<>(subDepartamento , HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Agregar un Sub departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SubDepartamento encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "El SubDepartamento no fue encontrado", content = {@Content(schema = @Schema())}),
    })
    public ResponseEntity<?> agregarSubDepartamento(@RequestParam(value = "logo", required = false) MultipartFile logo, @Valid @RequestParam("subdepartamento") String subDepartamentoString){
//    public ResponseEntity<?> agregarSubDepartamento(@RequestBody SubDepartamentoDTO subDepartamentoDTO){


        //public ResponseEntity<?> agregarSubDepartamento( @RequestParam(value = "logo", required = false) MultipartFile logo,@Valid @RequestParam("subdepartamento") String subDepartamentoString){

        File file = null;

        try {
            String url = null;
            if(   logo != null && !logo.isEmpty() ){
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
            SubDepartamentoDTO subDepartamentoDTO = objectMapper.readValue(subDepartamentoString, SubDepartamentoDTO.class);

            subDepartamentoDTO.setLogo(url);


            SubDepartamento subDepartamento = subDepartamentoServicio.agregar(subDepartamentoDTO);
            return new ResponseEntity<>(subDepartamento, HttpStatus.CREATED);

        }catch (ConstraintViolationException e) {
            if (file != null) {
                boolean borrado = file.delete();
            }
            String mensaje = "";
            // Recorremos el conjunto de violaciones y mostramos solo el mensaje
            for (ConstraintViolation violation : e.getConstraintViolations()) {
                mensaje += violation.getPropertyPath() + " " + violation.getMessage() + "\n";
            }
            return new ResponseEntity<>(mensaje, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (Exception e) {
            if (file != null) {
                boolean borrado = file.delete();
            }
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }





//        Optional<SubDepartamento> _opcionalSubDepartamento = subDepartamentoServicio.buscarPorNombreYPorDepartamento(subDepartamentoDTO.getNombre(), subDepartamentoDTO.getIdDepartamento());
//
//        if (_opcionalSubDepartamento.isEmpty()) {
//            return new ResponseEntity<>(subDepartamentoServicio.agregar(subDepartamentoDTO), HttpStatus.CREATED);
//        }
//
//        return new ResponseEntity<>("El nombre del subdepartamento ya existe para este departamento", HttpStatus.BAD_REQUEST);


    }

    @DeleteMapping("/{idSubDepartamento}")
    @Operation(summary = "Eliminar SubDepartamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SubDepartamento eliminado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
    })
    public ResponseEntity<Object> eliminarAutoridad(@PathVariable Long idSubDepartamento)throws SubDepartamentoException {
        subDepartamentoServicio.eliminarSubDepartamento(idSubDepartamento);
        return new ResponseEntity<>("SubDepartamento eliminado", HttpStatus.OK);
    }


    @PutMapping("/{idSubDepartamento}")
    @Operation(summary = "Modifica una SubDepartamento en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SubDepartamento modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El Usuario no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
//    public ResponseEntity<?> actualizarSubUsuario(@PathVariable Long idSubDepartamento, @RequestBody SubDepartamentoDTO subDepartamentoDTO){
        public ResponseEntity<?> actualizarSubDepartamento(@Valid @PathVariable Long idSubDepartamento,  @RequestParam(value = "logo", required = false) MultipartFile logo,@Valid @RequestParam("subdepartamento") String subDepartamentoString){


        try {

            ObjectMapper objectMapper = new ObjectMapper();
            SubDepartamentoDTO subDepartamentoDTO = objectMapper.readValue(subDepartamentoString, SubDepartamentoDTO.class);


            String url = subDepartamentoServicio.buscarPorId(idSubDepartamento).getLogo();

            if( logo != null && !logo.isEmpty() ){ // si hay un logo, entonces hace esto
                // Borra el logo viejo a partir del userID
                logoServicio.deletePorSubDepartamento(idSubDepartamento);

                url = logoServicio.save(logo);

            }

            subDepartamentoDTO.setLogo(url);
            SubDepartamento subDepartamento = subDepartamentoServicio.actualizar(idSubDepartamento, subDepartamentoDTO);
            return new ResponseEntity<>(subDepartamento, HttpStatus.CREATED);

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
//            SubDepartamento subDepartamento = subDepartamentoServicio.actualizar(idSubDepartamento, subDepartamentoDTO);
//            return new ResponseEntity<>(subDepartamento, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
//        }
    }

    //---------------------------------------------------------------------------------------------------------------

    @GetMapping("/{idSubDepartamento}/autoridades")
    @Operation(summary = "Retorna todos las autoridades de un subdepartamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autoridades encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Socio.class))})
    })
    public ResponseEntity<Page<AutoridadSubDepartamento>> verAutoridadesPorDepartamento(Pageable pageable, @PathVariable Long idSubDepartamento){
        Page<AutoridadSubDepartamento> autoridades = autoridadSubDepartamentoServicio.traerAutoridadesPorSubDepartamento(pageable, idSubDepartamento);
        return new ResponseEntity<>(autoridades , HttpStatus.OK);
    }

    @PostMapping("/{idSubDepartamento}/autoridades")
    @Operation(summary = "Agrega una autoridad de un sub departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autoridad encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "La autoridad no fue encontrado", content = {@Content(schema = @Schema())}),
    })
    public ResponseEntity<?> agregarAutoridad(@PathVariable Long idSubDepartamento, @RequestBody AutoridadDTO autoridadDTO){
        AutoridadSubDepartamento autoridadSubDepartamento = autoridadSubDepartamentoServicio.agregar(idSubDepartamento, autoridadDTO);
        return new ResponseEntity<>(autoridadSubDepartamento, HttpStatus.CREATED);
    }


    //-------------------------------------------------------RESERVAS--------------------------------------------------------

    @GetMapping("/{idSubDepartamento}/reservas")
    @Operation(summary = "Retorna todos las Reservas de un subdepartamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas por subdepartamentos encontradas", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Reserva.class))})
    })
    public ResponseEntity<Page<Reserva>> verReservasPorDepartamento(Pageable pageable, @PathVariable Long idSubDepartamento){
        Page<Reserva> reservas = reservaServicio.buscarPorSubdepartamentoId(idSubDepartamento, pageable);
        return new ResponseEntity<>(reservas , HttpStatus.OK);
    }


}

