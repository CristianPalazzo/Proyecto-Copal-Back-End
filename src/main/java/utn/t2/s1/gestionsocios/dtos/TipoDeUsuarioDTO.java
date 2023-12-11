package utn.t2.s1.gestionsocios.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TipoDeUsuarioDTO {
    @NotNull
    private String nombreTipoDeUsuario;
}
