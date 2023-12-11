package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.converters.UsuarioConverter;
import utn.t2.s1.gestionsocios.dtos.AutoridadDTO;
import utn.t2.s1.gestionsocios.excepciones.AutoridadSubDepartamentoException;
import utn.t2.s1.gestionsocios.modelos.*;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.*;

import java.util.Optional;

@Service
public class AutoridadSubDepartamentoServicio {
    @Autowired
    private AutoridadSubDepartamentoRepo autoridadSubDepartamentoRepo;
    @Autowired
    private UsuarioRepo usuarioRepo;   // tipo de usuario
    @Autowired
    private UsuarioConverter usuarioConverter;
    @Autowired
    private RolRepo rolRepo;  // socio
    @Autowired
    private SubDepartamentoRepo subDepartamentoRepo;

    public Page<AutoridadSubDepartamento> traerAutoridadesPorSubDepartamento(Pageable pageable , Long departamentoId){
        return autoridadSubDepartamentoRepo.findAllBySubDepartamentoIdAndEstado(pageable, departamentoId, Estado.ACTIVO);
    }


    public Page<AutoridadSubDepartamento> traerSubAutoridadesDepartamento(Pageable pageable){
        return autoridadSubDepartamentoRepo.findAllByEstado(pageable, Estado.ACTIVO);
    }

    public AutoridadSubDepartamento agregar(Long subDepartamentoId, AutoridadDTO autoridadDTO){

        Optional<Usuario> optionalUsuario = usuarioRepo.findByIdAndEstado(autoridadDTO.getUsuarioId(), Estado.ACTIVO);
        if (!optionalUsuario.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Optional<Rol> optionalRol = rolRepo.findByIdAndEstado(autoridadDTO.getRolId(), Estado.ACTIVO);
        if (!optionalRol.isPresent()) {
            throw new RuntimeException("Rol no encontrado");
        }

        Optional<SubDepartamento> optionalSubDepartamento = subDepartamentoRepo.findByIdAndEstado(subDepartamentoId, Estado.ACTIVO);
        if (!optionalSubDepartamento.isPresent()) {
            throw new RuntimeException("SubDepartamento no encontrado");
        }


        AutoridadSubDepartamento autoridadSubDepartamento = new AutoridadSubDepartamento();
        autoridadSubDepartamento.setSubDepartamento(optionalSubDepartamento.get());
        autoridadSubDepartamento.setRol(optionalRol.get());
        autoridadSubDepartamento.setUsuario(optionalUsuario.get());
        autoridadSubDepartamento.setEstado( Estado.ACTIVO);

        return autoridadSubDepartamentoRepo.save(autoridadSubDepartamento);
    }



    public AutoridadSubDepartamento buscarPorId(Long id) throws AutoridadSubDepartamentoException {
        return autoridadSubDepartamentoRepo.findByIdAndEstado(id, Estado.ACTIVO).orElseThrow(() -> new EntityNotFoundException("Autoridad no encontrada"));
    }

//    public Optional<Usuario> buscarPorNombre(String nombreUsuario){
//        return usuarioRepo.findByNombre(nombreUsuario);
//    }

    public void eliminarAutoridadSubDepartamento(Long id) throws AutoridadSubDepartamentoException{
        AutoridadSubDepartamento autoridadSubDepartamento = this.buscarPorId(id);
        autoridadSubDepartamento.setEstado(Estado.ELIMINADO);
        autoridadSubDepartamentoRepo.save(autoridadSubDepartamento);
    }


    public AutoridadSubDepartamento actualizar(Long autoridadId, AutoridadDTO autoridadDto) {

        Optional<AutoridadSubDepartamento> optionalAutoridadSubDepartamento = autoridadSubDepartamentoRepo.findByIdAndEstado(autoridadId, Estado.ACTIVO);
        if (!optionalAutoridadSubDepartamento.isPresent()) {
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


        AutoridadSubDepartamento autoridadSubDepartamento = optionalAutoridadSubDepartamento.get();
        autoridadSubDepartamento.setRol(optionalRol.get());
        autoridadSubDepartamento.setUsuario(optionalUsuario.get());
        autoridadSubDepartamento.setEstado(Estado.ACTIVO);


        return autoridadSubDepartamentoRepo.save(autoridadSubDepartamento);
    }


}
