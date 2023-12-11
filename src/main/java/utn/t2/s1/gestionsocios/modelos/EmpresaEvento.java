package utn.t2.s1.gestionsocios.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

@Entity
@Data
@Table(name="empresa_evento")
public class EmpresaEvento extends Persistente {

    @ManyToOne
    @JoinColumn(name = "socio_id")
    private Socio socio;

    @Column(name = "otraEmpresa")
    private String otraEmpresa;
}
