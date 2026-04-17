package comseuprojetonutriapp.service;

import comseuprojetonutriapp.model.Alimento;
import comseuprojetonutriapp.repository.AlimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository repository;

    public Alimento salvar(Alimento alimento) {
        return repository.save(alimento);
    }

    public List<Alimento> listar() {
        return repository.findAll();
    }

    public Double calcularSubstituicao(Long origemId, Long substitutoId, Double quantidade) {
        Alimento origem = repository.findById(origemId)
                .orElseThrow(() -> new RuntimeException("Alimento de origem não encontrado"));

        Alimento substituto = repository.findById(substitutoId)
                .orElseThrow(() -> new RuntimeException("Alimento substituto não encontrado"));

        double caloriasPorGramaOrigem = origem.getCalorias() / 100;
        double caloriasPorGramaSubstituto = substituto.getCalorias() / 100;

        return (caloriasPorGramaOrigem * quantidade) / caloriasPorGramaSubstituto;
    }
}