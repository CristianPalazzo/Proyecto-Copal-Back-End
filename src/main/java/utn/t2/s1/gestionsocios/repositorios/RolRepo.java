package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Rol;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.util.List;
import java.util.Optional;

public interface RolRepo extends JpaRepository<Rol,Long> {
    Optional<Rol> findByIdAndEstado(Long id, Estado estado);
    Rol findByNombreRolAndEstado(String nombre, Estado estado);

    List<Rol> findAllByEstado(Estado estado);


}
