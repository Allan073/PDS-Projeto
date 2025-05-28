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

import br.ufrn.imd.cbm.models.Operation;

@Component
public class Agent {
    private String key;

    public Agent(Environment env) {
        this.key = env.getProperty("ai.key");
    };

    public void report(double total, double totalIncome, double totalExpense, List<Operation> operations) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        StringBuilder prompt = new StringBuilder();
    prompt.append(String.format(
        "Relatório de valores:\n" +
        "- Total: %.2f\n" +
        "- Total Income: %.2f\n" +
        "- Total Expense: %.2f\n" +
        "Por favor, gere um relatório detalhado com essas informações.\n\n",
        total, totalIncome, totalExpense
    ));
    
    prompt.append("Lista de operações:\n");
    
    for (Operation op : operations) {
        prompt.append(String.format("- Tipo: %s | Valor: %.2f | Data: %s\n", op.getType(), op.getAmount(), op.getDate()));
    }
    
    // Aqui você pode utilizar o prompt na requisição
    String finalPrompt = prompt.toString();




        Map<String, Object> map = Map.of(
            "model", "nousresearch/deephermes-3-mistral-24b-preview:free",
            "messages", List.of(
                Map.of(
                    "role","user",
                    "content", finalPrompt
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
