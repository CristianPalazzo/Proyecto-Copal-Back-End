package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Evento;
import utn.t2.s1.gestionsocios.modelos.Participante;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.util.List;
import java.util.Optional;

public interface ParticipanteRepo extends JpaRepository<Participante, Long> {
    Optional<Participante> findByIdAndEstado(Long id, Estado estado);

//    Participante findByNombreRolAndEstado(String nombre, Estado estado);
    Page<Participante> findByNombreAndEstado(Pageable pageable, String nombre, Estado estado);


//    Page<Participante> findAllByEstado(Pageable pageable, Estado estado);
    Page<Participante> findAllByEstadoAndEvento(Pageable pageable, Estado estado, Evento evento);

}
