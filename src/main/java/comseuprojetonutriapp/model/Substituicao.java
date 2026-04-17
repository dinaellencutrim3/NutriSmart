package comseuprojetonutriapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "substituicoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Substituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String textoPedido; // Ex: "Gostaria de trocar o ovo por frango"
    private LocalDateTime dataSolicitacao = LocalDateTime.now();
    private String status = "PENDENTE"; // PENDENTE, APROVADO, NEGADO

    @ManyToOne
    @JoinColumn(name = "dieta_id")
    private Dieta dieta; // Para sabermos de qual refeição ele está falando

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
}
