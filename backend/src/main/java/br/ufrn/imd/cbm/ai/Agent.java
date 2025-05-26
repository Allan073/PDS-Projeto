package br.ufrn.imd.cbm.ai;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Agent {
    private String key;

    public Agent(Environment env) {
        this.key = env.getProperty("ai.key");
    };

    public void report() {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        String prompt = "óla mundo";
        Map<String, Object> map = Map.of(
            "model", "google/gemini-2.0-flash-exp:free",
            "messages", List.of(
                Map.of(
                    "role","user",
                    "content", prompt
                )
            )
        );

        try {
            String body = mapper.writeValueAsString(map);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://openrouter.ai/api/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + this.key)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
            
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept((response) -> {
                    try {
                        JsonNode root = mapper.readTree(response);
                        String responseText = root.path("choices").get(0).path("message").path("content").asText();
                        System.out.println("Response: " + responseText);
                    } catch (Exception e) {
                        System.out.println("Erro ao processar a resposta: " + e.getMessage());
                    };
                });
        } catch (Exception e) {
            e.printStackTrace();
        };
    };
}
