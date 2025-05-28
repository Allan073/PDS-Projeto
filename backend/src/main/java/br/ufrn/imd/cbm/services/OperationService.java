package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.CreateOperationDto;
import br.ufrn.imd.cbm.enums.FinancialMovement;
import br.ufrn.imd.cbm.models.Operation;
import br.ufrn.imd.cbm.models.Order;
import br.ufrn.imd.cbm.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OperationService {
    @Autowired
    private OperationRepository operationRepository;

    public void createOperation(CreateOperationDto createOperationDto) {
        Operation newOperation = Operation.builder()
                .type(createOperationDto.type())
                .date(LocalDate.now())
                .description(createOperationDto.description())
                .amount(createOperationDto.amount())
                .build();

        operationRepository.save(newOperation);
    }
    public void createFromOrder(Order order) {
        Operation newOperation = Operation.builder()
                .type(FinancialMovement.INCOMING)
                .date(LocalDate.now())
                .description("Order " + order.getId().toString())
                .amount(order.getTotalPrice())
                .build();
        operationRepository.save(newOperation);
    }

    public Operation findOperationById(Long id) {
        return operationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
    }

    public List<Operation> findAllOperations() {
        return operationRepository.findAll();
    }
}
