package utn.t2.s1.gestionsocios.converters;

import org.springframework.stereotype.Component;
import utn.t2.s1.gestionsocios.dtos.RolDTO;
import utn.t2.s1.gestionsocios.modelos.Rol;


@Component
public class RolConverter {
    public Rol toRol(RolDTO rolDTO) {
        Rol rol = new Rol();
        rol.setNombreRol(rolDTO.getNombre());
        return rol;
    }
}
