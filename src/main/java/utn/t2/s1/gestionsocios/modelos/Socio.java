package utn.t2.s1.gestionsocios.modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.t2.s1.gestionsocios.persistencia.Persistente;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="socio")
public class Socio extends Persistente {

    @Column(name="denominacion", nullable = false)
    private String denominacion;
    @Column(name="cuit", nullable = false)
    private String cuit;
    @Column(name="telefono", nullable = false)
    private String telefono;

    @JoinColumn(name="tipo", nullable = false)
    @ManyToOne()  // chequear
    private TipoSocio tipo;
    @Column(name="direccion", nullable = false)
    private String direccion;
    @Column(name="mail", nullable = false)
    private String mail;
    @Column(name="descripcion", nullable = false)
    private String descripcion;
    @Column(name="web", nullable = false)
    private String web;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaAlta; //TODO formatear fecha dd-MM-yyyy
    @Column(name="logo", nullable = true)
    private String logo;
    @ManyToMany()
    private Set<Categoria> categorias =new HashSet<>(); ;
}