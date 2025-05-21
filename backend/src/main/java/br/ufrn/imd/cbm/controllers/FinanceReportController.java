import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import br.ufrn.imd.cbm.services.FinanceReportService; // Ajuste o caminho conforme necessário

@RestController
@RequestMapping("/report")
public class FinanceReportController {
    @Autowired
    private FinanceReportService service;

    @GetMapping
    public ResponseEntity<String> getReport() {
        return ResponseEntity.ok(service.generateReport());
    }
}