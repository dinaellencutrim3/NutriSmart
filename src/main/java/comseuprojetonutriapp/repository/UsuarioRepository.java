package comseuprojetonutriapp.repository;

import comseuprojetonutriapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método mágico do Spring que busca por email automaticamente
    Optional<Usuario> findByEmail(String email);
}