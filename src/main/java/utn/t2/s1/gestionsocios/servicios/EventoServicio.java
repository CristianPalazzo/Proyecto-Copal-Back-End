package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.converters.EventoConverter;
import utn.t2.s1.gestionsocios.dtos.EstadoEventoDTO;
import utn.t2.s1.gestionsocios.dtos.EventoDTO;
import utn.t2.s1.gestionsocios.dtos.ParticipanteDTO;
import utn.t2.s1.gestionsocios.modelos.*;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.persistencia.EstadoEvento;
import utn.t2.s1.gestionsocios.persistencia.Modalidad;
import utn.t2.s1.gestionsocios.repositorios.EventoRepo;
import utn.t2.s1.gestionsocios.repositorios.LugarRepo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class EventoServicio {

    @Autowired
    private EventoRepo eventoRepo;

    @Autowired
    private LugarServicio lugarServicio;
    @Autowired
    private LugarRepo lugarRepo;

    @Autowired
    private EventoConverter eventoConverter;


    //Filtra participantes activas por evento
    public Evento filtrarParticipantesActivos(Evento evento){
        List<Participante> participantes = this.filtrarParticipantesActivos(evento.getParticipantes());
        evento.setParticipantes(participantes);
        return evento;
    }

    public List<Participante> filtrarParticipantesActivos(List<Participante> participantes){
        List<Participante> participantesActivos = participantes.stream()
                .filter((y) -> y.getEstado().equals(Estado.ACTIVO))
                .collect(Collectors.toList());
        return participantesActivos;
    }


    public Page<Evento> buscarTodos(Pageable page) {

        Page <Evento> eventoPage = eventoRepo.findAllByEstado(page , Estado.ACTIVO);

        eventoPage.stream().map(this::filtrarParticipantesActivos).collect(Collectors.toList());

        return eventoPage;


//        departamento.getSubDepartamentoList().stream().map(this::filtrarAutoridadesActivasSubDepartamento).collect(Collectors.toList());
//
//        return eventoRepo.findAllByEstado(page , Estado.ACTIVO) ;

    }


    public Evento buscarPorId(UUID id) {

        Evento evento = eventoRepo.findByIdAndEstado(id, Estado.ACTIVO).orElseThrow( () -> new EntityNotFoundException("Evento no encontrado"));

        List<Participante> participantes = this.filtrarParticipantesActivos(evento.getParticipantes());
        evento.setParticipantes(participantes);

        return evento;

//        return eventoRepo.findByIdAndEstado(id, Estado.ACTIVO).orElseThrow( () -> new EntityNotFoundException("Evento no encontrado"));
    }



    public Page<Evento> buscarPorNombre(Pageable page, String nombre) {

        Page <Evento> eventoPage = eventoRepo.findByNombreContainsAndEstado(page, nombre, Estado.ACTIVO);

        eventoPage.stream().map(this::filtrarParticipantesActivos).collect(Collectors.toList());

        return eventoPage;

//        return eventoRepo.findByNombreContainsAndEstado(page, nombre, Estado.ACTIVO);
    }


    //filtrado por modalidades y por estado
    public Page<Evento> buscarPorNombreFiltrandoPorModalidadYOEstadoEvento(Pageable page, String nombre,String modalidad, String estadoEvento) {


        modalidad = modalidad == null ? null : modalidad.isEmpty() ? null : modalidad;
        estadoEvento = estadoEvento == null ? null : estadoEvento.isEmpty() ? null : estadoEvento;


        if (nombre != null && modalidad != null && estadoEvento != null) {

            Modalidad modalidadEnum = Modalidad.valueOf(modalidad);
            EstadoEvento estadoEventoEnum = EstadoEvento.valueOf(estadoEvento);

            Page <Evento> eventoPage = eventoRepo.findByNombreContainsAndModalidadAndEstadoEventoAndEstado(page, nombre, modalidadEnum, estadoEventoEnum, Estado.ACTIVO);

            eventoPage.stream().map(this::filtrarParticipantesActivos).collect(Collectors.toList());

            return eventoPage;

//            return eventoRepo.findByNombreContainsAndModalidadAndEstadoEventoAndEstado(page, nombre, modalidadEnum, estadoEventoEnum, Estado.ACTIVO);

        } else if (nombre == null && modalidad != null && estadoEvento != null) {

            Modalidad modalidadEnum = Modalidad.valueOf(modalidad);
            EstadoEvento estadoEventoEnum = EstadoEvento.valueOf(estadoEvento);

            Page <Evento> eventoPage = eventoRepo.findByModalidadAndEstadoEventoAndEstado(page, modalidadEnum, estadoEventoEnum, Estado.ACTIVO);

            eventoPage.stream().map(this::filtrarParticipantesActivos).collect(Collectors.toList());

            return eventoPage;

//            return eventoRepo.findByModalidadAndEstadoEventoAndEstado(page, modalidadEnum, estadoEventoEnum, Estado.ACTIVO);

        } else if (nombre != null && modalidad == null && estadoEvento != null) {

            EstadoEvento estadoEventoEnum = EstadoEvento.valueOf(estadoEvento);

            Page <Evento> eventoPage = eventoRepo.findByNombreContainsAndEstadoEventoAndEstado(page, nombre, estadoEventoEnum, Estado.ACTIVO);

            eventoPage.stream().map(this::filtrarParticipantesActivos).collect(Collectors.toList());

            return eventoPage;


//            return eventoRepo.findByNombreContainsAndEstadoEventoAndEstado(page, nombre, estadoEventoEnum, Estado.ACTIVO);

        } else if (nombre != null && modalidad != null && estadoEvento == null) {

            Modalidad modalidadEnum = Modalidad.valueOf(modalidad);

            Page <Evento> eventoPage = eventoRepo.findByNombreContainsAndModalidadAndEstado(page, nombre, modalidadEnum, Estado.ACTIVO);

            eventoPage.stream().map(this::filtrarParticipantesActivos).collect(Collectors.toList());

            return eventoPage;
            
            
//            return eventoRepo.findByNombreContainsAndModalidadAndEstado(page, nombre, modalidadEnum, Estado.ACTIVO);

        } else if (nombre == null && modalidad == null && estadoEvento != null) {

            EstadoEvento estadoEventoEnum = EstadoEvento.valueOf(estadoEvento);

            Page <Evento> eventoPage = eventoRepo.findByEstadoEventoAndEstado(page, estadoEventoEnum, Estado.ACTIVO);

            eventoPage.stream().map(this::filtrarParticipantesActivos).collect(Collectors.toList());

            return eventoPage;
            
//            return eventoRepo.findByEstadoEventoAndEstado(page, estadoEventoEnum, Estado.ACTIVO);

        } else if (nombre == null && modalidad != null && estadoEvento == null) {

            Modalidad modalidadEnum = Modalidad.valueOf(modalidad);

            Page <Evento> eventoPage = eventoRepo.findByModalidadAndEstado(page, modalidadEnum, Estado.ACTIVO);

            eventoPage.stream().map(this::filtrarParticipantesActivos).collect(Collectors.toList());

            return eventoPage;

            
//            return eventoRepo.findByModalidadAndEstado(page, modalidadEnum, Estado.ACTIVO);

        } else if (nombre != null && modalidad == null && estadoEvento == null) {

            Page <Evento> eventoPage = eventoRepo.findByNombreContainsAndEstado(page, nombre, Estado.ACTIVO);

            eventoPage.stream().map(this::filtrarParticipantesActivos).collect(Collectors.toList());

            return eventoPage;

//            return eventoRepo.findByNombreContainsAndEstado(page, nombre, Estado.ACTIVO);

        } else {
            Page <Evento> eventoPage = eventoRepo.findAllByEstado(page, Estado.ACTIVO);

            eventoPage.stream().map(this::filtrarParticipantesActivos).collect(Collectors.toList());

            return eventoPage;


//            return eventoRepo.findAllByEstado(page, Estado.ACTIVO);
        }


    }


    public Evento agregar(EventoDTO eventoDTO) {


//        if (empresaEvento.getSocio() != null && empresaEvento.getOtraEmpresa() != null) {
//            throw new IllegalArgumentException("No se puede asignar un socio y otra empresa al mismo tiempo.");
//        }
//
//        if (empresaEvento.getSocio() == null && empresaEvento.getOtraEmpresa() == null) {
//            throw new IllegalArgumentException("Se debe asignar un socio o una empresa");
//        }

        ModelMapper modelMapper = new ModelMapper();
        Evento evento = modelMapper.map(eventoDTO, Evento.class);


        Lugar lugar = lugarServicio.agregar(eventoDTO.getLugar());


        if ((evento.getLugar().getDireccion() == null || evento.getLugar().getDireccion().isEmpty()) && (evento.getLugar().getLinkVirtual() == null || evento.getLugar().getLinkVirtual().isEmpty() ) && (evento.getLugar().getLinkMaps() == null || evento.getLugar().getLinkMaps().isEmpty())) {
            throw new IllegalArgumentException("Se debe asignar una dirección, un link de maps o un link virtual");
        }

        evento.setLugar(lugar);


        evento.setEstado(Estado.ACTIVO);

        evento = eventoRepo.save(evento);


  /*      try {
            evento = eventoRepo.save(evento);
        } catch (DataIntegrityViolationException e) {
            // Manejar la excepción de violación de restricción única (rol duplicado)
            throw new IllegalArgumentException("El nombre del participante ya existe en la base de datos.");
        }*/
        return evento;
    }


    public Evento actualizar(EventoDTO eventoDTO, UUID id) {
        Optional<Evento> optionalEvento = eventoRepo.findById(id);
        if (!optionalEvento.isPresent()) {
            throw new EntityNotFoundException("Evento no encontrado");
        }
        Evento eventoUpdate = optionalEvento.get();


        eventoUpdate.setLugar(lugarServicio.actualizar(eventoDTO.getLugar(), eventoUpdate.getLugar().getId()));

        eventoConverter.toEvento(eventoDTO, eventoUpdate);

        eventoUpdate = eventoConverter.toEvento(eventoDTO, eventoUpdate);


        eventoUpdate = eventoRepo.save(eventoUpdate);

        return eventoUpdate;
    }


    public void eliminar(UUID id) {

        Optional<Evento> optionalParticipante = eventoRepo.findById(id);
        if (!optionalParticipante.isPresent() || optionalParticipante.get().getEstado() == Estado.ELIMINADO) {
            throw new EntityNotFoundException("Participante no encontrado");
        }

        Evento evento = optionalParticipante.get();

        evento.setEstado(Estado.ELIMINADO);

        eventoRepo.save(evento);
    }


    public Evento cambiarEstado(UUID id, EstadoEventoDTO estadoEventoDTO) {
        Optional<Evento> optionalEvento = eventoRepo.findById(id);
        if (!optionalEvento.isPresent()) {
            throw new EntityNotFoundException("Evento no encontrado");
        }
        Evento eventoUpdate = optionalEvento.get();
//        if (estado.equals("ACTIVO")){
//            eventoUpdate.setEstadoEvento(EstadoEvento.ACTIVO);
//        }
//        if (estado.equals("INACTIVO")){
//            eventoUpdate.setEstadoEvento(EstadoEvento.INACTIVO);
//        }
//        if (estado.equals("INACTIVO")) {
//
//        } else {
//
//        }

        eventoUpdate.setEstadoEvento(estadoEventoDTO.getEstadoEvento());
        eventoUpdate = eventoRepo.save(eventoUpdate);

        return eventoUpdate;
    }

}
