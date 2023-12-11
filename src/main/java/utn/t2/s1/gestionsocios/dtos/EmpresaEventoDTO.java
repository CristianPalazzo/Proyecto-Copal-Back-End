package utn.t2.s1.gestionsocios.dtos;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link utn.t2.s1.gestionsocios.modelos.EmpresaEvento}
 */
@Data
public class EmpresaEventoDTO implements Serializable {
    Long socioId;
    String otraEmpresa;
}