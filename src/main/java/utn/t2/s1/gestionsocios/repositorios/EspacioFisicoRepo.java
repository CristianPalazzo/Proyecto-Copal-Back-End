package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.EspacioFisico;
import utn.t2.s1.gestionsocios.modelos.Reserva;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EspacioFisicoRepo extends JpaRepository<EspacioFisico,Long> {

    Optional<EspacioFisico> findByIdAndEstado(Long id, Estado estado);

    Optional<EspacioFisico> findByNombreContainsAndEstado(String nombre, Estado estado);

    List<EspacioFisico> findAllByEstado(Estado estado);



}
