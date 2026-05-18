package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.Alimento; // Importa o Alimento da pasta model
import comseuprojetonutriapp.dto.SubstituicaoDTO; // Importa o DTO da pasta dto
import comseuprojetonutriapp.service.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/alimentos")
@CrossOrigin(origins = "*") // Importante para o cadastro funcionar!
public class AlimentoController {

    @Autowired
    private AlimentoService service;

    @PostMapping
    public Alimento cadastrar(@RequestBody Alimento alimento) {
        return service.salvar(alimento);
    }

    @GetMapping
    public List<Alimento> listar() {
        return service.listar();
    }

    @PostMapping("/substituir")
    public String realizarSubstituicao(@RequestBody SubstituicaoDTO dto) {
        Double resultado = service.calcularSubstituicao(
                dto.getAlimentoOrigemId(),
                dto.getAlimentoSubstitutoId(),
                dto.getQuantidadeGramas()
        );

        return String.format("Para substituir %.2fg do alimento ID %d, utilize %.2fg do alimento ID %d.",
                dto.getQuantidadeGramas(), dto.getAlimentoOrigemId(),
                resultado, dto.getAlimentoSubstitutoId());
    }
} // <--- ESTA CHAVE FECHA A CLASSE. VERIFIQUE SE ELA EXISTE NO FINAL DO ARQUIVO!