package br.ufrn.imd.framework.ai;


import br.ufrn.imd.framework.models.Operation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Component
public abstract class AbstractAgent<Type> {
    private final String key;

    public AbstractAgent(Environment env) {
        this.key = env.getProperty("ai.key");
    };

    public void report(Map<String,Double> totals, List<Type> list, Map<Type,Double> map) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        StringBuilder prompt = new StringBuilder();
        promptBuilderTotals(totals, prompt);
        promptBuilderList(list, prompt);
        String finalPrompt = prompt.toString();
        queryAI(finalPrompt, mapper, client);
    }

    abstract protected void promptBuilderTotals(Map<String,Double> totals, StringBuilder prompt);

    abstract protected void promptBuilderList(List<Type> list, StringBuilder prompt);

    abstract protected void promptBuilderMap(Map<Type, Double> map, StringBuilder prompt);

    protected void queryAI(String finalPrompt, ObjectMapper mapper, HttpClient client) {
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
                        System.out.println(response);
                        JsonNode root = mapper.readTree(response);
                        String responseText = root.path("choices").get(0).path("message").path("content").asText();
                        System.out.println("Response: " + responseText); //Return seria aqui
                    } catch (Exception e) {
                        System.out.println("Erro ao processar a resposta: " + e.getMessage());
                    };
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }

    ;
}
