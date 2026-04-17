package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.Paciente;
import comseuprojetonutriapp.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @PostMapping
    public Paciente cadastrar(@RequestBody Paciente paciente) {
        return service.salvar(paciente);
    }

    @GetMapping
    public List<Paciente> listar() {
        return service.listarTodos();
    }
}