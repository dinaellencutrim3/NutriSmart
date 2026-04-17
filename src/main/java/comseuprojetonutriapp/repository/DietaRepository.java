package comseuprojetonutriapp.repository;

import comseuprojetonutriapp.model.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DietaRepository extends JpaRepository<Dieta, Long> {
    List<Dieta> findByPacienteId(Long pacienteId);
}