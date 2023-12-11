package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.EstadoReserva;
import utn.t2.s1.gestionsocios.modelos.Reserva;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EstadoReservaRepo extends JpaRepository<EstadoReserva,Long> {

    Optional<EstadoReserva> findByIdAndEstado(Long id, Estado estado);

    Optional<EstadoReserva> findByNombreContainsAndEstado(String nombre, Estado estado);

    List<EstadoReserva> findAllByEstado(Estado estado);




}
