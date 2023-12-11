package utn.t2.s1.gestionsocios.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "reserva")
public class Reserva extends Persistente {

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;


    @Column(name = "codigo_de_seguimiento", unique = true)
    private String codigoDeSeguimiento;

    @Column(name = "observaciones")
    private String observaciones;


    @ManyToMany
    @JoinTable(name = "recursos_reserva",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "recurso_id"))
    private List<Recurso> recursos;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "espacio_fisico_id")
    private EspacioFisico espacioFisico;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "estado_reserva_id")
    private EstadoReserva estadoReserva;

    @OneToOne(cascade = CascadeType.ALL)
    private Encargado encargado;

    @ManyToOne
    @JoinColumn(name = "sub_departamento_id")
    private SubDepartamento subDepartamento;


    @PrePersist
    public void generarCodigoDeSeguimiento() {
        this.codigoDeSeguimiento = UUID.randomUUID().toString();
    }

}
