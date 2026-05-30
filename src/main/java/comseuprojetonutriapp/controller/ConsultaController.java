package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.Consulta;
import comseuprojetonutriapp.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*")
public class ConsultaController {

    @Autowired
    private ConsultaRepository repository;

    @PostMapping
    public ResponseEntity<?> solicitar(@RequestBody Consulta consulta) {
        try {
            return ResponseEntity.ok(repository.save(consulta));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro ao salvar consulta: " + e.getMessage()));
        }
    }

    @GetMapping
    public List<Consulta> listarTodas() {
        return repository.findAllByOrderBySolicitadoEmDesc();
    }

    @GetMapping("/paciente/{email:.+}")
    public List<Consulta> listarPorEmail(@PathVariable String email) {
        return repository.findByEmailPacienteIgnoreCase(email);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id,
                                             @RequestBody Map<String, String> corpo) {
        return repository.findById(id).map(c -> {
            c.setStatus(corpo.get("status"));
            if (corpo.containsKey("dataSugerida"))    c.setDataSugerida(corpo.get("dataSugerida"));
            if (corpo.containsKey("horarioSugerido")) c.setHorarioSugerido(corpo.get("horarioSugerido"));
            repository.save(c);
            return ResponseEntity.ok(Map.of("mensagem", "Status atualizado!"));
        }).orElse(ResponseEntity.notFound().build());
    }
}