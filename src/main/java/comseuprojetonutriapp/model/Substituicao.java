package comseuprojetonutriapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private String textoPedido;
    private LocalDateTime dataSolicitacao = LocalDateTime.now();
    private String status = "PENDENTE";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dieta_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Dieta dieta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Paciente paciente;
}