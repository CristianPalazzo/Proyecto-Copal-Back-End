package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Evento;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.persistencia.EstadoEvento;
import utn.t2.s1.gestionsocios.persistencia.Modalidad;

import java.util.Optional;
import java.util.UUID;

public interface EventoRepo extends JpaRepository<Evento,UUID> {
    Optional<Evento> findByIdAndEstado(UUID id, Estado estado);
    Page<Evento> findByNombreContainsAndEstado(Pageable pageable,String nombre, Estado estado);

    Page<Evento> findAllByEstado(Pageable pageable, Estado estado);

    Page<Evento> findByNombreContainsAndModalidadAndEstadoEventoAndEstado(Pageable page, String nombre, Modalidad modalidad, EstadoEvento estadoEvento, Estado estado);

    Page<Evento> findByModalidadAndEstado(Pageable page, Modalidad modalidad, Estado estado);

    Page<Evento> findByModalidadAndEstadoEventoAndEstado(Pageable page, Modalidad modalidad, EstadoEvento estadoEvento, Estado estado);

    Page<Evento> findByNombreContainsAndEstadoEventoAndEstado(Pageable page, String nombre, EstadoEvento estadoEvento, Estado estado);

    Page<Evento> findByEstadoEventoAndEstado(Pageable page, EstadoEvento estadoEvento, Estado estado);

    Page<Evento> findByNombreContainsAndModalidadAndEstado(Pageable page, String nombre, Modalidad modalidad, Estado estado);
}
