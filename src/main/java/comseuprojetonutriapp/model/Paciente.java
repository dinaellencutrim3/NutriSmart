package comseuprojetonutriapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private Integer idade;
    private Double peso;
    private Double altura;

    @Column(unique = true)
    private String email;

    private String sexo;
    private String restricoes;
    private String observacoes;
    private String objetivo;

    @Transient
    private Double imc;

    public Double getImc() {
        if (altura != null && altura > 0 && peso != null) {
            return peso / (altura * altura);
        }
        return 0.0;
    }
}