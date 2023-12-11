package utn.t2.s1.gestionsocios.converters;

import org.springframework.stereotype.Component;
import utn.t2.s1.gestionsocios.dtos.EventoDTO;
import utn.t2.s1.gestionsocios.dtos.ParticipanteDTO;
import utn.t2.s1.gestionsocios.modelos.Evento;
import utn.t2.s1.gestionsocios.modelos.Participante;

@Component
public class ParticipanteConverter {
    public Participante toParticipante(ParticipanteDTO participanteDTO) {

        Participante participante = new Participante();
        this.toParticipante(participanteDTO, participante);

        return participante;
    }


    public Participante toParticipante(ParticipanteDTO participanteDTO, Participante participante) {

        participante.setNombre(participanteDTO.getNombre());
        participante.setApellido(participanteDTO.getApellido());
        participante.setMail(participanteDTO.getMail());
        participante.setReferente(participanteDTO.getReferente());

        return participante;
    }

}
