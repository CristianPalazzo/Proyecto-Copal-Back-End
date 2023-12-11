package utn.t2.s1.gestionsocios.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import utn.t2.s1.gestionsocios.persistencia.EstadoEvento;
import utn.t2.s1.gestionsocios.persistencia.Modalidad;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * DTO for {@link utn.t2.s1.gestionsocios.modelos.Evento}
 */
@Value
public class EventoDTO implements Serializable {
    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime fechaInicio;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime fechaFin;

//    String lugar;

    @NotNull
    LugarDTO lugar;

    @NotNull
    @NotEmpty
    @Length(max = 1000)
    String descripcion;

    @NotNull
    Modalidad modalidad;

    @NotNull
    EstadoEvento estadoEvento;


    @NotNull
    @NotEmpty
    String nombre;
}