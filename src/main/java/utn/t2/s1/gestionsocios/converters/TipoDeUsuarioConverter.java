package utn.t2.s1.gestionsocios.converters;

import org.springframework.stereotype.Component;
import utn.t2.s1.gestionsocios.dtos.TipoDeUsuarioDTO;
import utn.t2.s1.gestionsocios.modelos.TipoDeUsuario;

@Component
public class TipoDeUsuarioConverter {
    public TipoDeUsuario toUsuario(TipoDeUsuarioDTO tipoDeUsuarioDTO) {
        TipoDeUsuario tipoDeUsuario = new TipoDeUsuario();
        tipoDeUsuario.setTipo(tipoDeUsuarioDTO.getNombreTipoDeUsuario());
        return tipoDeUsuario;
    }
}
