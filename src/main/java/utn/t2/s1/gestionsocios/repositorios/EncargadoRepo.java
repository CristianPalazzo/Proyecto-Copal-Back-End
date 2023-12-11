package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Encargado;
import utn.t2.s1.gestionsocios.modelos.Reserva;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EncargadoRepo extends JpaRepository<Encargado,Long> {

    Optional<Encargado> findByIdAndEstado(Long id, Estado estado);

    Optional<Encargado> findByNombreContainsAndEstado(String nombre, Estado estado);

    List<Encargado> findAllByEstado(Estado estado);



}
