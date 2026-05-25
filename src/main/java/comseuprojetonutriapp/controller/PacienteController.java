package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.Paciente;
import comseuprojetonutriapp.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    /**
     * Cadastra um novo paciente.
     * Retorna 400 com mensagem de erro se o nome já estiver cadastrado.
     */
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Paciente paciente) {
        try {
            Paciente salvo = service.salvar(paciente);
            return ResponseEntity.ok(salvo);
        } catch (IllegalArgumentException e) {
            // Duplicação de nome detectada no service
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro ao cadastrar paciente: " + e.getMessage()));
        }
    }

    /**
     * Lista todos os pacientes sem duplicatas.
     */
    @GetMapping
    public List<Paciente> listar() {
        return service.listarTodos();
    }
}