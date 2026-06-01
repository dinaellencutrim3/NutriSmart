package comseuprojetonutriapp.repository;

import comseuprojetonutriapp.model.EvolucaoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvolucaoPacienteRepository extends JpaRepository<EvolucaoPaciente, Long> {
    List<EvolucaoPaciente> findByPacienteIdOrderByDataAsc(Long pacienteId);
}