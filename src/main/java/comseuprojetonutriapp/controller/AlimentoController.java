package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.dto.SubstituicaoDTO;
import comseuprojetonutriapp.model.Alimento;
import comseuprojetonutriapp.service.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class AlimentoController {

    @Autowired
    private AlimentoService service;

    // ── CRUD ─────────────────────────────────────────────────────────────────

    /** Cadastrar alimento — POST /alimentos */
    @PostMapping("/alimentos")
    public Alimento cadastrar(@RequestBody Alimento alimento) {
        return service.salvar(alimento);
    }

    /**
     * Listar todos os alimentos.
     * Responde tanto em /alimentos quanto em /api/alimentos
     * (o dashboard chama /api/alimentos, o HTML de alimentos chama /alimentos).
     */
    @GetMapping({"/alimentos", "/api/alimentos"})
    public List<Alimento> listar() {
        return service.listar();
    }

    // ── Substituição simples (mantém compatibilidade) ─────────────────────────

    /**
     * Endpoint legado — retorna texto simples.
     * Continua funcionando para não quebrar nada existente.
     * Rota: POST /alimentos/substituir
     */
    @PostMapping("/alimentos/substituir")
    public String realizarSubstituicaoSimples(@RequestBody SubstituicaoDTO dto) {
        Double resultado = service.calcularSubstituicao(
                dto.getAlimentoOrigemId(),
                dto.getAlimentoSubstitutoId(),
                dto.getQuantidadeGramas()
        );
        return String.format(
                "Para substituir %.2fg do alimento ID %d, utilize %.2fg do alimento ID %d.",
                dto.getQuantidadeGramas(), dto.getAlimentoOrigemId(),
                resultado, dto.getAlimentoSubstitutoId()
        );
    }

    /**
     * Endpoint novo — retorna JSON detalhado com equivalência nutricional completa.
     * Rota: POST /api/alimentos/substituir  ← esta é a que o dashboard chama
     *
     * Resposta JSON:
     * {
     *   "alimentoOrigem": "Arroz branco",
     *   "alimentoSubstituto": "Batata doce",
     *   "quantidadeOrigemGramas": 100.0,
     *   "quantidadeEquivalenteGramas": 93.5,
     *   "nutrientesOrigem":     { "calorias": 130, "proteinas": 2.7, ... },
     *   "nutrientesSubstituto": { "calorias": 130, "proteinas": 1.8, ... },
     *   "mensagem": "Para substituir 100g de Arroz branco, utilize 93.5g de Batata doce."
     * }
     */
    @PostMapping("/api/alimentos/substituir")
    public ResponseEntity<?> realizarSubstituicaoDetalhada(@RequestBody SubstituicaoDTO dto) {
        try {
            // Validação básica dos campos
            if (dto.getAlimentoOrigemId() == null || dto.getAlimentoSubstitutoId() == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("erro", "Informe o ID do alimento original e do substituto."));
            }
            if (dto.getQuantidadeGramas() == null || dto.getQuantidadeGramas() <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("erro", "A quantidade em gramas deve ser maior que zero."));
            }

            Map<String, Object> resultado = service.calcularSubstituicaoDetalhada(
                    dto.getAlimentoOrigemId(),
                    dto.getAlimentoSubstitutoId(),
                    dto.getQuantidadeGramas()
            );

            return ResponseEntity.ok(resultado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro inesperado: " + e.getMessage()));
        }
    }
}