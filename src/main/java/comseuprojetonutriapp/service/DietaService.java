package comseuprojetonutriapp.service;

import comseuprojetonutriapp.model.Dieta;
import comseuprojetonutriapp.repository.DietaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DietaService {

    @Autowired
    private DietaRepository repository;

    @Transactional
    public Dieta salvar(Dieta dieta) {
        return repository.save(dieta);
    }

    @Transactional(readOnly = true)
    public List<Dieta> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Dieta> buscarPorPacienteId(Long id) {
        return repository.findByPacienteId(id);
    }
}