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
import utn.t2.s1.gestionsocios.modelos.AutoridadDepartamento;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.Usuario;
import utn.t2.s1.gestionsocios.servicios.AutoridadDepartamentoServicio;

@Tag(name = "Operaciones de Autoridades (Departamento)", description = "Api para realizar las operaciones de Autoridades (Departamento)")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema()) })
})
@RestController
@Validated
@RequestMapping("/departamento/autoridades")
@CrossOrigin
public class AutoridadDepartamentoController {

    @Autowired
    AutoridadDepartamentoServicio autoridadDepartamentoServicio;



    @GetMapping("/{idAutoridad}")
    @Operation(summary = "Retorna una autoridad (Departamento) por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autoridad encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = AutoridadDepartamento.class))})
    })
    public ResponseEntity<AutoridadDepartamento> verAutoridadPorId(@PathVariable Long idAutoridad){
        AutoridadDepartamento autoridad = autoridadDepartamentoServicio.buscarPorId(idAutoridad);
        return new ResponseEntity<>(autoridad , HttpStatus.OK);
    }


    @DeleteMapping("/{idAutoridad}")
    @Operation(summary = "Eliminar Autoridad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autoridad (Departamento) eliminado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
    })
    public ResponseEntity<Object> eliminarAutoridad(@PathVariable Long idAutoridad) {
        autoridadDepartamentoServicio.eliminarAutoridadDepartamento(idAutoridad);
        return new ResponseEntity<>("Autoridad eliminado", HttpStatus.OK);
    }


    @PutMapping("/{idAutoridad}")
    @Operation(summary = "Modifica una Autoridad (Departamento) en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autoridad modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El Usuario no fue encontrado",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long idAutoridad, @RequestBody AutoridadDTO autoridadDTO){
        try {
            AutoridadDepartamento autoridadDepartamento = autoridadDepartamentoServicio.actualizar(idAutoridad, autoridadDTO);
            return new ResponseEntity<>(autoridadDepartamento, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
        }
    }



}