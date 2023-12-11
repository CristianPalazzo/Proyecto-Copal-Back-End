package utn.t2.s1.gestionsocios.converters;

import org.springframework.stereotype.Component;
import utn.t2.s1.gestionsocios.dtos.ReservaDto;
import utn.t2.s1.gestionsocios.dtos.RolDTO;
import utn.t2.s1.gestionsocios.modelos.Reserva;
import utn.t2.s1.gestionsocios.modelos.Rol;
import utn.t2.s1.gestionsocios.persistencia.Estado;


@Component
public class ReservaConverter {
    public Reserva toReserva(ReservaDto reservaDto) {

        Reserva reserva = new Reserva();
        this.toReserva(reservaDto, reserva);

        return reserva;
    }

    public Reserva toReserva(ReservaDto reservaDto, Reserva reserva){

        reserva.setDescripcion(reservaDto.getDescripcion());
        reserva.setFechaInicio(reservaDto.getFechaInicio());
        reserva.setFechaFin(reservaDto.getFechaFin());


        reserva.setObservaciones(reservaDto.getObservaciones());

        return reserva;
    }
}
