package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.ai.OperationAgent;
import br.ufrn.imd.framework.enums.FinancialMovement;
import br.ufrn.imd.framework.models.Operation;
import br.ufrn.imd.framework.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinanceService {
    @Autowired
    private OperationService operationService;

    @Autowired
    private OperationAgent operationAgent;

    public void generateReport() {
        List<Operation> operations = operationService.findAllOperations();
        if (operations.isEmpty()) {
            return;
        }
        double total=0, totalIncome=0, totalExpense=0;
        for(Operation operation : operations){
            switch(operation.getType()){
                case FinancialMovement.INCOMING:
                    total += operation.getAmount();
                    totalIncome += operation.getAmount();
                    break;
                case FinancialMovement.OUTGOING:
                    total -= operation.getAmount();
                    totalExpense += operation.getAmount();
                    break;
                default: break;
            }
        }
        Map<String,Double> totals = new HashMap<>();
        totals.put("totalIncome", totalIncome);
        totals.put("totalExpense", totalExpense);
        totals.put("total", total);
        operationAgent.report(totals, operations, null);
    }
}
