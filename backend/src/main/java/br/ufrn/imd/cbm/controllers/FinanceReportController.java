package br.ufrn.imd.cbm.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufrn.imd.cbm.ai.Agent;
import br.ufrn.imd.cbm.models.Operation;
import br.ufrn.imd.cbm.repositories.OperationRepository;
import br.ufrn.imd.cbm.services.FinanceService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/report")
public class FinanceReportController {
    @Autowired
    private FinanceService financeService;

    @GetMapping
    public String generation() {
        financeService.generateReport();
        return "";
    }
}
