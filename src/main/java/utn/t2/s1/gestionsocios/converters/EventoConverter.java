package utn.t2.s1.gestionsocios.converters;

import org.springframework.stereotype.Component;
import utn.t2.s1.gestionsocios.dtos.EventoDTO;
import utn.t2.s1.gestionsocios.dtos.TipoDeUsuarioDTO;
import utn.t2.s1.gestionsocios.modelos.Evento;
import utn.t2.s1.gestionsocios.modelos.TipoDeUsuario;

@Component
public class EventoConverter {
    public Evento toEvento(EventoDTO eventoDTO, Evento evento) {

        evento.setNombre(eventoDTO.getNombre());
        evento.setFechaInicio(eventoDTO.getFechaInicio());
        evento.setFechaFin(eventoDTO.getFechaFin());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setEstadoEvento(eventoDTO.getEstadoEvento());
        evento.setModalidad(eventoDTO.getModalidad());

        return evento;
    }
}