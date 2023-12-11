package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

@Data
@Entity
@Table(name="tipo_de_usuario")
public class TipoDeUsuario extends Persistente {

    @Column(name="tipo")
    private String tipo;
}
