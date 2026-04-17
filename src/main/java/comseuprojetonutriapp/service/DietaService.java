package comseuprojetonutriapp.service;

import comseuprojetonutriapp.model.Dieta;
import comseuprojetonutriapp.repository.DietaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DietaService {

    @Autowired
    private DietaRepository repository;

    public Dieta salvar(Dieta dieta) {
        return repository.save(dieta);
    }

    // Adicione este método EXATAMENTE assim (sem parâmetros)
    public List<Dieta> listar() {
        return repository.findAll();
    }

    // Mantenha este se quiser usar futuramente para filtrar
    public List<Dieta> buscarPorPacienteId(Long id) {
        return repository.findByPacienteId(id);
    }
}