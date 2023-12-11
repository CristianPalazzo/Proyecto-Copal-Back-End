package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Recurso;
import utn.t2.s1.gestionsocios.modelos.Reserva;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecursosRepo extends JpaRepository<Recurso,Long> {

    Optional<Recurso> findByIdAndEstado(Long id, Estado estado);

    Optional<Recurso> findByNombreContainsAndEstado(String nombre, Estado estado);

    List<Recurso> findAllByEstado(Estado estado);



}
