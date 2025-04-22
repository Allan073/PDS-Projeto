package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.CreateOperationDto;
import br.ufrn.imd.cbm.models.Operation;
import br.ufrn.imd.cbm.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class OperationService {
    @Autowired
    private OperationRepository operationRepository;

    public void createOperation(CreateOperationDto createOperationDto) {
        Operation newOperation = Operation.builder()
                .type(createOperationDto.type())
                .date(Timestamp.valueOf(LocalDateTime.now()))
                .description(createOperationDto.description())
                .amount(createOperationDto.amount())
                .build();

        operationRepository.save(newOperation);
    }

    public Operation findOperationById(Long id) {
        return operationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
    }
}
