package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.AvaliacaoNutricional;
import comseuprojetonutriapp.model.EvolucaoPaciente;
import comseuprojetonutriapp.model.Paciente;
import comseuprojetonutriapp.repository.AvaliacaoNutricionalRepository;
import comseuprojetonutriapp.repository.EvolucaoPacienteRepository;
import comseuprojetonutriapp.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/avaliacao")
@CrossOrigin(origins = "*")
public class AvaliacaoNutricionalController {

    @Autowired
    private AvaliacaoNutricionalRepository avaliacaoRepo;

    @Autowired
    private PacienteRepository pacienteRepo;

    @Autowired
    private EvolucaoPacienteRepository evolucaoRepo;

    // Lista todas as avaliações de um paciente
    @GetMapping("/{pacienteId}")
    public ResponseEntity<?> listar(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(avaliacaoRepo.findByPacienteIdOrderByDataDesc(pacienteId));
    }

    // Última avaliação
    @GetMapping("/{pacienteId}/ultima")
    public ResponseEntity<?> ultima(@PathVariable Long pacienteId) {
        return avaliacaoRepo.findTopByPacienteIdOrderByDataDesc(pacienteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Salva nova avaliação e gera ponto de evolução automaticamente
    @PostMapping("/{pacienteId}")
    public ResponseEntity<?> salvar(@PathVariable Long pacienteId,
                                    @RequestBody AvaliacaoNutricional dados) {

        Optional<Paciente> pacOpt = pacienteRepo.findById(pacienteId);
        if (pacOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Paciente não encontrado."));
        }

        dados.setPacienteId(pacienteId);
        if (dados.getData() == null) dados.setData(LocalDate.now());

        // Calcula IMC se não veio preenchido
        if (dados.getImc() == null && dados.getPeso() != null && dados.getAltura() != null && dados.getAltura() > 0) {
            double imc = dados.getPeso() / (dados.getAltura() * dados.getAltura());
            dados.setImc(Math.round(imc * 10.0) / 10.0);
        }

        AvaliacaoNutricional salva = avaliacaoRepo.save(dados);

        // Gera ponto de evolução se veio com peso
        if (dados.getPeso() != null && dados.getPeso() > 0) {
            double imc = dados.getImc() != null ? dados.getImc() : 0;
            evolucaoRepo.save(new EvolucaoPaciente(
                    pacienteId, dados.getPeso(), imc, dados.getData(), "Avaliação nutricional"
            ));

            // Atualiza peso atual do paciente
            Paciente p = pacOpt.get();
            p.setPeso(dados.getPeso());
            if (dados.getAltura() != null) p.setAltura(dados.getAltura());
            pacienteRepo.save(p);
        }

        return ResponseEntity.ok(salva);
    }
}