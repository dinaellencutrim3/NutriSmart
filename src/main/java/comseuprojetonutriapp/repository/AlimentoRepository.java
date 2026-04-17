package comseuprojetonutriapp.repository;

import comseuprojetonutriapp.model.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    // Aqui o Spring Data JPA já cria para você os métodos
    // save(), findAll(), delete() e findById() automaticamente!
}
