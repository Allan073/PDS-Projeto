@Service
public class FinanceReportService {
    @Autowired
    private FinanceRepository repository;

    public String generateReport() {
        List<Finance> data = repository.findAll();
        StringBuilder report = new StringBuilder("Relatório Financeiro:\n");

        for (Finance entry : data) {
            report.append("Data: ").append(entry.getDate())
                  .append(", Receita: ").append(entry.getRevenue())
                  .append(", Despesa: ").append(entry.getExpenses()).append("\n");
        }

        return report.toString();
    }
}