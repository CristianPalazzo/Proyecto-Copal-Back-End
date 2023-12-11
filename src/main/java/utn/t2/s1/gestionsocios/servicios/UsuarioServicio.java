package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.converters.UsuarioConverter;
import utn.t2.s1.gestionsocios.dtos.UsuarioDTOLogin;
import utn.t2.s1.gestionsocios.dtos.UsuarioDTO;
import utn.t2.s1.gestionsocios.excepciones.UsuarioContraseniaException;
import utn.t2.s1.gestionsocios.excepciones.UsuarioNombreException;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.TipoDeUsuario;
import utn.t2.s1.gestionsocios.modelos.TipoSocio;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.SocioRepo;
import utn.t2.s1.gestionsocios.repositorios.TipoDeUsuarioRepo;
import utn.t2.s1.gestionsocios.repositorios.UsuarioRepo;
import utn.t2.s1.gestionsocios.modelos.Usuario;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepo usuarioRepo;
    @Autowired
    private TipoDeUsuarioRepo tipoDeUsuarioRepo;
    @Autowired
    private UsuarioConverter usuarioConverter;
    @Autowired
    private SocioRepo socioRepo;


    public Page<Usuario> traerUsuarios(Pageable pageable){
        return usuarioRepo.findAllByEstado(pageable, Estado.ACTIVO);
    }


    public Usuario agregar(UsuarioDTO usuarioDTO){

        Optional<TipoDeUsuario> optionalTipoDeUsuario = tipoDeUsuarioRepo.findById(usuarioDTO.getTipoDeUsuarioId());
        if (!optionalTipoDeUsuario.isPresent()) {
            throw new RuntimeException("Tipo de Usuario no encontrado");
        }

        Usuario usuario = usuarioConverter.toUsuario(usuarioDTO);
        usuario.setTipoDeUsuario(optionalTipoDeUsuario.get());

        Optional<Socio> optionalSocio;
        if (usuarioDTO.getSocioId() != null) {
            optionalSocio = socioRepo.findById(usuarioDTO.getSocioId());
            if (!optionalSocio.isPresent()) {
                throw new RuntimeException("Usuario no encontrado");
            }
            usuario.setSocio(optionalSocio.get());

        }


        usuario.setEstado(Estado.ACTIVO);
        return usuarioRepo.save(usuario);
    }


    public Usuario buscarUsuario(UsuarioDTOLogin usuarioDTOLogin) throws UsuarioNombreException, UsuarioContraseniaException {
        Optional<Usuario> usuario = buscarPorNombre(usuarioDTOLogin.getNombre());
        if (usuario.isPresent()){

            if(!usuario.get().getContrasenia().equals(usuarioDTOLogin.getContrasenia())){ //no coinciden la contraseña con el nombre
                throw new UsuarioContraseniaException();
            }

        }else{ //no existe el usuario
            throw new UsuarioNombreException(  );
        }
        Usuario usuarioUpdate = usuario.get();
        return usuarioUpdate;
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepo.findByIdAndEstado(id, Estado.ACTIVO).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    public Optional<Usuario> buscarPorNombre(String nombreUsuario){
        return usuarioRepo.findByNombreAndEstado(nombreUsuario, Estado.ACTIVO);
    }

    public void eliminarUsuario(Long id){
        Usuario usuario = this.buscarPorId(id);
        usuario.setEstado(Estado.ELIMINADO);
        usuarioRepo.save(usuario);
    }

    public Usuario actualizar(Long id, UsuarioDTO usuarioDTO) {

        Usuario usuario1 = buscarPorId(id);

        Optional<TipoDeUsuario> optionalTipoDeUsuario = tipoDeUsuarioRepo.findById(usuarioDTO.getTipoDeUsuarioId());
        if (!optionalTipoDeUsuario.isPresent()) {
            throw new RuntimeException("Tipo de Usuario no encontrado");
        }

        Usuario usuario = usuarioConverter.toUsuario(usuarioDTO, usuario1);
        usuario.setTipoDeUsuario(optionalTipoDeUsuario.get());

        Optional<Socio> optionalSocio;
        if (usuarioDTO.getSocioId() != null) {
            optionalSocio = socioRepo.findById(usuarioDTO.getSocioId());
            if (!optionalSocio.isPresent()) {
                throw new RuntimeException("Socio no encontrado");
            }
            usuario.setSocio(optionalSocio.get());
        }

        return usuarioRepo.save(usuario);
    }


}
