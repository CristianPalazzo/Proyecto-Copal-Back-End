package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="tipo_socio")
public class TipoSocio extends Persistente {

    @Column(name="nombre")
    private String nombre;
}
