package utn.t2.s1.gestionsocios.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import utn.t2.s1.gestionsocios.modelos.Categoria;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.modelos.TipoSocio;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class SocioDTO {
    @NotNull
    @NotBlank( message = "Error, debe asignar una denominacion")
    private String denominacion;
    @NotNull
    @Pattern(regexp = "^\\d{2}-\\d{8}-\\d{1}$", message = "Debe ser XX-XXXXXXXX-X")
    private String cuit;
    @NotNull
    @Pattern(regexp = "^\\+54\s\\d{8,10}$", message = "Debe ser +54 XXXXXXXX")
    @Schema( type = "string",example = "+54 numero")
    private String telefono;
    @NotNull //TODO atrapar error valor fuera del enum
    private String tipo;
    @NotNull(message = "La dirección no puede ser nula")
    @Size(max = 254, message = "La dirección no puede ocupar mas de 254 caracteres")
    private String direccion;
    @NotNull
    @Email(message = "Debe ser una dirección de correo electrónico con formato correcto")
    @Schema( type = "string",example = "string@string.com")
    private String mail;
    @NotNull(message = "La descripcion no puede ser nula")
    @Size(max = 254, message = "La descripcion no puede ocupar mas de 254 caracteres")
    private String descripcion;
    @NotNull
    @URL(message = "La url debe ser https://www.algo.com")
    @Schema( type = "string",example = "https://www.string.com")
    private String web;
    @NotNull
    @DateTimeFormat( pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema( type = "string",example = "dd-MM-aaaa")
    private LocalDate fechaAlta; //TODO atrapar error fecha invalida
//    @NotNull
    @URL(message = "La url del logo debe ser https://www.algo.com")
    @Schema( type = "string",example = "https://www.string.com")
    private String logo;
    @NotNull
    private Set<String> categorias; //TODO ver si atrapar error de categoria ya que esta esta seleccionada

}
