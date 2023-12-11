package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.converters.UsuarioConverter;
import utn.t2.s1.gestionsocios.dtos.*;
import utn.t2.s1.gestionsocios.excepciones.UsuarioContraseniaException;
import utn.t2.s1.gestionsocios.excepciones.UsuarioNombreException;
import utn.t2.s1.gestionsocios.modelos.*;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.*;

import java.util.List;
import java.util.Optional;

@Service
public class AutoridadDepartamentoServicio {
    @Autowired
    private AutoridadDepartamentoRepo autoridadDepartamentoRepo;
    @Autowired
    private UsuarioRepo usuarioRepo;   // tipo de usuario
    @Autowired
    private UsuarioConverter usuarioConverter;
    @Autowired
    private RolRepo rolRepo;  // socio
    @Autowired
    private DepartamentoRepo departamentoRepo;

    public Page<AutoridadDepartamento> traerAutoridadesPorDepartamento(Pageable pageable ,Long departamentoId){
       // List<AutoridadDepartamento> autoridades = departamentoRepo.findById(departamentoId).get().getAutoridadDepartamentos();

        return autoridadDepartamentoRepo.findAllByDepartamentoIdAndEstado(pageable, departamentoId, Estado.ACTIVO);
    }


    public Page<AutoridadDepartamento> traerAutoridadesDepartamento(Pageable pageable){
        return autoridadDepartamentoRepo.findAllByEstado(pageable, Estado.ACTIVO);
    }

    public AutoridadDepartamento agregar(Long departamentoId, AutoridadDTO autoridadDTO){

        Optional<Usuario> optionalUsuario = usuarioRepo.findByIdAndEstado(autoridadDTO.getUsuarioId(), Estado.ACTIVO);
        if (!optionalUsuario.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Optional<Rol> optionalRol = rolRepo.findByIdAndEstado(autoridadDTO.getRolId(), Estado.ACTIVO);
        if (!optionalRol.isPresent()) {
            throw new RuntimeException("Rol no encontrado");
        }

        Optional<Departamento> optionalDepartamento = departamentoRepo.findByIdAndEstado(departamentoId, Estado.ACTIVO);
        if (!optionalDepartamento.isPresent()) {
            throw new RuntimeException("Departamento no encontrado");
        }


        AutoridadDepartamento autoridadDepartamento = new AutoridadDepartamento();
        autoridadDepartamento.setDepartamento(optionalDepartamento.get());
        autoridadDepartamento.setRol(optionalRol.get());
        autoridadDepartamento.setUsuario(optionalUsuario.get());
        autoridadDepartamento.setEstado( Estado.ACTIVO);

        return autoridadDepartamentoRepo.save(autoridadDepartamento);
    }


//    public Usuario buscarAutoridad(UsuarioDTOLogin usuarioDTOLogin) throws UsuarioNombreException, UsuarioContraseniaException {
//        Optional<Usuario> usuario = buscarPorNombre(usuarioDTOLogin.getNombre());
//        if (usuario.isPresent()){
//
//            if(!usuario.get().getContrasenia().equals(usuarioDTOLogin.getContrasenia())){ //no coinciden la contraseña con el nombre
//                throw new UsuarioContraseniaException();
//            }
//
//        }else{ //no existe el usuario
//            throw new UsuarioNombreException(  );
//        }
//        Usuario usuarioUpdate = usuario.get();
//        return usuarioUpdate;
//    }

    public AutoridadDepartamento buscarPorId(Long id) {
        return autoridadDepartamentoRepo.findByIdAndEstado(id, Estado.ACTIVO).orElseThrow(() -> new EntityNotFoundException("Autoridad no encontrada"));
    }

//    public Optional<Usuario> buscarPorNombre(String nombreUsuario){
//        return usuarioRepo.findByNombre(nombreUsuario);
//    }

    public void eliminarAutoridadDepartamento(Long id){
        AutoridadDepartamento autoridadDepartamento = this.buscarPorId(id);
        autoridadDepartamento.setEstado(Estado.ELIMINADO);
        autoridadDepartamentoRepo.save(autoridadDepartamento);
    }

    public AutoridadDepartamento actualizar(Long autoridadId, AutoridadDTO autoridadDto) {

        Optional<AutoridadDepartamento> optionalAutoridadDepartamento = autoridadDepartamentoRepo.findByIdAndEstado(autoridadId, Estado.ACTIVO);
        if (!optionalAutoridadDepartamento.isPresent()) {
            throw new RuntimeException("Autoridad no encontrado");
        }

        Optional<Usuario> optionalUsuario = usuarioRepo.findByIdAndEstado(autoridadDto.getUsuarioId(), Estado.ACTIVO);
        if (!optionalUsuario.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Optional<Rol> optionalRol = rolRepo.findByIdAndEstado(autoridadDto.getRolId(), Estado.ACTIVO);
        if (!optionalRol.isPresent()) {
            throw new RuntimeException("Rol no encontrado");
        }


        AutoridadDepartamento autoridadDepartamento = optionalAutoridadDepartamento.get();
        autoridadDepartamento.setRol(optionalRol.get());
        autoridadDepartamento.setUsuario(optionalUsuario.get());
        autoridadDepartamento.setEstado(Estado.ACTIVO);

       /* Optional<AutoridadDepartamento> optionalAutoridad;
        if (usuarioDTO.getSocioId() != null) {
            optionalSocio = socioRepo.findById(usuarioDTO.getSocioId());
            if (!optionalSocio.isPresent()) {
                throw new RuntimeException("Usuario no encontrado");
            }
            usuario.setSocio(optionalSocio.get());

        }*/

        return autoridadDepartamentoRepo.save(autoridadDepartamento);
    }


}
