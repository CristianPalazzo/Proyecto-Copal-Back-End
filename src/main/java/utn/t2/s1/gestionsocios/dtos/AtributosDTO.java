package utn.t2.s1.gestionsocios.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtributosDTO {
    @NotNull
    @NotBlank( message = "Error, el nombre es obligatorio")
    private String nombre;
}
