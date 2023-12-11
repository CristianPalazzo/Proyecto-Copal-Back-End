package utn.t2.s1.gestionsocios.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

import java.util.List;

@Entity
@Data
@Table(name="estado_reserva")
public class EstadoReserva extends Persistente {

    @Column(nullable = false)
    private String nombre;

    //@JsonManagedReference
    @JsonBackReference
    @OneToMany(mappedBy = "estadoReserva")
    private List<Reserva> reservas;

}
