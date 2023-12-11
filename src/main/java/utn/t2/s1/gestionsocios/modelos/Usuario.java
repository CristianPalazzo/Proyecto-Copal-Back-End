package utn.t2.s1.gestionsocios.modelos;

import jakarta.persistence.*;
import lombok.*;
import utn.t2.s1.gestionsocios.modelos.Socio;
import utn.t2.s1.gestionsocios.persistencia.Persistente;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="usuario")
public class Usuario extends Persistente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String contrasenia;

    @JoinColumn
    @ManyToOne()
    private Socio socio;

    @JoinColumn(name="tipo_de_usuario", nullable = false)
    @ManyToOne()
    private TipoDeUsuario tipoDeUsuario;

}
