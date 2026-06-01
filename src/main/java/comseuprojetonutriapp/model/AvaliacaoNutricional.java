package comseuprojetonutriapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "avaliacao_nutricional")
public class AvaliacaoNutricional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pacienteId;

    @Column(nullable = false)
    private LocalDate data;

    // === ANTROPOMÉTRICAS ===
    private Double peso;
    private Double altura;
    private Double imc;
    private Double circunferenciaCintura;
    private Double circunferenciaAbdomen;
    private Double circunferenciaQuadril;
    private Double circunferenciaBraco;
    private Double relacaoCinturaQuadril;

    // === COMPOSIÇÃO CORPORAL ===
    private Double percentualGordura;
    private Double massaMagra;
    private Double massaGorda;
    private Double aguaCorporal;
    private Double taxaMetabolismoBasal;

    // === BIOQUÍMICOS ===
    private Double colesterolTotal;
    private Double hdl;
    private Double ldl;
    private Double triglicerideos;
    private Double glicemia;
    private Double hemoglobina;
    private String outrosExames;

    // === AVALIAÇÃO CLÍNICA ===
    private String sintomasAtuais;
    private String historicoFamiliar;
    private String medicamentosSupl;
    private String saudePeleUnhasCabelo;

    // === HISTÓRICO ALIMENTAR ===
    private String registroAlimentar;
    private String alergias;
    private String intolerancias;
    private String nivelAtividadeFisica;
    private String rotinaSono;
    private String preferenciasAlimentares;

    // === OBSERVAÇÕES ===
    private String observacoes;

    public AvaliacaoNutricional() {}

    // GETTERS E SETTERS
    public Long getId() { return id; }
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long v) { this.pacienteId = v; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate v) { this.data = v; }
    public Double getPeso() { return peso; }
    public void setPeso(Double v) { this.peso = v; }
    public Double getAltura() { return altura; }
    public void setAltura(Double v) { this.altura = v; }
    public Double getImc() { return imc; }
    public void setImc(Double v) { this.imc = v; }
    public Double getCircunferenciaCintura() { return circunferenciaCintura; }
    public void setCircunferenciaCintura(Double v) { this.circunferenciaCintura = v; }
    public Double getCircunferenciaAbdomen() { return circunferenciaAbdomen; }
    public void setCircunferenciaAbdomen(Double v) { this.circunferenciaAbdomen = v; }
    public Double getCircunferenciaQuadril() { return circunferenciaQuadril; }
    public void setCircunferenciaQuadril(Double v) { this.circunferenciaQuadril = v; }
    public Double getCircunferenciaBraco() { return circunferenciaBraco; }
    public void setCircunferenciaBraco(Double v) { this.circunferenciaBraco = v; }
    public Double getRelacaoCinturaQuadril() { return relacaoCinturaQuadril; }
    public void setRelacaoCinturaQuadril(Double v) { this.relacaoCinturaQuadril = v; }
    public Double getPercentualGordura() { return percentualGordura; }
    public void setPercentualGordura(Double v) { this.percentualGordura = v; }
    public Double getMassaMagra() { return massaMagra; }
    public void setMassaMagra(Double v) { this.massaMagra = v; }
    public Double getMassaGorda() { return massaGorda; }
    public void setMassaGorda(Double v) { this.massaGorda = v; }
    public Double getAguaCorporal() { return aguaCorporal; }
    public void setAguaCorporal(Double v) { this.aguaCorporal = v; }
    public Double getTaxaMetabolismoBasal() { return taxaMetabolismoBasal; }
    public void setTaxaMetabolismoBasal(Double v) { this.taxaMetabolismoBasal = v; }
    public Double getColesterolTotal() { return colesterolTotal; }
    public void setColesterolTotal(Double v) { this.colesterolTotal = v; }
    public Double getHdl() { return hdl; }
    public void setHdl(Double v) { this.hdl = v; }
    public Double getLdl() { return ldl; }
    public void setLdl(Double v) { this.ldl = v; }
    public Double getTriglicerideos() { return triglicerideos; }
    public void setTriglicerideos(Double v) { this.triglicerideos = v; }
    public Double getGlicemia() { return glicemia; }
    public void setGlicemia(Double v) { this.glicemia = v; }
    public Double getHemoglobina() { return hemoglobina; }
    public void setHemoglobina(Double v) { this.hemoglobina = v; }
    public String getOutrosExames() { return outrosExames; }
    public void setOutrosExames(String v) { this.outrosExames = v; }
    public String getSintomasAtuais() { return sintomasAtuais; }
    public void setSintomasAtuais(String v) { this.sintomasAtuais = v; }
    public String getHistoricoFamiliar() { return historicoFamiliar; }
    public void setHistoricoFamiliar(String v) { this.historicoFamiliar = v; }
    public String getMedicamentosSupl() { return medicamentosSupl; }
    public void setMedicamentosSupl(String v) { this.medicamentosSupl = v; }
    public String getSaudePeleUnhasCabelo() { return saudePeleUnhasCabelo; }
    public void setSaudePeleUnhasCabelo(String v) { this.saudePeleUnhasCabelo = v; }
    public String getRegistroAlimentar() { return registroAlimentar; }
    public void setRegistroAlimentar(String v) { this.registroAlimentar = v; }
    public String getAlergias() { return alergias; }
    public void setAlergias(String v) { this.alergias = v; }
    public String getIntolerancias() { return intolerancias; }
    public void setIntolerancias(String v) { this.intolerancias = v; }
    public String getNivelAtividadeFisica() { return nivelAtividadeFisica; }
    public void setNivelAtividadeFisica(String v) { this.nivelAtividadeFisica = v; }
    public String getRotinaSono() { return rotinaSono; }
    public void setRotinaSono(String v) { this.rotinaSono = v; }
    public String getPreferenciasAlimentares() { return preferenciasAlimentares; }
    public void setPreferenciasAlimentares(String v) { this.preferenciasAlimentares = v; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String v) { this.observacoes = v; }
}