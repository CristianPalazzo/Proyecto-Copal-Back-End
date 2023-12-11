package utn.t2.s1.gestionsocios.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.converters.RolConverter;
import utn.t2.s1.gestionsocios.dtos.ParticipanteDTO;
import utn.t2.s1.gestionsocios.dtos.RolDTO;
import utn.t2.s1.gestionsocios.modelos.Participante;
import utn.t2.s1.gestionsocios.modelos.Rol;
import utn.t2.s1.gestionsocios.servicios.ParticipanteServicio;
import utn.t2.s1.gestionsocios.servicios.RolServicio;

import java.util.List;


@Tag(name = "Operaciones de editar y eliminar participantes", description = "Api para realizar las operaciones de editar y eliminar entidades Participantes")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema(allOf = Participante.class)) })
})
@RestController
@RequestMapping("/participantes")
@Validated
@CrossOrigin
public class ParticipanteController {


    @Autowired
    ParticipanteServicio participanteServicio;





    @PutMapping("/{id}")
    @Operation(summary = "Modifica un participante en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Participante modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El participante no fue encontrada",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> actualizarParticipante(@PathVariable Long id, @RequestBody ParticipanteDTO participanteDTO){
            Participante participanteUpdate = participanteServicio.actualizar(participanteDTO, id);
            return new ResponseEntity<>(participanteUpdate , HttpStatus.OK);
    }

    @DeleteMapping(value = {"/{id}", "/{id}/"})
    @Operation(summary = "Elimina un Participante en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participante eliminado" ,content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> deleteParticipante(@PathVariable Long id){
            participanteServicio.eliminar(id);
            return new ResponseEntity<>("Participante eliminado", HttpStatus.OK);
    }


}
