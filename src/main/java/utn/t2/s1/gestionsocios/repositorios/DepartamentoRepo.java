package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Departamento;
import utn.t2.s1.gestionsocios.modelos.SubDepartamento;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.util.List;
import java.util.Optional;

public interface DepartamentoRepo extends JpaRepository<Departamento, Long> {
    Optional<Departamento> findByIdAndEstado(Long id, Estado estado);

    Page<Departamento> findAllByEstado(Pageable pageable, Estado estado);

    Optional<Departamento> findByNombreDepartamentoAndEstado(String nombre, Estado estado);


//    Page<Departamento> findAllByDepartamentoIdAndEstado(Pageable page, Long departamentoId, Estado estado);

//    Page<SubDepartamento> findAllByDepartamentoAndEstado(Pageable page, Long departamentoId, Estado estado);
    Page<Departamento> findByNombreDepartamentoContainsAndEstado(Pageable page, String nombre, Estado estado);
}
