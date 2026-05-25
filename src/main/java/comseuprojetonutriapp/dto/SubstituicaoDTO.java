package comseuprojetonutriapp.dto;

public class SubstituicaoDTO {

    private Long alimentoOrigemId;
    private Long alimentoSubstitutoId;
    private Double quantidadeGramas;

    public SubstituicaoDTO() {}

    public Long getAlimentoOrigemId() { return alimentoOrigemId; }
    public void setAlimentoOrigemId(Long alimentoOrigemId) { this.alimentoOrigemId = alimentoOrigemId; }

    public Long getAlimentoSubstitutoId() { return alimentoSubstitutoId; }
    public void setAlimentoSubstitutoId(Long alimentoSubstitutoId) { this.alimentoSubstitutoId = alimentoSubstitutoId; }

    public Double getQuantidadeGramas() { return quantidadeGramas; }
    public void setQuantidadeGramas(Double quantidadeGramas) { this.quantidadeGramas = quantidadeGramas; }
}