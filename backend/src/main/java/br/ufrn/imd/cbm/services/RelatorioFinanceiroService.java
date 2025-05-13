import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class RelatorioFinanceiroService {
    private final String apiUrl = "https://api.openai.com/v1/completions";
    private final String apiKey = "SUA_CHAVE_OPENAI"; //falta colocar a chave 

    public String gerarRelatorio(String dados) {
        String prompt = "Gere um relatório financeiro com base nestes dados:\n" + dados;

        String jsonInput = "{\"model\": \"gpt-4\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(jsonInput, headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        return response.getBody();
    }
}