package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.EvolucaoPaciente;
import comseuprojetonutriapp.model.Paciente;
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
@RequestMapping("/evolucao")
@CrossOrigin(origins = "*")
public class EvolucaoPacienteController {

    @Autowired
    private EvolucaoPacienteRepository evolucaoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    // Lista histórico de evolução de um paciente
    @GetMapping("/{pacienteId}")
    public ResponseEntity<?> listar(@PathVariable Long pacienteId) {
        List<EvolucaoPaciente> lista = evolucaoRepository.findByPacienteIdOrderByDataAsc(pacienteId);
        return ResponseEntity.ok(lista);
    }

    // Registra novo ponto de evolução manualmente
    @PostMapping("/{pacienteId}")
    public ResponseEntity<?> registrar(@PathVariable Long pacienteId,
                                       @RequestBody Map<String, Object> body) {
        Optional<Paciente> pacOpt = pacienteRepository.findById(pacienteId);
        if (pacOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Paciente não encontrado."));
        }

        Object pesoObj = body.get("peso");
        if (pesoObj == null) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Peso é obrigatório."));
        }

        double peso = Double.parseDouble(pesoObj.toString());
        Paciente p  = pacOpt.get();

        double imc = 0;
        if (p.getAltura() != null && p.getAltura() > 0) {
            imc = peso / (p.getAltura() * p.getAltura());
            imc = Math.round(imc * 10.0) / 10.0;
        }

        String obs = body.getOrDefault("observacao", "").toString();

        EvolucaoPaciente ev = new EvolucaoPaciente(pacienteId, peso, imc, LocalDate.now(), obs);

        // % Gordura e Massa Muscular (opcionais)
        Object gordObj = body.get("percentualGordura");
        if (gordObj != null && !gordObj.toString().isBlank()) {
            try { ev.setPercentualGordura(Double.parseDouble(gordObj.toString())); } catch (NumberFormatException ignored) {}
        }
        Object muscObj = body.get("massaMuscular");
        if (muscObj != null && !muscObj.toString().isBlank()) {
            try { ev.setMassaMuscular(Double.parseDouble(muscObj.toString())); } catch (NumberFormatException ignored) {}
        }

        evolucaoRepository.save(ev);

        // Atualiza o peso atual do paciente também
        p.setPeso(peso);
        pacienteRepository.save(p);

        return ResponseEntity.ok(Map.of("mensagem", "Evolução registrada com sucesso!", "imc", imc));
    }
}