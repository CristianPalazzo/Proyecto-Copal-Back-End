package utn.t2.s1.gestionsocios.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.dtos.AutoridadDTO;
import utn.t2.s1.gestionsocios.excepciones.AutoridadSubDepartamentoException;
import utn.t2.s1.gestionsocios.modelos.AutoridadDepartamento;
import utn.t2.s1.gestionsocios.modelos.AutoridadSubDepartamento;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.Usuario;
import utn.t2.s1.gestionsocios.servicios.AutoridadSubDepartamentoServicio;

@Tag(name = "Operaciones de Autoridades (Sub Departamento)", description = "Api para realizar las operaciones de Autoridades (Sub Departamento)")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@Validated
@RequestMapping("/subdepartamento/autoridades")
@CrossOrigin
public class AutoridadSubDepartamentoController {

    @Autowired
    AutoridadSubDepartamentoServicio autoridadSubDepartamentoServicio;


    @GetMapping("/{idAutoridad}")
    @Operation(summary = "Retorna una autoridad (Sub departamento) por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autoridad encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = AutoridadDepartamento.class))})
    })
    public ResponseEntity<AutoridadSubDepartamento> verAutoridadPorId(@PathVariable Long idAutoridad) throws AutoridadSubDepartamentoException {
        AutoridadSubDepartamento autoridadSubDepartamento = autoridadSubDepartamentoServicio.buscarPorId(idAutoridad);
        return new ResponseEntity<>(autoridadSubDepartamento , HttpStatus.OK);
    }


    @DeleteMapping("/{idAutoridad}")
    @Operation(summary = "Eliminar Autoridad (Sub departamento)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autoridad eliminado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
    })
    public ResponseEntity<Object> eliminarAutoridad(@PathVariable Long idAutoridad) throws AutoridadSubDepartamentoException{
        autoridadSubDepartamentoServicio.eliminarAutoridadSubDepartamento(idAutoridad);
        return new ResponseEntity<>("Autoridad eliminado", HttpStatus.OK);
    }


    @PutMapping("/{idAutoridad}")
    @Operation(summary = "Modifica una Autoridad (Sub departamento) en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autoridad modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El Usuario no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long idAutoridad, @RequestBody AutoridadDTO autoridadDTO){
        try {
            AutoridadSubDepartamento autoridadSubDepartamento = autoridadSubDepartamentoServicio.actualizar(idAutoridad, autoridadDTO);
            return new ResponseEntity<>(autoridadSubDepartamento, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }



}