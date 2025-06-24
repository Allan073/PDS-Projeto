package br.ufrn.imd.cbm.controllers;


import br.ufrn.imd.framework.services.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
