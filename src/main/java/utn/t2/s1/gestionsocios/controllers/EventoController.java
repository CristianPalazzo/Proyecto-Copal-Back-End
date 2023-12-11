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
import utn.t2.s1.gestionsocios.dtos.EventoDTO;
import utn.t2.s1.gestionsocios.dtos.EstadoEventoDTO;
import utn.t2.s1.gestionsocios.dtos.ParticipanteDTO;
import utn.t2.s1.gestionsocios.modelos.*;
import utn.t2.s1.gestionsocios.persistencia.EstadoEvento;
import utn.t2.s1.gestionsocios.persistencia.Modalidad;
import utn.t2.s1.gestionsocios.servicios.EventoServicio;
import utn.t2.s1.gestionsocios.servicios.LugarServicio;
import utn.t2.s1.gestionsocios.servicios.ParticipanteServicio;

import java.util.UUID;

@Tag(name = "Operaciones para los eventos", description = "Api para realizar las operaciones de alta, baja y modificacion de eventos")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Error en el servidor", content = { @Content(schema = @Schema(allOf = Evento.class)) })
})
@RestController
@RequestMapping("/eventos")
@Validated
@CrossOrigin
public class EventoController {

    @Autowired
    ParticipanteServicio participanteServicio;

    @Autowired
    EventoServicio eventoServicio;

    @Autowired
    LugarServicio lugarServicio;

    @GetMapping()
    @Operation(summary = "Retorna los eventos de la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Evento.class))}),
    })
    public ResponseEntity<Page<Evento>> verEventos(Pageable page) {
        Page<Evento> eventos = eventoServicio.buscarTodos(page);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }

// NO SE ESTARIA USANDO EN EL FRONT
    @GetMapping(value = {"/search", "/search/"})
    @Operation(summary = "Retorna los Eventos buscados por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos Filtrados encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Evento.class))}),
    })
    public ResponseEntity<Page<Evento>> verEventosBuscadoPorNombre(Pageable pageable, @RequestParam(required = false) String nombre) {

        Page<Evento> eventos = eventoServicio.buscarPorNombre(pageable, nombre);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }


    @GetMapping(value = {"/search/filtro", "/search/filtro/"})
    @Operation(summary = "Retorna los Eventos buscados por nombre y filtrados por MODALIDAD y/o ESTADOEVENTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos Filtrados encontrados", content = {@Content(mediaType = "application/json", schema = @Schema(allOf = Evento.class))}),
    })
    public ResponseEntity<Page<Evento>> verEventosBuscadosYFiltradoPorModalidadYEstadoEvento(Pageable pageable, @RequestParam(required = false) String nombre, @RequestParam(required = false) String modalidad, @RequestParam(required = false) String estadoEvento) {

        Page<Evento> eventos = eventoServicio.buscarPorNombreFiltrandoPorModalidadYOEstadoEvento(pageable, nombre, modalidad, estadoEvento);
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Retorna el evento  correspondiente al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Evento.class))}),
            @ApiResponse(responseCode = "404", description = "El evento no fue encontrado", content = {@Content(schema = @Schema())}),
    })
    public ResponseEntity<Evento> verEvento(@PathVariable UUID id) {

        if (eventoServicio.buscarPorId(id) == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Evento _evento = eventoServicio.buscarPorId(id);
        return new ResponseEntity<>(_evento, HttpStatus.OK);
    }


    @PostMapping()
    @Operation(summary = "Agrega un evento a la Base de datos")
    public ResponseEntity<Object> agregarEvento(@RequestBody @Valid EventoDTO eventoDTO) {
//        if(eventoServicio.buscarPorNombre(eventoDTO.getNombre()) != null){
//            return new ResponseEntity<>("El evento '"+ eventoDTO.getNombre()+"' ya existe", HttpStatus.CREATED);
//        }

        Evento evento = eventoServicio.agregar(eventoDTO);


        return new ResponseEntity<>(evento, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Modifica un evento en la Base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento modificado", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "El formato del objeto es invalido", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "El evento no fue encontrada", content = {@Content(schema = @Schema())}),
    })
    public ResponseEntity<?> actualizarEvento(@PathVariable UUID id, @RequestBody EventoDTO eventoDTO) {
        Evento eventoUpdate = eventoServicio.actualizar(eventoDTO, id);
        return new ResponseEntity<>(eventoUpdate, HttpStatus.OK);
    }

// COMPLETAMENTE FUNCIONAL
//    @DeleteMapping(value = {"/{id}", "/{id}/"})
//    @Operation(summary = "Elimina un evento en la Base de datos")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "evento eliminado" ,content = { @Content(schema = @Schema()) }),
//    })
//    public ResponseEntity<?> deleteEvento(@PathVariable Long id){
//        eventoServicio.eliminar(id);
//        return new ResponseEntity<>("Evento eliminado", HttpStatus.OK);
//    }


    // -----------ESTADO-------------------------------------------------------------------------------------
    //Cambio de estado rapido
    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambia el estado del evento correspondiente al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado cambiado" ,content = { @Content(schema = @Schema()) }),
    })
    public ResponseEntity<?> cambiarEstado(@PathVariable UUID id, @RequestBody EstadoEventoDTO estadoEventoDTO){

        eventoServicio.cambiarEstado(id, estadoEventoDTO);

        return new ResponseEntity<>("Estado cambiado", HttpStatus.OK);

    }

    //Devuelve los estados
    @GetMapping("/estados")
    @Operation(summary = "Retorna los estados de los eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estados encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = EstadoEvento.class)) }),
    })
    public ResponseEntity<EstadoEvento[]> verEstados() {
        EstadoEvento[] estados = EstadoEvento.values();
        return new ResponseEntity<>(estados, HttpStatus.OK);
    }


    // -----------MODALIDADES-------------------------------------------------------------------------------------

    //Devuelve las modalidades
    @GetMapping("/modalidades")
    @Operation(summary = "Retorna las modalidades de los eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modalidades encontradas" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Modalidad.class)) }),
    })
    public ResponseEntity<Modalidad[]> verModalidades() {
        Modalidad[] modalidades = Modalidad.values();
        return new ResponseEntity<>(modalidades, HttpStatus.OK);
    }


    // -----------PARTICIPANTES-------------------------------------------------------------------------------------

    @GetMapping("/{id}/participantes")
    @Operation(summary = "Retorna los participantes del evento correspondiente al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participantes encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Participante.class)) }),
    })
    public ResponseEntity<Page<Participante>> verParticipantes(@PathVariable UUID id, Pageable pageable) {
        Page<Participante> participantes = participanteServicio.buscarTodos(pageable, id);
        return new ResponseEntity<>(participantes, HttpStatus.OK);
    }

    @GetMapping("/{id}/participantes/search")
    @Operation(summary = "Retorna los participantes del evento correspondiente al id buscados por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participantes encontrados" ,content = { @Content(mediaType = "application/json",schema = @Schema( allOf = Participante.class)) }),
    })
    public ResponseEntity<Page<Participante>> verParticipantesFiltradoBuscado(@PathVariable UUID id, @RequestParam(required = true) String nombre, Pageable pageable) {

        if (participanteServicio.buscarPorNombre(pageable, nombre) == null){
            Page<Participante> participantes = participanteServicio.buscarTodos(pageable, id);
            return new ResponseEntity<>(participantes, HttpStatus.OK);
        }

        Page<Participante> participantes = participanteServicio.buscarPorNombre(pageable, nombre);
        return new ResponseEntity<>(participantes, HttpStatus.OK);
    }

    @PostMapping("/{idString}/participantes")
    @Operation(summary = "Agrega un participante al evento correspondiente al id")
    public ResponseEntity<Participante> agregarParticipante(@PathVariable String idString, @RequestBody @Valid ParticipanteDTO participanteDTO){
        UUID id = UUID.fromString (idString);

        participanteDTO.setEventoId(id);


        Participante participante = participanteServicio.agregar(participanteDTO);

        return new ResponseEntity<>(participante, HttpStatus.CREATED);
    }







}
