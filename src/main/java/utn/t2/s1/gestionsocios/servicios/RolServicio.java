package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.converters.RolConverter;
import utn.t2.s1.gestionsocios.dtos.RolDTO;
import utn.t2.s1.gestionsocios.modelos.Rol;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.RolRepo;


import java.util.List;
import java.util.Optional;


@Service
public class RolServicio {

    @Autowired
    RolRepo rolRepo;

    @Autowired
    RolConverter rolConverter;


    public List<Rol> buscarTodos() {
        return rolRepo.findAllByEstado(Estado.ACTIVO) ;
    }


    public Rol buscarPorNombre(String nombre) {
        return rolRepo.findByNombreRolAndEstado(nombre, Estado.ACTIVO);
    }


    public Rol agregar(RolDTO rolDTO) {
        Rol rol = rolConverter.toRol(rolDTO);

        rol.setEstado(Estado.ACTIVO);

        try {
            rol = rolRepo.save(rol);
        } catch (DataIntegrityViolationException e) {
            // Manejar la excepción de violación de restricción única (rol duplicado)
            throw new IllegalArgumentException("El nombre del rol ya existe en la base de datos.");
        }
        return rol;
    }




    public Rol actualizar(RolDTO rolDTO, long id) {
        Optional<Rol> optionalRol = rolRepo.findById(id);
        if (!optionalRol.isPresent()) {
            throw new EntityNotFoundException("Rol no encontrado");
        }
        Rol rolUpdate = optionalRol.get();
        rolUpdate.setNombreRol(rolDTO.getNombre());
        rolUpdate = rolRepo.save(rolUpdate);

        return rolUpdate;
    }


    public void eliminar(long id) {

        Optional<Rol> optionalRol = rolRepo.findById(id);
        if (!optionalRol.isPresent() || optionalRol.get().getEstado() == Estado.ELIMINADO) {
            throw new EntityNotFoundException("ROL no encontrado");
        }

        Rol rol = optionalRol.get();

        rol.setEstado(Estado.ELIMINADO);

        rolRepo.save(rol);
    }

}
