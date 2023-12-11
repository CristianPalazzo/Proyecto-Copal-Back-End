package utn.t2.s1.gestionsocios.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Data
public class SubDepartamentoDTO {

    private Long idDepartamento;

    @NotNull
    private String nombre;

    @NotNull
    private String objetivo;

    @URL(message = "La url del logo debe ser https://www.algo.com")
    @Schema( type = "string",example = "https://www.string.com")
    private String logo;

}
