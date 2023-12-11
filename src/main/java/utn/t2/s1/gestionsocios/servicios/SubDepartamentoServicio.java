package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.converters.UsuarioConverter;
import utn.t2.s1.gestionsocios.dtos.AutoridadDTO;
import utn.t2.s1.gestionsocios.dtos.SubDepartamentoDTO;
import utn.t2.s1.gestionsocios.dtos.UsuarioDTO;
import utn.t2.s1.gestionsocios.dtos.UsuarioDTOLogin;
import utn.t2.s1.gestionsocios.excepciones.SubDepartamentoException;
import utn.t2.s1.gestionsocios.excepciones.UsuarioContraseniaException;
import utn.t2.s1.gestionsocios.excepciones.UsuarioNombreException;
import utn.t2.s1.gestionsocios.modelos.*;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubDepartamentoServicio {
    @Autowired
    private SubDepartamentoRepo subDepartamentoRepo;
    @Autowired
    private DepartamentoRepo departamentoRepo;
    @Autowired
    private LogoServicio logoServicio;


    public SubDepartamento buscarPorId(Long id) throws SubDepartamentoException {

        SubDepartamento subDepartamento= subDepartamentoRepo.findByIdAndEstado(id, Estado.ACTIVO).orElseThrow(() -> new EntityNotFoundException("SubDepartamento no encontrado"));

        subDepartamento = filtrarAutoridadesActivas(subDepartamento);

        return subDepartamento;
    }

    public Page<SubDepartamento> traerSubDepartamentosPorDepartamento(Pageable pageable , Long departamentoId){
        // List<AutoridadDepartamento> autoridades = departamentoRepo.findById(departamentoId).get().getAutoridadDepartamentos();

        Page<SubDepartamento> subDepartamentoPage = subDepartamentoRepo.findAllByDepartamentoIdAndEstado(pageable, departamentoId, Estado.ACTIVO);

        subDepartamentoPage.stream().map(this::filtrarAutoridadesActivas).collect(Collectors.toList());

        return subDepartamentoPage;
    }

    public SubDepartamento filtrarAutoridadesActivas(SubDepartamento subDepartamento){
        List<AutoridadSubDepartamento> autoridadSubDepartamentos = subDepartamento.getAutoridadSubDepartamentos().stream()
                .filter((y) -> y.getEstado().equals(Estado.ACTIVO))
                .collect(Collectors.toList());
        subDepartamento.setAutoridadSubDepartamentos(autoridadSubDepartamentos);

        return subDepartamento;
    }

    public SubDepartamento agregar(SubDepartamentoDTO subDepartamentoDTO){

        Optional<Departamento> optionalDepartamento = departamentoRepo.findByIdAndEstado(subDepartamentoDTO.getIdDepartamento(), Estado.ACTIVO);
        if (!optionalDepartamento.isPresent()) {
            throw new EntityNotFoundException("Departamento no encontrado");
        }

        SubDepartamento subDepartamento = new SubDepartamento();
        subDepartamento.setDepartamento(optionalDepartamento.get());
        subDepartamento.setNombreSubDepartamento(subDepartamentoDTO.getNombre());
        subDepartamento.setObjetivo(subDepartamentoDTO.getObjetivo());
        subDepartamento.setLogo(subDepartamentoDTO.getLogo());
        subDepartamento.setEstado(Estado.ACTIVO);

        return subDepartamentoRepo.save(subDepartamento);
    }




    public Optional<SubDepartamento> buscarPorNombreYPorDepartamento(String nombreUsuario, Long idDepartamento){
        return subDepartamentoRepo.findByNombreSubDepartamentoAndEstadoAndDepartamento_Id(nombreUsuario,Estado.ACTIVO, idDepartamento);
    }

    public void eliminarSubDepartamento(Long id) throws SubDepartamentoException{
        
        SubDepartamento subDepartamento = this.buscarPorId(id);

        logoServicio.deletePorSubDepartamento(subDepartamento.getId());
        subDepartamento.setLogo(null);

        subDepartamento.setEstado(Estado.ELIMINADO);
        subDepartamentoRepo.save(subDepartamento);
    }

    public SubDepartamento actualizar(Long id, SubDepartamentoDTO subDepartamentoDTO) {

        Optional<SubDepartamento> optionalSubDepartamento = subDepartamentoRepo.findById(id);
        if (!optionalSubDepartamento.isPresent()) {
            throw new RuntimeException("SubDepartamento no encontrado");
        }

        SubDepartamento subDepartamento = optionalSubDepartamento.get();
        subDepartamento.setNombreSubDepartamento(subDepartamentoDTO.getNombre());
        subDepartamento.setObjetivo(subDepartamentoDTO.getObjetivo());
        subDepartamento.setLogo(subDepartamentoDTO.getLogo());


        return subDepartamentoRepo.save(subDepartamento);
    }


}
