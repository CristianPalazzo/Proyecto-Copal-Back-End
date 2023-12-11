package utn.t2.s1.gestionsocios.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    @NotNull
    @NotBlank( message = "Error, el usuario necesita un nombre")
    private String nombre;
    @NotNull
    @NotBlank
    private String contrasenia;
  //  @Schema( hidden = true)
  //  private Socio socio;
    @NotNull
    @NotBlank( message = "Error, el usuario necesita un tipo de usuario")
    private Long tipoDeUsuarioId;

    private Long socioId;

}