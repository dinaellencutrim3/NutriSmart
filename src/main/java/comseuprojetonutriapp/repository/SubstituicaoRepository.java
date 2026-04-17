package comseuprojetonutriapp.repository;

import comseuprojetonutriapp.model.Substituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubstituicaoRepository extends JpaRepository<Substituicao, Long> {

    // Este método permite que o Nutricionista filtre apenas o que ainda não foi respondido
    List<Substituicao> findByStatus(String status);

    // Opcional: Buscar todas as solicitações de um paciente específico
    List<Substituicao> findByPacienteId(Long pacienteId);
}
