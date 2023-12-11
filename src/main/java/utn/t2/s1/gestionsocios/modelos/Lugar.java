package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

@Entity
@Data
@Table(name="lugar")
public class Lugar extends Persistente {

    @Column(name = "direccion", nullable = true)
    private String direccion;

    @Column(name = "link_maps", nullable = true)
    private String linkMaps;

    @Column(name = "link_virtual", nullable = true)
    private String linkVirtual;

}
