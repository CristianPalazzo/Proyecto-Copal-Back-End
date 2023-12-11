package utn.t2.s1.gestionsocios.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.EstadoEvento;

@Data
public class EstadoEventoDTO {

    private EstadoEvento estadoEvento;

}
