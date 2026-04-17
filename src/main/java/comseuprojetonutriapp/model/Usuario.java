package comseuprojetonutriapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonProperty("name") // Mapeia o 'name' do JS para 'nome' no banco
    private String nome;

    @JsonProperty("password") // Mapeia o 'password' do JS para 'senha' no banco
    private String senha;

    @JsonProperty("type") // Mapeia o 'type' do JS para 'tipo' no banco
    private String tipo;

    private String crn;
}