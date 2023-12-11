package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.dtos.EmpresaEventoDTO;
import utn.t2.s1.gestionsocios.dtos.LugarDTO;
import utn.t2.s1.gestionsocios.modelos.EmpresaEvento;
import utn.t2.s1.gestionsocios.modelos.Lugar;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.EmpresaEventoRepo;
import utn.t2.s1.gestionsocios.repositorios.LugarRepo;

import java.util.Optional;

@Service
public class EmpresaEventoServicio {

    @Autowired
    private EmpresaEventoRepo empresaEventoRepo;

    @Autowired
    private SocioServicio socioService;


    public EmpresaEvento agregar(EmpresaEventoDTO empresaEventoDTO) {

        ModelMapper modelMapper = new ModelMapper();
        EmpresaEvento empresaEvento = modelMapper.map(empresaEventoDTO, EmpresaEvento.class);


        if (empresaEventoDTO.getSocioId() != null) {
            Socio socio = socioService.buscarPorId(empresaEventoDTO.getSocioId());
            empresaEvento.setSocio(socio);
        }

        if (empresaEvento.getSocio() != null && empresaEvento.getOtraEmpresa() != null) {
            throw new IllegalArgumentException("No se puede asignar un socio y otra empresa al mismo tiempo.");
        }

        if (empresaEvento.getSocio() == null && empresaEvento.getOtraEmpresa() == null) {
            throw new IllegalArgumentException("Se debe asignar un socio o una empresa");
        }




        empresaEvento.setEstado(Estado.ACTIVO);

        empresaEvento = empresaEventoRepo.save(empresaEvento);


  /*      try {
            evento = eventoRepo.save(evento);
        } catch (DataIntegrityViolationException e) {
            // Manejar la excepción de violación de restricción única (rol duplicado)
            throw new IllegalArgumentException("El nombre del participante ya existe en la base de datos.");
        }*/
        return empresaEvento;
    }

//
//    public Lugar actualizar(LugarDTO lugarDTO, long id) {
//        Optional<Lugar> optionalLugar = lugarRepo.findById(id);
//        if (!optionalLugar.isPresent()) {
//            throw new EntityNotFoundException("Lugar no encontrado");
//        }
//        Lugar lugarUpdate = optionalLugar.get();
//        lugarUpdate.setDireccion(lugarDTO.getDireccion());
//        lugarUpdate.setLinkMaps(lugarDTO.getLinkMaps());
//        lugarUpdate.setLinkVirtual(lugarDTO.getLinkVirtual());
//
//
//        lugarUpdate = lugarRepo.save(lugarUpdate);
//
//        return lugarUpdate;
//    }


//    public void eliminar(long id) {
//
//        Optional<Evento> optionalParticipante = eventoRepo.findById(id);
//        if (!optionalParticipante.isPresent() || optionalParticipante.get().getEstado() == Estado.ELIMINADO) {
//            throw new EntityNotFoundException("Participante no encontrado");
//        }
//
//        Evento evento = optionalParticipante.get();
//
//        evento.setEstado(Estado.ELIMINADO);
//
//        eventoRepo.save(evento);
//    }

}
