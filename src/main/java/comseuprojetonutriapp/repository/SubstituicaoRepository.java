package comseuprojetonutriapp.repository;

import comseuprojetonutriapp.model.Substituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubstituicaoRepository extends JpaRepository<Substituicao, Long> {
    List<Substituicao> findByStatus(String status);
    List<Substituicao> findByPacienteId(Long pacienteId);
    void deleteByPacienteId(Long pacienteId);
}