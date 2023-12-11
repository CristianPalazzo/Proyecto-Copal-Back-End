package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.AutoridadSubDepartamento;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.util.Optional;

public interface AutoridadSubDepartamentoRepo extends JpaRepository<AutoridadSubDepartamento, Long> {
//    Page<AutoridadSubDepartamento> findAllByDepartamentoIdAndEstado(Pageable pageable, Long departamentoId, Estado estado);



    Page<AutoridadSubDepartamento> findAllByEstado(Pageable pageable, Estado estado);

    Optional<AutoridadSubDepartamento> findByIdAndEstado(Long id, Estado estado);

    Page<AutoridadSubDepartamento> findAllBySubDepartamentoIdAndEstado(Pageable pageable, Long departamentoId, Estado estado);



}
