package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.Substituicao;
import comseuprojetonutriapp.repository.SubstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/substituicoes")
@CrossOrigin(origins = "*") // Permite que seu Frontend acesse a API
public class SubstituicaoController {

    @Autowired
    private SubstituicaoRepository repository;

    // 1. Endpoint para o Paciente enviar a sugestão
    @PostMapping
    public ResponseEntity<?> solicitarSubstituicao(@RequestBody Substituicao substituicao) {
        try {
            // O Spring Boot vai salvar o texto, o ID da dieta e o ID do paciente
            Substituicao novaSubstituicao = repository.save(substituicao);
            return ResponseEntity.ok(Map.of("mensagem", "Solicitação enviada com sucesso!", "id", novaSubstituicao.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Erro ao processar solicitação: " + e.getMessage()));
        }
    }

    // 2. Endpoint para o Nutricionista listar todas as pendentes
    @GetMapping("/pendentes")
    public List<Substituicao> listarPendentes() {
        return repository.findByStatus("PENDENTE");
    }

    // 3. Endpoint para o Nutricionista aprovar/negar
    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestBody Map<String, String> corpo) {
        return repository.findById(id).map(sub -> {
            sub.setStatus(corpo.get("status")); // Recebe "APROVADO" ou "NEGADO"
            repository.save(sub);
            return ResponseEntity.ok(Map.of("mensagem", "Status atualizado!"));
        }).orElse(ResponseEntity.notFound().build());
    }
}