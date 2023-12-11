package utn.t2.s1.gestionsocios.modelos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

import java.util.List;

@Data
@Entity
public class Departamento extends Persistente {

    @Column(name = "nombre", nullable = false)
    private String nombreDepartamento;
    @Column(name = "objetivo", nullable = false,length = 500)
    private String objetivo;
    @Column(name="logo", nullable = true, length = 400)
    private String logo;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AutoridadDepartamento> autoridadDepartamentos;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SubDepartamento> subDepartamentoList;


//    @ElementCollection
//    @CollectionTable(name = "autoridades", joinColumns = @JoinColumn(name = "departamento_id"))
//    @MapKeyJoinColumn(name = "usuario_id")
//    @Column(name = "rol_id")
//    private Map<Usuario, Rol> autoridades;







//    private List<SubDepartamento> subDepartamentos;

}
