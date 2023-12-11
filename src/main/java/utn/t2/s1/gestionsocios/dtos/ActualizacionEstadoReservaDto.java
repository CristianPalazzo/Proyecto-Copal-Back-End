package utn.t2.s1.gestionsocios.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link utn.t2.s1.gestionsocios.modelos.EstadoReserva}
 */
@Data
public class ActualizacionEstadoReservaDto implements Serializable {

    @NotNull
    Long idEstadoReserva;

    String observaciones;


}