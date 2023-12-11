package utn.t2.s1.gestionsocios.converters;

import org.springframework.stereotype.Component;
import utn.t2.s1.gestionsocios.dtos.EncargadoDto;
import utn.t2.s1.gestionsocios.dtos.RolDTO;
import utn.t2.s1.gestionsocios.modelos.Encargado;
import utn.t2.s1.gestionsocios.modelos.Rol;


@Component
public class EncargadoConverter {
    public Encargado toEncargado(EncargadoDto encargadoDto) {
        Encargado encargado = new Encargado();

        this.toEncargado(encargadoDto, encargado);

        return encargado;
    }

    public Encargado toEncargado(EncargadoDto encargadoDto, Encargado encargado) {

        encargado.setNombre(encargadoDto.getNombre());
        encargado.setMail(encargadoDto.getMail());
        encargado.setApellido(encargadoDto.getApellido());


        return encargado;
    }

}
