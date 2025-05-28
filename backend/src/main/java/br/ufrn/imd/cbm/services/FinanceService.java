package br.ufrn.imd.cbm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.cbm.ai.Agent;
import br.ufrn.imd.cbm.enums.FinancialMovement;
import br.ufrn.imd.cbm.models.Operation;
import br.ufrn.imd.cbm.repositories.OperationRepository;
@Service
public class FinanceService {
    @Autowired
    private OperationRepository repository;

    @Autowired
    private Agent agent;

    public void generateReport() {
        List<Operation> operations = repository.findAll();

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
