package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.Dieta;
import comseuprojetonutriapp.model.Paciente;
import comseuprojetonutriapp.repository.DietaRepository;
import comseuprojetonutriapp.repository.PacienteRepository;
import comseuprojetonutriapp.service.DietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dietas")
@CrossOrigin(origins = "*")
public class DietaController {

    @Autowired private DietaService service;
    @Autowired private DietaRepository dietaRepository;
    @Autowired private PacienteRepository pacienteRepository;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Dieta dieta) {
        try {
            // Garante que o Paciente é carregado do banco pelo ID
            if (dieta.getPaciente() != null && dieta.getPaciente().getId() != null) {
                Paciente paciente = pacienteRepository.findById(dieta.getPaciente().getId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Paciente com ID " + dieta.getPaciente().getId() + " não encontrado."));
                dieta.setPaciente(paciente);
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("erro", "Paciente não informado."));
            }
            Dieta salva = service.salvar(dieta);
            return ResponseEntity.ok(salva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro ao salvar dieta: " + e.getMessage()));
        }
    }

    @GetMapping
    public List<Dieta> listar() {
        return service.listar();
    }

    // Por ID do paciente — nutricionista
    @GetMapping("/paciente/{id}")
    public List<Dieta> listarPorPaciente(@PathVariable Long id) {
        return dietaRepository.findByPacienteId(id);
    }

    // Por EMAIL do paciente — paciente vê a própria dieta
    @GetMapping("/paciente/email/{email}")
    public ResponseEntity<?> listarPorEmail(@PathVariable String email) {
        return pacienteRepository.findByEmailIgnoreCase(email)
                .map(p -> ResponseEntity.ok(dietaRepository.findByPacienteId(p.getId())))
                .orElse(ResponseEntity.notFound().build());
    }
}