package utn.t2.s1.gestionsocios.modelos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import utn.t2.s1.gestionsocios.persistencia.Estado;
import utn.t2.s1.gestionsocios.persistencia.EstadoEvento;
import utn.t2.s1.gestionsocios.persistencia.Modalidad;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name="evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonIgnore
    private Estado estado;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_de_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @OneToOne(cascade = CascadeType.ALL)
    private Lugar lugar;

    @Column(name = "descripcion", nullable = false)
    @Length(max = 1000)
    private String descripcion;
    @Column(name = "modalidad", nullable = false)
    private Modalidad modalidad;

    @Column(name = "participantes", nullable = false)
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Participante> participantes;

    @Column(name = "estado_evento", nullable = false)
    private EstadoEvento estadoEvento;



}




