package comseuprojetonutriapp.repository;

import comseuprojetonutriapp.model.AvaliacaoNutricional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoNutricionalRepository extends JpaRepository<AvaliacaoNutricional, Long> {
    List<AvaliacaoNutricional> findByPacienteIdOrderByDataDesc(Long pacienteId);
    Optional<AvaliacaoNutricional> findTopByPacienteIdOrderByDataDesc(Long pacienteId);
}