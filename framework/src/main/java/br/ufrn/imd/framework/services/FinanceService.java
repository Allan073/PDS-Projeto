package br.ufrn.imd.framework.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.framework.ai.Agent;
import br.ufrn.imd.framework.enums.FinancialMovement;
import br.ufrn.imd.framework.models.Operation;

@Service
public class FinanceService {
    @Autowired
    private OperationService operationService;

    @Autowired
    private Agent agent;

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
        agent.report(total, totalIncome, totalExpense, operations);
    }
}
