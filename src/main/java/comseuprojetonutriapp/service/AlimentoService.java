package comseuprojetonutriapp.service;

import comseuprojetonutriapp.model.Alimento;
import comseuprojetonutriapp.repository.AlimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    // ── CRUD básico ───────────────────────────────────────────────────────────

    public Alimento salvar(Alimento alimento) {
        return alimentoRepository.save(alimento);
    }

    public List<Alimento> listar() {
        return alimentoRepository.findAll();
    }

    // ── Cálculo simples (mantém compatibilidade com o controller antigo) ──────
    /**
     * Retorna quantos gramas do substituto equivalem à mesma caloria
     * de quantidadeGramas do alimento origem.
     */
    public Double calcularSubstituicao(Long origemId, Long substitutoId, Double quantidadeGramas) {

        Alimento origem     = alimentoRepository.findById(origemId)
                .orElseThrow(() -> new IllegalArgumentException("Alimento de origem não encontrado. ID: " + origemId));

        Alimento substituto = alimentoRepository.findById(substitutoId)
                .orElseThrow(() -> new IllegalArgumentException("Alimento substituto não encontrado. ID: " + substitutoId));

        validarCalorias(origem);
        validarCalorias(substituto);

        double caloriasNaQtd         = (origem.getCalorias() / 100.0) * quantidadeGramas;
        double quantidadeEquivalente = (caloriasNaQtd / substituto.getCalorias()) * 100.0;

        return arredondar(quantidadeEquivalente);
    }

    /**
     * Versão detalhada: retorna JSON completo com nutrientes do original
     * E do substituto — usado pelo endpoint /api/alimentos/substituir.
     */
    public Map<String, Object> calcularSubstituicaoDetalhada(
            Long origemId, Long substitutoId, Double quantidadeGramas) {

        Alimento origem     = alimentoRepository.findById(origemId)
                .orElseThrow(() -> new IllegalArgumentException("Alimento de origem não encontrado. ID: " + origemId));

        Alimento substituto = alimentoRepository.findById(substitutoId)
                .orElseThrow(() -> new IllegalArgumentException("Alimento substituto não encontrado. ID: " + substitutoId));

        validarCalorias(origem);
        validarCalorias(substituto);

        // ── Cálculo de equivalência calórica ──────────────────────────────────
        double caloriasNaQtd         = (origem.getCalorias() / 100.0) * quantidadeGramas;
        double quantidadeEquivalente = (caloriasNaQtd / substituto.getCalorias()) * 100.0;
        double fatorOrig             = quantidadeGramas / 100.0;
        double fatorSub              = quantidadeEquivalente / 100.0;

        // ── Nutrientes do original na quantidade informada ────────────────────
        Map<String, Object> nutOrigem = new LinkedHashMap<>();
        nutOrigem.put("calorias",     arredondar(caloriasNaQtd));
        nutOrigem.put("proteinas",    arredondar(safe(origem.getProteinas())    * fatorOrig));
        nutOrigem.put("carboidratos", arredondar(safe(origem.getCarboidratos()) * fatorOrig));
        nutOrigem.put("gorduras",     arredondar(safe(origem.getGorduras())     * fatorOrig));

        // ── Nutrientes do substituto na quantidade equivalente ────────────────
        Map<String, Object> nutSubstituto = new LinkedHashMap<>();
        nutSubstituto.put("calorias",     arredondar((substituto.getCalorias() / 100.0) * quantidadeEquivalente));
        nutSubstituto.put("proteinas",    arredondar(safe(substituto.getProteinas())    * fatorSub));
        nutSubstituto.put("carboidratos", arredondar(safe(substituto.getCarboidratos()) * fatorSub));
        nutSubstituto.put("gorduras",     arredondar(safe(substituto.getGorduras())     * fatorSub));

        // ── Resposta final ────────────────────────────────────────────────────
        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("alimentoOrigem",              origem.getNome());
        resultado.put("alimentoSubstituto",          substituto.getNome());
        resultado.put("quantidadeOrigemGramas",      quantidadeGramas);
        resultado.put("quantidadeEquivalenteGramas", arredondar(quantidadeEquivalente));
        resultado.put("nutrientesOrigem",            nutOrigem);
        resultado.put("nutrientesSubstituto",        nutSubstituto);
        resultado.put("mensagem", String.format(
                "Para substituir %.1fg de %s, utilize %.1fg de %s (equivalência calórica mantida).",
                quantidadeGramas, origem.getNome(),
                arredondar(quantidadeEquivalente), substituto.getNome()
        ));

        return resultado;
    }

    // ── Helpers privados ──────────────────────────────────────────────────────

    private void validarCalorias(Alimento a) {
        if (a.getCalorias() == null || a.getCalorias() <= 0) {
            throw new IllegalArgumentException(
                    "O alimento '" + a.getNome() + "' não possui calorias cadastradas.");
        }
    }

    private double safe(Double v) { return v != null ? v : 0.0; }

    private double arredondar(double v) { return Math.round(v * 10.0) / 10.0; }
}