package utn.t2.s1.gestionsocios.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link utn.t2.s1.gestionsocios.modelos.Encargado}
 */
@Data
public class EncargadoDto implements Serializable {
    @NotNull
    @NotEmpty
    String nombre;
    @NotNull
    @NotEmpty
    String apellido;
    @NotNull
    @Email
    @NotEmpty
    String mail;
}