package utn.t2.s1.gestionsocios.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link utn.t2.s1.gestionsocios.modelos.EspacioFisico}
 */
@Data
public class EspacioFisicoDto implements Serializable {
    @NotNull
    Long id;
}