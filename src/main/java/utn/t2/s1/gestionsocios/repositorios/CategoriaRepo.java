package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import java.util.List;
import java.util.Optional;


public interface CategoriaRepo extends JpaRepository<Categoria,Long> {

    Optional<Categoria> findByIdAndEstado(Long id, Estado estado);
    Categoria findByNombreAndEstado(String nombre,Estado estado);
    List<Categoria> findAllByEstado(Estado estado);

}
