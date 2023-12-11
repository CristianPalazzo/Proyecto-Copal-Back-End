package utn.t2.s1.gestionsocios.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utn.t2.s1.gestionsocios.converters.ReservaConverter;
import utn.t2.s1.gestionsocios.dtos.ActualizacionEstadoReservaDto;
import utn.t2.s1.gestionsocios.dtos.ReservaDto;
import utn.t2.s1.gestionsocios.modelos.*;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.EspacioFisicoRepo;
import utn.t2.s1.gestionsocios.repositorios.EstadoReservaRepo;
import utn.t2.s1.gestionsocios.repositorios.RecursosRepo;
import utn.t2.s1.gestionsocios.servicios.ReservaServicio;

import java.util.List;


@Tag(name = "Operaciones para los roles", description = "Api para realizar las operaciones de alta, baja y modificacion de roles")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema(allOf = Reserva.class)) })
})
@RestController
@RequestMapping("/reservas")
@Validated
@CrossOrigin
public class ReservaController {


    @Autowired
    ReservaServicio reservaServicio;

    @Autowired
    ReservaConverter reservaConverter;

    @Autowired
    EspacioFisicoRepo espacioFisicoRepo;

    @Autowired
    EstadoReservaRepo estadoReservaRepo;

    @Autowired
    RecursosRepo recursosRepo;

    
    @GetMapping()
    @Operation(summary = "Retorna las reservas de la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservas encontradas" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Reserva.class)) }),
    })
    public ResponseEntity<Page<Reserva>> verReservas(Pageable page){

        Page<Reserva> reservas = reservaServicio.buscarTodos(page);
        return new ResponseEntity<>(reservas , HttpStatus.OK);
    }



    @GetMapping("/{id}")
    @Operation(summary = "Retorna la reserva  correspondiente al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class))}),
            @ApiResponse(responseCode = "404", description = "La reserva no fue encontrado", content = {@Content(schema = @Schema())}),
    })
    public ResponseEntity<Reserva> verReserva(@PathVariable Long id) {
        Reserva _reserva = reservaServicio.buscarPorId(id);
        return new ResponseEntity<>(_reserva, HttpStatus.OK);
    }

    @GetMapping("/codigoDeSeguimiento/{codigoDeSeguimiento}")
    @Operation(summary = "Retorna la reserva por codigo de seguimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class))}),
            @ApiResponse(responseCode = "404", description = "La reserva no fue encontrado", content = {@Content(schema = @Schema())}),
    })
    public ResponseEntity<Reserva> verReservaPorCodigoDeSeguimiento(@PathVariable String codigoDeSeguimiento) {
        Reserva _reserva = reservaServicio.buscarPorCodigoDeSeguimiento(codigoDeSeguimiento);

        return new ResponseEntity<>(_reserva, HttpStatus.OK);
    }


    @PostMapping()
    @Operation(summary = "Agrega una reserva a la Base de datos")
    public ResponseEntity<Object> agregarReserva(@RequestBody @Valid ReservaDto reservaDto){

        Reserva reserva = reservaServicio.agregar(reservaDto);

        return new ResponseEntity<>(reserva, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Modifica un reserva en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol modificado" ,content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "El rol no fue encontrada",content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> actualizarReserva(@PathVariable Long id, @RequestBody ReservaDto reservaDto){
            Reserva reservaUpdate = reservaServicio.actualizar(reservaDto, id);
            return new ResponseEntity<>(reservaUpdate , HttpStatus.OK);
    }


    // -----------ESTADO-------------------------------------------------------------------------------------
    //Cambio de estado rapido
    @PatchMapping("/{id}/estadoReserva")
    @Operation(summary = "Cambia el estado del evento correspondiente al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado cambiado" ,content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody ActualizacionEstadoReservaDto actualizacionEstadoReservaDto){

        reservaServicio.cambiarEstadoEvento(id, actualizacionEstadoReservaDto);

        return new ResponseEntity<>("Estado cambiado", HttpStatus.OK);

    }


    @DeleteMapping(value = {"/{id}", "/{id}/"})
    @Operation(summary = "Elimina una Reserva en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol eliminado" ,content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> deleteReserva(@PathVariable Long id){
            reservaServicio.eliminar(id);
            return new ResponseEntity<>("Reserva eliminado", HttpStatus.OK);
    }



//    GETS DE RECURSOS ESPECIFICOS PARA RESERVA

    @GetMapping("/espaciosFisicos")
    @Operation(summary = "Retorna los espacis fisicos de la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " Estados Fisicos encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Reserva.class)) }),
    })
    public ResponseEntity<List<EspacioFisico>> verEspaciosFisicos(){

        List<EspacioFisico> espaciosFisicos = espacioFisicoRepo.findAllByEstado(Estado.ACTIVO);
        return new ResponseEntity<>(espaciosFisicos , HttpStatus.OK);
    }


    @GetMapping("/recursos")
    @Operation(summary = "Retorna las recursos de la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "recursos encontradas" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Reserva.class)) }),
    })
    public ResponseEntity<List<Recurso>> verRecursos(){

        List<Recurso> recursos = recursosRepo.findAllByEstado(Estado.ACTIVO);
        return new ResponseEntity<>(recursos , HttpStatus.OK);
    }


    @GetMapping("/estadoReservas")
    @Operation(summary = "Retorna los estados de las reservas de la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estados de las reservas encontradas" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Reserva.class)) }),
    })
    public ResponseEntity<List<EstadoReserva>> verEstadoReserva(){

        List<EstadoReserva> estadoReservas = estadoReservaRepo.findAllByEstado(Estado.ACTIVO);
        return new ResponseEntity<>(estadoReservas , HttpStatus.OK);
    }


}
