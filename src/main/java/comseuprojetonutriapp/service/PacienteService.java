package comseuprojetonutriapp.service;

import comseuprojetonutriapp.model.Paciente;
import comseuprojetonutriapp.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    public Paciente salvar(Paciente paciente) {
        String nomeNormalizado = paciente.getNome().trim();
        paciente.setNome(nomeNormalizado);

        boolean nomeExiste = repository.findByNomeIgnoreCase(nomeNormalizado).isPresent();
        if (nomeExiste) {
            throw new IllegalArgumentException("Já existe um paciente cadastrado com esse nome.");
        }

        // Valida email duplicado se informado
        if (paciente.getEmail() != null && !paciente.getEmail().isBlank()) {
            String emailNorm = paciente.getEmail().trim().toLowerCase();
            paciente.setEmail(emailNorm);
            boolean emailExiste = repository.findByEmailIgnoreCase(emailNorm).isPresent();
            if (emailExiste) {
                throw new IllegalArgumentException("Já existe um paciente cadastrado com esse e-mail.");
            }
        }

        return repository.save(paciente);
    }

    public Paciente atualizar(Long id, Paciente dados) {
        Paciente existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado."));

        // Verifica nome duplicado apenas se mudou
        if (!existente.getNome().equalsIgnoreCase(dados.getNome().trim())) {
            boolean nomeExiste = repository.findByNomeIgnoreCase(dados.getNome().trim()).isPresent();
            if (nomeExiste) {
                throw new IllegalArgumentException("Já existe um paciente cadastrado com esse nome.");
            }
        }

        existente.setNome(dados.getNome().trim());
        existente.setIdade(dados.getIdade());
        existente.setPeso(dados.getPeso());
        existente.setAltura(dados.getAltura());
        existente.setSexo(dados.getSexo());
        existente.setObjetivo(dados.getObjetivo());
        existente.setRestricoes(dados.getRestricoes());
        existente.setObservacoes(dados.getObservacoes());

        if (dados.getEmail() != null && !dados.getEmail().isBlank()) {
            String emailNorm = dados.getEmail().trim().toLowerCase();
            if (!emailNorm.equals(existente.getEmail())) {
                boolean emailExiste = repository.findByEmailIgnoreCase(emailNorm).isPresent();
                if (emailExiste) {
                    throw new IllegalArgumentException("Já existe um paciente com esse e-mail.");
                }
            }
            existente.setEmail(emailNorm);
        }

        return repository.save(existente);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Paciente não encontrado.");
        }
        repository.deleteById(id);
    }

    public List<Paciente> listarTodos() {
        List<Paciente> pacientes = repository.findAll();
        Map<Long, Paciente> mapa = new LinkedHashMap<>();
        for (Paciente p : pacientes) {
            if (p.getId() != null && !mapa.containsKey(p.getId())) {
                mapa.put(p.getId(), p);
            }
        }
        return mapa.values().stream()
                .sorted((a, b) -> a.getId().compareTo(b.getId()))
                .collect(Collectors.toList());
    }
}