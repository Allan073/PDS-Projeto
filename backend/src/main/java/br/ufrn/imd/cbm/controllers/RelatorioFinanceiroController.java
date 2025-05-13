import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relatorio-financeiro")
public class RelatorioFinanceiroController {
    private final FinanceRepository financeRepository;
    private final RelatorioFinanceiroService relatorioFinanceiroService;

    public RelatorioFinanceiroController(FinanceRepository financeRepository, RelatorioFinanceiroService relatorioFinanceiroService) {
        this.financeRepository = financeRepository;
        this.relatorioFinanceiroService = relatorioFinanceiroService;
    }

    @GetMapping
    public String gerarRelatorioFinanceiro() {
        var operations = financeRepository.findAll();
        StringBuilder dados = new StringBuilder();

        for (var operation : operations) // operations tá dando problema
        {
            dados.append("Total: ").append(operation.getTotal())
                 .append(" | totalIncome: R$").append(operation.getTotalIncome())
                 .append(" | totalExpense: ").append(operation.getTotalExpense())
                 .append("\n");
        }

        return relatorioFinanceiroService.gerarRelatorio(dados.toString());
    }
}