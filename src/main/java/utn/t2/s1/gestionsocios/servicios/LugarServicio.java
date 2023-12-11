package utn.t2.s1.gestionsocios.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utn.t2.s1.gestionsocios.dtos.EventoDTO;
import utn.t2.s1.gestionsocios.dtos.LugarDTO;
import utn.t2.s1.gestionsocios.modelos.Evento;
import utn.t2.s1.gestionsocios.modelos.Lugar;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.repositorios.EventoRepo;
import utn.t2.s1.gestionsocios.repositorios.LugarRepo;

import java.util.Optional;

@Service
public class LugarServicio {

    @Autowired
    private LugarRepo lugarRepo;


    public Lugar agregar(LugarDTO lugarDTO) {

        ModelMapper modelMapper = new ModelMapper();
        Lugar lugar = modelMapper.map(lugarDTO, Lugar.class);

        if(lugar.getDireccion() != null){
            if (lugar.getDireccion().isEmpty()){
                lugar.setDireccion(null);
            }
        }

        if(lugar.getLinkVirtual() != null){
            if (lugar.getLinkVirtual().isEmpty()){
                lugar.setLinkVirtual(null);
            }
        }

        if(lugar.getLinkMaps() != null){
            if (lugar.getLinkMaps().isEmpty()){
                lugar.setLinkMaps(null);
            }
        }

        lugar.setEstado(Estado.ACTIVO);

        lugar = lugarRepo.save(lugar);


  /*      try {
            evento = eventoRepo.save(evento);
        } catch (DataIntegrityViolationException e) {
            // Manejar la excepción de violación de restricción única (rol duplicado)
            throw new IllegalArgumentException("El nombre del participante ya existe en la base de datos.");
        }*/
        return lugar;
    }


    public Lugar actualizar(LugarDTO lugarDTO, long id) {
        Optional<Lugar> optionalLugar = lugarRepo.findById(id);
        if (!optionalLugar.isPresent()) {
            throw new EntityNotFoundException("Lugar no encontrado");
        }
        Lugar lugarUpdate = optionalLugar.get();
        lugarUpdate.setDireccion(lugarDTO.getDireccion());
        lugarUpdate.setLinkMaps(lugarDTO.getLinkMaps());
        lugarUpdate.setLinkVirtual(lugarDTO.getLinkVirtual());


        lugarUpdate = lugarRepo.save(lugarUpdate);

        return lugarUpdate;
    }


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
