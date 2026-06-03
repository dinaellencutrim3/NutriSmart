package comseuprojetonutriapp.controller;

import comseuprojetonutriapp.model.Paciente;
import comseuprojetonutriapp.model.Usuario;
import comseuprojetonutriapp.repository.PacienteRepository;
import comseuprojetonutriapp.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private JavaMailSender mailSender;

    // CADASTRO
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario novoUsuario) {
        if (repository.findByEmail(novoUsuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Este email já está em uso!"));
        }
        Usuario usuarioSalvo = repository.save(novoUsuario);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Usuário cadastrado com sucesso!",
                "id", usuarioSalvo.getId()
        ));
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginDados) {
        Optional<Usuario> usuarioOpt = repository.findByEmail(loginDados.getEmail());
        if (usuarioOpt.isPresent()
                && usuarioOpt.get().getSenha().equals(loginDados.getSenha())) {
            Usuario user = usuarioOpt.get();
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("status", "sucesso");
            resposta.put("tipo",   user.getTipo());
            resposta.put("nome",   user.getNome());
            resposta.put("id",     user.getId());

            // Se for PACIENTE, resolve o ID real da tabela 'pacientes' pelo e-mail
            if ("PACIENTE".equalsIgnoreCase(user.getTipo())) {
                pacienteRepository.findByEmailIgnoreCase(user.getEmail())
                        .ifPresent(p -> resposta.put("pacienteId", p.getId()));
            }

            return ResponseEntity.ok(resposta);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("erro", "E-mail ou senha incorretos"));
    }

    // ESQUECI A SENHA — gera token e envia link por e-mail
    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> esqueciSenha(@RequestBody Map<String, String> body,
                                          HttpServletRequest request) {
        String email = body.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Informe um e-mail válido."));
        }

        email = email.toLowerCase().trim();
        Optional<Usuario> usuarioOpt = repository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "mensagem", "Se este e-mail estiver cadastrado, você receberá o link em breve."
            ));
        }

        Usuario usuario = usuarioOpt.get();

        String token = UUID.randomUUID().toString();
        usuario.setResetToken(token);
        usuario.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30));
        repository.save(usuario);

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String link = baseUrl + "/redefinir_senha.html?token=" + token;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("dinaellen5cutrim@gmail.com", "NutriSmart");
            helper.setTo(email);
            helper.setSubject("Recuperação de senha — NutriSmart");

            String html = "<!DOCTYPE html>" +
                    "<html><body style='font-family:sans-serif;background:#f0fdf4;padding:32px;margin:0;'>" +
                    "<div style='max-width:480px;margin:0 auto;background:#fff;border-radius:18px;" +
                    "padding:36px;box-shadow:0 8px 30px rgba(45,106,79,.10);border:1px solid #bbf7d0;'>" +
                    "<div style='text-align:center;margin-bottom:24px;'>" +
                    "<div style='display:inline-block;background:linear-gradient(135deg,#52B788,#2D6A4F);" +
                    "border-radius:14px;padding:14px 20px;font-size:28px;'>🌿</div>" +
                    "<h1 style='font-size:22px;color:#1a3a2a;margin-top:16px;margin-bottom:4px;'>Recuperação de senha</h1>" +
                    "<p style='color:#6b7280;font-size:14px;margin:0;'>NutriSmart</p>" +
                    "</div>" +
                    "<p style='color:#374151;font-size:15px;margin-bottom:8px;'>Olá, <strong>" + usuario.getNome() + "</strong>!</p>" +
                    "<p style='color:#374151;font-size:15px;margin-bottom:20px;'>Clique no botão abaixo para criar uma nova senha. Este link expira em <strong>30 minutos</strong>.</p>" +
                    "<div style='text-align:center;margin:28px 0;'>" +
                    "<a href='" + link + "' style='display:inline-block;background:linear-gradient(135deg,#52B788,#2D6A4F);" +
                    "color:#fff;text-decoration:none;padding:14px 36px;border-radius:12px;" +
                    "font-size:16px;font-weight:700;'>Redefinir minha senha</a>" +
                    "</div>" +
                    "<p style='color:#9ca3af;font-size:13px;'>Se o botão não funcionar, copie e cole este link no navegador:</p>" +
                    "<p style='color:#52B788;font-size:12px;word-break:break-all;'>" + link + "</p>" +
                    "<p style='color:#9ca3af;font-size:13px;margin-top:16px;'>Se você não solicitou isso, ignore este e-mail.</p>" +
                    "<hr style='border:none;border-top:1px solid #e5e7eb;margin:24px 0;'>" +
                    "<p style='text-align:center;color:#9ca3af;font-size:12px;margin:0;'>NutriSmart — Cuidando da sua saúde 🌱</p>" +
                    "</div></body></html>";

            helper.setText(html, true);
            mailSender.send(message);

        } catch (Exception e) {
            System.err.println("[ERRO EMAIL] " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao enviar o e-mail. Tente novamente."));
        }

        return ResponseEntity.ok(Map.of(
                "mensagem", "Link de recuperação enviado para " + email + ". Verifique sua caixa de entrada."
        ));
    }

    // REDEFINIR SENHA — valida token e salva nova senha
    @PostMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestBody Map<String, String> body) {
        String token     = body.get("token");
        String novaSenha = body.get("novaSenha");

        if (token == null || token.isBlank() || novaSenha == null || novaSenha.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Dados inválidos."));
        }

        if (novaSenha.length() < 6) {
            return ResponseEntity.badRequest().body(Map.of("erro", "A senha deve ter pelo menos 6 caracteres."));
        }

        Optional<Usuario> usuarioOpt = repository.findByResetToken(token);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Link inválido ou já utilizado."));
        }

        Usuario usuario = usuarioOpt.get();

        if (usuario.getResetTokenExpiry() == null
                || LocalDateTime.now().isAfter(usuario.getResetTokenExpiry())) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Este link expirou. Solicite um novo."));
        }

        usuario.setSenha(novaSenha);
        usuario.setResetToken(null);
        usuario.setResetTokenExpiry(null);
        repository.save(usuario);

        return ResponseEntity.ok(Map.of("mensagem", "Senha alterada com sucesso!"));
    }
}