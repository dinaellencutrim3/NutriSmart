package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.Dieta;
import comseuprojetonutriapp.model.Paciente;
import comseuprojetonutriapp.repository.DietaRepository;
import comseuprojetonutriapp.repository.PacienteRepository;
import comseuprojetonutriapp.service.DietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody Dieta dieta) {
        try {
            if (dieta.getPaciente() == null || dieta.getPaciente().getId() == null) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Paciente não informado."));
            }
            Paciente paciente = pacienteRepository.findById(dieta.getPaciente().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Paciente com ID " + dieta.getPaciente().getId() + " não encontrado."));
            dieta.setPaciente(paciente);
            Dieta salva = service.salvar(dieta);
            return ResponseEntity.ok(Map.of(
                    "id",           salva.getId(),
                    "refeicaoNome", salva.getRefeicaoNome() != null ? salva.getRefeicaoNome() : "",
                    "horario",      salva.getHorario()      != null ? salva.getHorario()      : "",
                    "descricao",    salva.getDescricao()    != null ? salva.getDescricao()    : "",
                    "objetivo",     salva.getObjetivo()     != null ? salva.getObjetivo()     : "",
                    "pacienteId",   paciente.getId()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro ao salvar dieta: " + e.getMessage()));
        }
    }

    @DeleteMapping("/paciente/{id}")
    @Transactional
    public ResponseEntity<?> deletarPorPaciente(@PathVariable Long id) {
        dietaRepository.deleteByPacienteId(id);
        return ResponseEntity.ok(Map.of("mensagem", "Dietas removidas."));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<Map<String, Object>> listar() {
        return toDTOList(dietaRepository.findAll());
    }

    @GetMapping("/paciente/{id}")
    @Transactional(readOnly = true)
    public List<Map<String, Object>> listarPorPaciente(@PathVariable Long id) {
        return toDTOList(dietaRepository.findByPacienteId(id));
    }

    @GetMapping("/paciente/email/{email:.+}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listarPorEmail(@PathVariable String email) {
        return pacienteRepository.findByEmailIgnoreCase(email)
                .map(p -> ResponseEntity.ok(toDTOList(dietaRepository.findByPacienteId(p.getId()))))
                .orElse(ResponseEntity.notFound().build());
    }

    private List<Map<String, Object>> toDTOList(List<Dieta> dietas) {
        return dietas.stream().map(d -> Map.<String, Object>of(
                "id",           d.getId(),
                "refeicaoNome", d.getRefeicaoNome() != null ? d.getRefeicaoNome() : "",
                "horario",      d.getHorario()      != null ? d.getHorario()      : "",
                "descricao",    d.getDescricao()    != null ? d.getDescricao()    : "",
                "objetivo",     d.getObjetivo()     != null ? d.getObjetivo()     : "",
                "pacienteId",   d.getPaciente()     != null ? d.getPaciente().getId() : null
        )).toList();
    }
}