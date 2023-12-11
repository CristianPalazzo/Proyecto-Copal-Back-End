package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.TipoDeUsuario;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.util.List;


public interface TipoDeUsuarioRepo extends JpaRepository<TipoDeUsuario,Long> {
//    List<TipoDeUsuario> findAllByFechaBajaIsNull();

    List<TipoDeUsuario> findAllByEstado(Estado estado);


//    TipoDeUsuario findByNombreTipoDeUsuarioAndEstado(String nombre, Estado estado);

    TipoDeUsuario findByTipoAndEstado(String tipo, Estado estado);


}
