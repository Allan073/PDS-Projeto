package br.ufrn.imd.cbm.ai;

import br.ufrn.imd.framework.ai.AbstractAgent;
import br.ufrn.imd.framework.models.Operation;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OperationAgent extends AbstractAgent<Operation> {
    public OperationAgent(Environment env) {
        super(env);
    }

    @Override
    protected void promptBuilderTotals(Map<String, Double> totals, StringBuilder prompt) {
        prompt.append(String.format(
                "Relatório de valores:\n" +
                        "- Total: %.2f\n" +
                        "- Total Income: %.2f\n" +
                        "- Total Expense: %.2f\n",
                totals.get("total"), totals.get("totalIncome"), totals.get("totalExpense")
        ));
    }

    @Override
    protected void promptBuilderList(List<Operation> list, StringBuilder prompt) {
        prompt.append("Lista de operações:\n");
        for (Operation op : list) {
            prompt.append(String.format("- Tipo: %s | Valor: %.2f | Data: %s\n", op.getType(), op.getAmount(), op.getDate()));
        }
    }

    @Override
    protected void promptBuilderMap(Map<Operation, Double> map, StringBuilder prompt) {
        return;
    }
}
