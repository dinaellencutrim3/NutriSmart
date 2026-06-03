package comseuprojetonutriapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "evolucao_paciente")
public class EvolucaoPaciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pacienteId;

    @Column(nullable = false)
    private Double peso;

    private Double imc;

    private Double percentualGordura;

    private Double massaMuscular;

    @Column(nullable = false)
    private LocalDate data;

    private String observacao;

    public EvolucaoPaciente() {}

    public EvolucaoPaciente(Long pacienteId, Double peso, Double imc, LocalDate data, String observacao) {
        this.pacienteId  = pacienteId;
        this.peso        = peso;
        this.imc         = imc;
        this.data        = data;
        this.observacao  = observacao;
    }

    public Long getId()                           { return id; }
    public Long getPacienteId()                   { return pacienteId; }
    public void setPacienteId(Long v)             { this.pacienteId = v; }
    public Double getPeso()                       { return peso; }
    public void setPeso(Double v)                 { this.peso = v; }
    public Double getImc()                        { return imc; }
    public void setImc(Double v)                  { this.imc = v; }
    public Double getPercentualGordura()          { return percentualGordura; }
    public void setPercentualGordura(Double v)    { this.percentualGordura = v; }
    public Double getMassaMuscular()              { return massaMuscular; }
    public void setMassaMuscular(Double v)        { this.massaMuscular = v; }
    public LocalDate getData()                    { return data; }
    public void setData(LocalDate v)              { this.data = v; }
    public String getObservacao()                 { return observacao; }
    public void setObservacao(String v)           { this.observacao = v; }
}