package utn.t2.s1.gestionsocios.modelos;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

import java.util.List;

@Data
@Entity
@Table(name = "sub_departamento")
public class SubDepartamento extends Persistente {

    @Column(name = "nombre", nullable = false)
    private String nombreSubDepartamento;
    @Column(name = "objetivo", nullable = false,length = 500)
    private String objetivo;
    @Column(name="logo", nullable = true, length = 400)
    private String logo;


    @OneToMany(mappedBy = "subDepartamento", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AutoridadSubDepartamento> autoridadSubDepartamentos;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;


}
