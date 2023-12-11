package utn.t2.s1.gestionsocios.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

import java.util.Set;


@Entity
@Data
@Table(name="recursos")
public class Recurso extends Persistente {

    @Column(nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "recursos")
    @JsonIgnore
    private Set<Reserva> reserva;


}
