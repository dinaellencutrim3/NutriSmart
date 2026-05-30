package comseuprojetonutriapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomePaciente;
    private String emailPaciente;
    private String data;
    private String horario;
    private String tipo;
    private String observacoes;
    private String status = "pendente";
    private String dataSugerida;
    private String horarioSugerido;
    private LocalDateTime solicitadoEm = LocalDateTime.now();
}