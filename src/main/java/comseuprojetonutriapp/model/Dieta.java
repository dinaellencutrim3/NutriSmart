package comseuprojetonutriapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "dietas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dieta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String objetivo;
    private LocalDate dataInicio;

    // Campos que o Paciente vai ver na tela
    private String horario;
    private String refeicaoNome;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "paciente_id") // O Hibernate criará a coluna sozinho
    private Paciente paciente;
}