package comseuprojetonutriapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alimentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String grupoAlimentar;

    private Double calorias;
    private Double proteinas;
    private Double carboidratos;
    private Double gorduras;
}