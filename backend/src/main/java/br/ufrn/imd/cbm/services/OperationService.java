package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.CreateOperationDto;
import br.ufrn.imd.cbm.enums.FinancialMovement;
import br.ufrn.imd.cbm.exceptions.InvalidArgumentException;
import br.ufrn.imd.cbm.exceptions.NotFoundException;
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

    public void createOperation(CreateOperationDto createOperationDto) throws InvalidArgumentException {
        if(createOperationDto.amount() == null || createOperationDto.amount() < 0) {
            throw new InvalidArgumentException("Quantidade nula ou inválida!");
        }
        Operation newOperation = Operation.builder()
                .type(createOperationDto.type())
                .date(LocalDate.now())
                .description(createOperationDto.description())
                .amount(createOperationDto.amount())
                .build();
        operationRepository.save(newOperation);
    }

    public Operation findOperationById(Long id) throws NotFoundException {
        return operationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transação não encontrada"));
    }
    public void createFromOrder(Order order) throws InvalidArgumentException {
        try {createOperation(new CreateOperationDto(FinancialMovement.INCOMING,"order " + order.getId(), order.getTotalPrice()));}
        catch (InvalidArgumentException e) {
            throw new InvalidArgumentException("Ocorreu um erro na criação da operação atrelada da Order!");
        }
    }
    public List<Operation> findAllOperations() {
        return operationRepository.findAll();
    }
}
