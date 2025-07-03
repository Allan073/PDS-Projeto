package br.ufrn.imd.sbm.ai;

import br.ufrn.imd.framework.ai.AbstractAgent;
import br.ufrn.imd.sbm.models.SubscriptionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

@Component
public class SubscriptionAgent extends AbstractAgent<SubscriptionType> {

    public SubscriptionAgent(Environment environment) {
        super(environment);
    }

    @Override
    protected void promptBuilderTotals(Map<String, Double> totals, StringBuilder prompt) {
        prompt.append(String.format("\nEntrada total: R$%.2f\nMédia: R$%.2f\n", totals.get("totalIncoming"), totals.get("totalAvg")));
    }

    @Override
    protected void promptBuilderList(List<SubscriptionType> list, StringBuilder prompt) {
        return;
    }

    @Override
    protected void promptBuilderMap(Map<SubscriptionType, Double> map, StringBuilder prompt) {
        prompt.append("Gere um relatório sobre as seguintes assinaturas, utilizando seus valores mensais já totalizados:\nAssinaturas:\n");
        for (Map.Entry<SubscriptionType, Double> entry : map.entrySet()) {
            prompt.append(String.format("%s: R$%.2f\n", entry.getKey().getName(), entry.getValue()));
        }
    }
}
