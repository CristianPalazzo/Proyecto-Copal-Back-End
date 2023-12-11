package utn.t2.s1.gestionsocios.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utn.t2.s1.gestionsocios.dtos.UsuarioDTOLogin;
import utn.t2.s1.gestionsocios.dtos.UsuarioDTO;
import utn.t2.s1.gestionsocios.modelos.Usuario;

@Component
public class UsuarioConverter {

    @Autowired
    TipoDeUsuarioConverter tipoDeUsuarioConverter;

    public Usuario toUsuario(UsuarioDTOLogin usuarioDTOLogin) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTOLogin.getNombre());
        usuario.setContrasenia(usuarioDTOLogin.getContrasenia());
        return usuario;
    }

    public Usuario toUsuario(UsuarioDTO usuarioDTO, Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setContrasenia(usuarioDTO.getContrasenia());
        //   usuario.setTipoDeUsuario(tipoDeUsuarioConverter.toUsuario( usuarioDTOSignUp.getTipoDeUsuario()));
        return usuario;
    }

    public Usuario toUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setContrasenia(usuarioDTO.getContrasenia());
     //   usuario.setTipoDeUsuario(tipoDeUsuarioConverter.toUsuario( usuarioDTOSignUp.getTipoDeUsuario()));
        return usuario;
    }
}
