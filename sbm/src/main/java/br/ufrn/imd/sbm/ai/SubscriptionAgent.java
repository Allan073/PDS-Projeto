package br.ufrn.imd.sbm.ai;

import br.ufrn.imd.framework.ai.Agent;
import br.ufrn.imd.sbm.models.SubscriptionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.util.Map;

@Component
public class SubscriptionAgent extends Agent {

    public SubscriptionAgent(Environment environment) {
        super(environment);
    }

    public void report(Map<SubscriptionType, Double> summedPrices, Double totalIncoming, Double totalAvg) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder prompt = new StringBuilder();
        prompt.append("Gere um relatório sobre as seguintes assinaturas, utilizando seus valores mensais já totalizados:\nAssinaturas:\n");
        for (Map.Entry<SubscriptionType, Double> entry : summedPrices.entrySet()) {
            prompt.append(String.format("%s: R$%.2f\n", entry.getKey().getName(), entry.getValue()));
        }
        prompt.append(String.format("\nEntrada total: R$%.2f\nMédia: R$%.2f\n", totalIncoming, totalAvg));
        String finalPrompt = prompt.toString();
        queryAI(finalPrompt,mapper,client);
    }
}
