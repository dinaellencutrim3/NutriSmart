package comseuprojetonutriapp.repository;

import comseuprojetonutriapp.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByNomeIgnoreCase(String nome);
    Optional<Paciente> findByEmailIgnoreCase(String email);
}