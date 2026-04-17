package comseuprojetonutriapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubstituicaoDTO {
    private Long alimentoOrigemId;
    private Long alimentoSubstitutoId;
    private Double quantidadeGramas;
}