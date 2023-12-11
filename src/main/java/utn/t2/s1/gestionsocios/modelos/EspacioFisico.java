package utn.t2.s1.gestionsocios.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

import java.util.List;

@Entity
@Data
@Table(name="espacio_fisico")
public class EspacioFisico extends Persistente {

    @Column(nullable = false)
    private String nombre;


//    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "espacioFisico")
    private List<Reserva> reservas;

}
