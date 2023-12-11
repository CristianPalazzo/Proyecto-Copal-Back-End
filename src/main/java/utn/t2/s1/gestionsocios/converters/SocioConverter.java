package utn.t2.s1.gestionsocios.converters;

import org.springframework.stereotype.Component;
import utn.t2.s1.gestionsocios.dtos.SocioDTO;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.TipoSocio;

import java.util.Set;

@Component
public class SocioConverter {


    public Socio toSocio(SocioDTO socioDTO, Set<Categoria> categorias, TipoSocio tipo) {
        Socio socio = new Socio();
        socio.setLogo(socioDTO.getLogo());
        socio.setCuit(socioDTO.getCuit());
        socio.setMail(socioDTO.getMail());
        socio.setFechaAlta(socioDTO.getFechaAlta());
        socio.setWeb(socioDTO.getWeb());
        socio.setTelefono(socioDTO.getTelefono());
        socio.setDenominacion(socioDTO.getDenominacion());
        socio.setDescripcion(socioDTO.getDescripcion());
        socio.setTipo(tipo);
        socio.setCategorias(categorias);
        socio.setDireccion(socioDTO.getDireccion());
        return socio;
    }

}
