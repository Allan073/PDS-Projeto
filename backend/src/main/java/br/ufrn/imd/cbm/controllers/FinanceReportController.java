package br.ufrn.imd.cbm.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufrn.imd.cbm.ai.Agent;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/report")
public class FinanceReportController {
    @Autowired
    private Agent agent;

    @GetMapping
    public String getMethodName() {
        agent.report();
        return "";
    }
}
