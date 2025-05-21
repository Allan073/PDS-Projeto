package br.ufrn.imd.cbm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import com.seuprojeto.model.Finance; // Ajuste o pacote conforme sua estrutura
import com.seuprojeto.repository.FinanceRepository; // Ajuste o caminho conforme necessário

import com.llamaindex.LlamaModel; // Exemplo fictício, ajuste conforme a biblioteca utilizada

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
                  .append(", Despesa: ").append(entry.getExpense()).append("\n");
        }

        return report.toString();
    }
}
