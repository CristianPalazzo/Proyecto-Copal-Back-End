package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import utn.t2.s1.gestionsocios.modelos.Rol;
import utn.t2.s1.gestionsocios.modelos.TipoDeUsuario;
import utn.t2.s1.gestionsocios.modelos.Usuario;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.util.Optional;

public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

    Page<Usuario> findAllByEstado(Pageable pageable, Estado estado);

    Optional<Usuario> findByNombreAndEstado(String nombreUsuario, Estado estado);

    Optional<Usuario> findByIdAndEstado(Long id, Estado estado);



}
