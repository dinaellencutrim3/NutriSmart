package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.EvolucaoPaciente;
import comseuprojetonutriapp.model.Paciente;
import comseuprojetonutriapp.repository.EvolucaoPacienteRepository;
import comseuprojetonutriapp.repository.PacienteRepository;
import comseuprojetonutriapp.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @Autowired
    private PacienteRepository repository;

    @Autowired
    private EvolucaoPacienteRepository evolucaoRepository;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Paciente paciente) {
        try {
            Paciente salvo = service.salvar(paciente);

            // Registra ponto inicial de evolução se vier com peso
            if (salvo.getPeso() != null && salvo.getPeso() > 0) {
                double imc = calcularImc(salvo.getPeso(), salvo.getAltura());
                evolucaoRepository.save(
                        new EvolucaoPaciente(salvo.getId(), salvo.getPeso(), imc, LocalDate.now(), "Cadastro inicial")
                );
            }

            return ResponseEntity.ok(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("erro", "Erro ao cadastrar: " + e.getMessage()));
        }
    }

    @GetMapping
    public List<Paciente> listar() {
        return service.listarTodos();
    }

    @GetMapping("/por-email")
    public ResponseEntity<?> buscarPorEmail(@RequestParam String email) {
        return repository.findByEmailIgnoreCase(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Paciente dados) {
        try {
            // Busca peso anterior para comparar
            Optional<Paciente> anterior = repository.findById(id);
            Double pesoAnterior = anterior.map(Paciente::getPeso).orElse(null);

            Paciente atualizado = service.atualizar(id, dados);

            // Registra evolução se o peso mudou
            if (atualizado.getPeso() != null && atualizado.getPeso() > 0) {
                boolean pesoMudou = pesoAnterior == null
                        || Math.abs(pesoAnterior - atualizado.getPeso()) >= 0.01;

                if (pesoMudou) {
                    double imc = calcularImc(atualizado.getPeso(), atualizado.getAltura());
                    evolucaoRepository.save(
                            new EvolucaoPaciente(id, atualizado.getPeso(), imc, LocalDate.now(), "Atualização de dados")
                    );
                }
            }

            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("erro", "Erro ao atualizar: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.ok(Map.of("mensagem", "Paciente removido com sucesso."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("erro", "Erro ao remover: " + e.getMessage()));
        }
    }

    private double calcularImc(Double peso, Double altura) {
        if (altura == null || altura <= 0 || peso == null) return 0;
        double imc = peso / (altura * altura);
        return Math.round(imc * 10.0) / 10.0;
    }
}