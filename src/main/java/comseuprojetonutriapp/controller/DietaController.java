package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.Dieta;
import comseuprojetonutriapp.repository.DietaRepository;
import comseuprojetonutriapp.service.DietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/dietas")
@CrossOrigin(origins = "*")
public class DietaController {

    @Autowired
    private DietaService service;
    @Autowired
    private DietaRepository dietaRepository;

    @PostMapping
    public Dieta cadastrar(@RequestBody Dieta dieta) {
        return service.salvar(dieta);
    }

    @GetMapping
    public List<Dieta> listar() {
        return service.listar();
    }

    @GetMapping("/paciente/{id}")
    public List<Dieta> listarPorPaciente(@PathVariable Long id) {
        // Chame o método com parênteses e passe o 'id'
        return dietaRepository.findByPacienteId(id);
    }
}