package br.ufrn.imd.sbm.controllers;


import br.ufrn.imd.sbm.services.SubscriptionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class FinanceReportController {
    @Autowired
    private SubscriptionReportService subscriptionReportService;

    @GetMapping
    public String generation() {
        subscriptionReportService.generateSubscriptionReport();
        return "";
    }
}
