package utn.t2.s1.gestionsocios.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AutoridadDTO {

    @NotNull
    private Long usuarioId;
    @NotNull
    private Long rolId;
}
