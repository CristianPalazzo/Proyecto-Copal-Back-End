package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.excepciones.TipoException;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.TipoSocio;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.TipoSocioRepo;

import java.util.List;

@Service
public class TipoSocioServicio {
    @Autowired
    TipoSocioRepo repo;

    public TipoSocio buscarPorNombre(String nombre){
        return repo.findByNombreAndEstado(nombre,Estado.ACTIVO);
    }
    public List<TipoSocio> tiposDesocio(){
        return repo.findAllByEstado(Estado.ACTIVO);
    }

//    public List<String> nombresSocios(){
//        return repo.findAllByEstado(Estado.ACTIVO).stream().map(TipoSocio::getNombre).toList() ;
//    }

    public TipoSocio buscarPorId(Long id) {
        return repo.findByIdAndEstado(id, Estado.ACTIVO).orElseThrow(() -> new EntityNotFoundException("Tipo de socio no encontrado"));
    }

    public TipoSocio agregar(TipoSocio tipo) {
        return repo.save(tipo);
    }

    public void borrar(Long id) {
        TipoSocio _tipo = this.buscarPorId(id);
        _tipo.setEstado(Estado.ELIMINADO);
        repo.save(_tipo);
    }
}
