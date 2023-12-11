package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.excepciones.CategoriaException;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.CategoriaRepo;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoriaServicio {

    @Autowired
    private CategoriaRepo repo;

    public Categoria buscarPorNombre(String nombre) {
        return repo.findByNombreAndEstado(nombre,Estado.ACTIVO);
    }
    public List<Categoria> categorias(){
        return repo.findAllByEstado(Estado.ACTIVO);
    }
//    public List<String> nombresCategoria(){
//        return repo.findAllByEstado(Estado.ACTIVO).stream().map(Categoria::getNombre).toList() ;
//    }

    public  Set<Categoria> stringSetToCategoriaSet(Set<String> lista) throws CategoriaException{
        Set<Categoria> set =  lista.stream()
                    .map(this::buscarPorNombre)
                    .collect(Collectors.toSet());
        if (set.contains(null)){
            throw new CategoriaException();
        }else{
            return set;
        }
    }

    public Categoria buscarPorId(Long id) {
        return repo.findByIdAndEstado(id, Estado.ACTIVO).orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));
    }

    public Categoria agregar(Categoria categoria) {
        return repo.save(categoria);
    }

    public void borrar(Long id) {
        Categoria _categoria = this.buscarPorId(id);
        _categoria.setEstado(Estado.ELIMINADO);
        repo.save(_categoria);
    }
}
