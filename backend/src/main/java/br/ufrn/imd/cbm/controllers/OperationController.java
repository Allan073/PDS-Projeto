package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.cbm.dtos.CreateOperationDto;
import br.ufrn.imd.cbm.models.Operation;
import br.ufrn.imd.cbm.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class OperationController {
    @Autowired
    private OperationService operationService;

    @PostMapping
    public ResponseEntity<Void> createOperation(@RequestBody CreateOperationDto createOperationDto) {
        operationService.createOperation(createOperationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operation> getOperationById(@PathVariable Long id) {
        Operation operation = operationService.findOperationById(id);
        return ResponseEntity.status(HttpStatus.OK).body(operation);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Operation>> getAllOperations() {
        List<Operation> operations = operationService.findAllOperations();
        return ResponseEntity.status(HttpStatus.OK).body(operations);
    }
}
