package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;


@Entity
@Data
public class Rol extends Persistente {

    @Column(name="nombre_rol", nullable = false)
    private String nombreRol;



}
