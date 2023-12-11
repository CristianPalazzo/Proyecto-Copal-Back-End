package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.dtos.DepartamentoDTO;
import utn.t2.s1.gestionsocios.excepciones.DepartamentoException;
import utn.t2.s1.gestionsocios.modelos.AutoridadDepartamento;
import utn.t2.s1.gestionsocios.modelos.AutoridadSubDepartamento;
import utn.t2.s1.gestionsocios.modelos.Departamento;
import utn.t2.s1.gestionsocios.modelos.SubDepartamento;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.DepartamentoRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartamentoServicio {

    @Autowired
    private DepartamentoRepo departamentoRepo;


    @Autowired
    private LogoServicio logoServicio;


    public Page<Departamento> traerDepartamentos(Pageable pageable){
        Page<Departamento> departamentoPage = departamentoRepo.findAllByEstado(pageable, Estado.ACTIVO);
        departamentoPage.stream().map(this::filtrarSubDepartamentosActivos).collect(Collectors.toList());

        departamentoPage.stream().map(this::filtrarAutoridadesActivas).collect(Collectors.toList());

        return departamentoPage;
    }



    public Departamento buscarPorId(Long id) throws DepartamentoException {
        Departamento departamento = departamentoRepo.findByIdAndEstado(id, Estado.ACTIVO).orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado"));

        departamento = filtrarAutoridadesActivas(departamento);
        departamento = filtrarSubDepartamentosActivos(departamento);

        return departamento;
    }

    //-----------------------------------------------Principio FILTROS--------------------------------------------------//
    public Departamento filtrarSubDepartamentosActivos(Departamento departamento){
        List<SubDepartamento> subDepartamentoList = departamento.getSubDepartamentoList().stream()
                .filter((y) -> y.getEstado().equals(Estado.ACTIVO))
                .collect(Collectors.toList());
        departamento.setSubDepartamentoList(subDepartamentoList);
        return departamento;

    }

    //Este filtra autoridades activas por departamento, y llama al filtro de autoridades activas por sub departamento
    public Departamento filtrarAutoridadesActivas(Departamento departamento){
        List<AutoridadDepartamento> autoridadDepartamentos = departamento.getAutoridadDepartamentos().stream()
                .filter((y) -> y.getEstado().equals(Estado.ACTIVO))
                .collect(Collectors.toList());
        departamento.setAutoridadDepartamentos(autoridadDepartamentos);

        departamento.getSubDepartamentoList().stream().map(this::filtrarAutoridadesActivasSubDepartamento).collect(Collectors.toList());

        return departamento;
    }

    //Filtra autoridades activas por sub departamento
    public SubDepartamento filtrarAutoridadesActivasSubDepartamento(SubDepartamento subDepartamento){
        List<AutoridadSubDepartamento> autoridadDepartamentos = subDepartamento.getAutoridadSubDepartamentos().stream()
                .filter((y) -> y.getEstado().equals(Estado.ACTIVO))
                .collect(Collectors.toList());
        subDepartamento.setAutoridadSubDepartamentos(autoridadDepartamentos);
        return subDepartamento;
    }
    //-----------------------------------------------Fin FILTROS--------------------------------------------------//



    public Departamento agregar(DepartamentoDTO departamentoDTO){


        Departamento departamento = new Departamento();
        departamento.setNombreDepartamento(departamentoDTO.getNombre());
        departamento.setObjetivo(departamentoDTO.getObjetivo());
        departamento.setLogo(departamentoDTO.getLogo());
        departamento.setEstado(Estado.ACTIVO);

        return departamentoRepo.save(departamento);
    }



    public Optional<Departamento> buscarPorNombre(String nombreUsuario){
        return departamentoRepo.findByNombreDepartamentoAndEstado(nombreUsuario, Estado.ACTIVO);
    }

    public void eliminarDepartamento(Long id) throws DepartamentoException{
        Departamento departamento = this.buscarPorId(id);
        //eliminar imagen
        logoServicio.deletePorDepartamento(departamento.getId());
        departamento.setLogo(null);

        departamento.setEstado(Estado.ELIMINADO);

        departamentoRepo.save(departamento);
    }

    public Departamento actualizar(Long id, DepartamentoDTO departamentoDTO) {

        Optional<Departamento> optionalDepartamento = departamentoRepo.findByIdAndEstado(id, Estado.ACTIVO);
        if (!optionalDepartamento.isPresent()) {
            throw new RuntimeException("SubDepartamento no encontrado");
        }

        Departamento departamentoUpdate = optionalDepartamento.get();
        departamentoUpdate.setNombreDepartamento(departamentoDTO.getNombre());
        departamentoUpdate.setObjetivo(departamentoDTO.getObjetivo());
        departamentoUpdate.setLogo(departamentoDTO.getLogo());


        return departamentoRepo.save(departamentoUpdate);
    }


}
