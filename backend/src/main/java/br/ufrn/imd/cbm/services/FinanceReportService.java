package br.ufrn.imd.cbm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import br.ufrn.imd.cbm.models.Finance; 
import br.ufrn.imd.cbm.repositories.FinanceRepository; 

import ai.llamaindex.LlamaModel; //problema aqui

@Service
public class FinanceReportService {
    @Autowired
    private FinanceRepository repository;

    public String generateReport() {
        List<Finance> data = repository.findAll();
        StringBuilder report = new StringBuilder("Relatório Financeiro:\n");

        for (Finance entry : data) {
            report.append("total: ").append(entry.getTotal())
                  .append(", Income: ").append(entry.getTotalIncome())
                  .append(", Despesa: ").append(entry.getTotalExpense()).append("\n");
        }

        return report.toString();
    }

     public String generateAIReport() {
        List<Finance> data = repository.findAll();
        String rawData = data.stream()
            .map(entry -> "total: " + entry.getTotal() + ", Income: " + entry.getIncome() + ", Despesa: " + entry.getTotalExpense())
            .collect(Collectors.joining("\n"));

        // Chamando um modelo de IA (Llama 2) para gerar um relatório mais elaborado
        String aiReport = llamaModel.generateReport(rawData);
        return aiReport;
    }



}
