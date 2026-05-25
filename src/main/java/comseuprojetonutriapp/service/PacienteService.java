package comseuprojetonutriapp.service;

import comseuprojetonutriapp.model.Paciente;
import comseuprojetonutriapp.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    /**
     * Salva um paciente, impedindo duplicação por nome (case-insensitive).
     * Se já existir um paciente com o mesmo nome, lança exceção com mensagem clara.
     */
    public Paciente salvar(Paciente paciente) {
        // Verifica duplicata por nome (ignora maiúsculas/minúsculas e espaços extras)
        String nomeNormalizado = paciente.getNome().trim();
        paciente.setNome(nomeNormalizado);

        boolean jaExiste = repository.findAll().stream()
                .anyMatch(p -> p.getNome().trim().equalsIgnoreCase(nomeNormalizado));

        if (jaExiste) {
            throw new IllegalArgumentException(
                    "Já existe um paciente cadastrado com o nome '" + nomeNormalizado + "'."
            );
        }

        return repository.save(paciente);
    }

    /**
     * Lista todos os pacientes sem duplicatas.
     * A deduplicação por ID garante que mesmo que o banco tenha registros
     * duplicados por algum bug anterior, o retorno sempre será limpo.
     */
    public List<Paciente> listarTodos() {
        return repository.findAll().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Paciente::getId,       // chave = ID único
                                p -> p,                // valor = o próprio paciente
                                (a, b) -> a            // em caso de colisão, mantém o primeiro
                        ),
                        map -> map.values().stream()
                                .sorted((a, b) -> Long.compare(a.getId(), b.getId()))
                                .collect(Collectors.toList())
                ));
    }
}