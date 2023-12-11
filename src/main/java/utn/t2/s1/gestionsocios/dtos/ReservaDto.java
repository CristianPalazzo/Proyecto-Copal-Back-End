package utn.t2.s1.gestionsocios.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link utn.t2.s1.gestionsocios.modelos.Reserva}
 */
@Data
public class ReservaDto implements Serializable {
    @NotNull
    @NotEmpty
    @Length(max = 500)
    String descripcion;

    @NotNull
    @FutureOrPresent
    LocalDateTime fechaInicio;

    @NotNull
    @FutureOrPresent
    LocalDateTime fechaFin;

    List<RecursoDto> recursos;

    EspacioFisicoDto espacioFisico;

    ActualizacionEstadoReservaDto estadoReserva;

    EncargadoDto encargado;

    Long subDepartamentoId;

    String observaciones;
}