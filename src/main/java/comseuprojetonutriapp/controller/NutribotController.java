package comseuprojetonutriapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nutribot")
@CrossOrigin(origins = "*")
public class NutribotController {

    @Value("${groq.api.key:}")
    private String apiKey;

    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";

    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody Map<String, Object> payload) {

        if (apiKey == null || apiKey.isBlank() || apiKey.equals("COLE_SUA_CHAVE_AQUI")) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("erro", "NutriBot indisponível: configure 'groq.api.key' no application.properties."));
        }

        try {
            String system = (String) payload.getOrDefault("system", "Você é um assistente nutricional.");
            List<Map<String, Object>> mensagensOriginais =
                    (List<Map<String, Object>>) payload.getOrDefault("messages", List.of());

            // Monta lista com system + histórico
            List<Map<String, Object>> messages = new ArrayList<>();
            messages.add(Map.of("role", "system", "content", system));
            messages.addAll(mensagensOriginais);

            // Corpo no formato OpenAI/Groq
            Map<String, Object> corpo = Map.of(
                    "model",       "llama-3.3-70b-versatile",
                    "messages",    messages,
                    "max_tokens",  1000,
                    "temperature", 0.7
            );

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(corpo, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(GROQ_URL, request, Map.class);

            // Extrai o texto e devolve no formato que o frontend já espera
            String texto = extrairTexto(response.getBody());
            return ResponseEntity.ok(Map.of(
                    "content", List.of(Map.of("type", "text", "text", texto))
            ));

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of("erro", "Erro da API Groq: " + e.getResponseBodyAsString()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro ao chamar NutriBot: " + e.getMessage()));
        }
    }

    @SuppressWarnings("unchecked")
    private String extrairTexto(Map<String, Object> body) {
        try {
            List<Map<String, Object>> choices =
                    (List<Map<String, Object>>) body.get("choices");
            Map<String, Object> message =
                    (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");
        } catch (Exception e) {
            return "Não consegui processar a resposta. Tente novamente.";
        }
    }
}