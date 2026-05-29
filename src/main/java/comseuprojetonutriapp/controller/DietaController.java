package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.Dieta;
import comseuprojetonutriapp.repository.DietaRepository;
import comseuprojetonutriapp.repository.PacienteRepository;
import comseuprojetonutriapp.service.DietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dietas")
@CrossOrigin(origins = "*")
public class DietaController {

    @Autowired private DietaService service;
    @Autowired private DietaRepository dietaRepository;
    @Autowired private PacienteRepository pacienteRepository;

    @PostMapping
    public Dieta cadastrar(@RequestBody Dieta dieta) {
        return service.salvar(dieta);
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

    // Por EMAIL do paciente — usado pelo login do paciente para buscar a dieta dele
    @GetMapping("/paciente/email/{email}")
    public ResponseEntity<?> listarPorEmail(@PathVariable String email) {
        return pacienteRepository.findByEmailIgnoreCase(email)
                .map(p -> ResponseEntity.ok(dietaRepository.findByPacienteId(p.getId())))
                .orElse(ResponseEntity.notFound().build());
    }
}