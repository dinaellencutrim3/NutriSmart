package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.Usuario;
import comseuprojetonutriapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario novoUsuario) {
        // 1. Validar se o email já existe usando o nome correto: usuarioRepository
        if (repository.findByEmail(novoUsuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Este email já está em uso!"));
        }

        // 2. Salvar no banco
        repository.save(novoUsuario);
        return ResponseEntity.ok(Map.of("mensagem", "Usuário cadastrado com sucesso!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginDados) {
        // Agora usamos 'repository' de forma consistente
        Optional<Usuario> usuarioOpt = repository.findByEmail(loginDados.getEmail());

        if (usuarioOpt.isPresent() && usuarioOpt.get().getSenha().equals(loginDados.getSenha())) {
            Usuario user = usuarioOpt.get();

            Map<String, Object> resposta = new HashMap<>();
            resposta.put("status", "sucesso");
            resposta.put("userType", user.getTipo());
            resposta.put("nome", user.getNome());
            resposta.put("id", user.getId());

            return ResponseEntity.ok(resposta);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha incorretos");
    }
}