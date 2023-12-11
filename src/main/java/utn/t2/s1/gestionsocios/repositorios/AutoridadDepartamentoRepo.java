package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.AutoridadDepartamento;
import utn.t2.s1.gestionsocios.modelos.Usuario;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.util.Optional;

public interface AutoridadDepartamentoRepo extends JpaRepository<AutoridadDepartamento, Long> {


    Page<AutoridadDepartamento> findAllByEstado(Pageable pageable, Estado estado);

    Optional<AutoridadDepartamento> findByIdAndEstado(Long id, Estado estado);



    Page<AutoridadDepartamento> findAllByDepartamentoIdAndEstado(Pageable pageable, Long departamentoId, Estado estado);


}
