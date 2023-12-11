package utn.t2.s1.gestionsocios.dtos;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link utn.t2.s1.gestionsocios.modelos.Lugar}
 */
@Value
@Data
public class LugarDTO implements Serializable {
    String direccion;
    String linkMaps;
    String linkVirtual;
}