package utn.t2.s1.gestionsocios.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.TipoSocio;
import utn.t2.s1.gestionsocios.persistencia.Estado;

import java.util.Optional;

public interface SocioRepo extends JpaRepository<Socio, Long> {


    Optional<Socio> findByIdAndEstado(Long id, Estado estado);

//    Socio findByIdAndEstado(Long id, Estado estado);
    Page<Socio> findAllByEstado(Pageable pageable, Estado estado);
    Page<Socio> findByDenominacionContainingAndTipo_NombreAndTipo_EstadoAndEstado(Pageable pageable, String denominacion, String tipo, Estado estadoTipo ,Estado estado);
    Page<Socio> findByTipo_NombreAndTipo_EstadoAndEstado(Pageable pageable, String tipo, Estado estadoTipo, Estado estado);
    Page<Socio> findByDenominacionContainingAndEstado(Pageable pageable, String denominacion, Estado estado);
    Optional<Socio> findByDenominacionAndEstado(String denominacion,Estado estado);
}
