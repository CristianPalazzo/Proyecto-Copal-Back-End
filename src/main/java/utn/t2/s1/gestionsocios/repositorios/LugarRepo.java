package utn.t2.s1.gestionsocios.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import utn.t2.s1.gestionsocios.modelos.Lugar;


public interface LugarRepo extends JpaRepository<Lugar,Long> {

}
