package br.ufrn.imd.cbm.strategies;

import br.ufrn.imd.framework.dtos.CreateOperationDto;
import br.ufrn.imd.framework.enums.FinancialMovement;
import br.ufrn.imd.framework.exceptions.InvalidArgumentException;
import br.ufrn.imd.framework.exceptions.NotFoundException;
import br.ufrn.imd.framework.interfaces.OrderStrategy;
import br.ufrn.imd.framework.models.Order;
import br.ufrn.imd.framework.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscountOrderStrategy implements OrderStrategy {
    private double discount;

    @Autowired
    private OperationService operationService;

    public DiscountOrderStrategy() {
    }

    public DiscountOrderStrategy(double discount) {
        this.discount = discount;
    }

    @Override
    public void payment(Order order) throws NotFoundException {
        try {
            operationService.createOperation(
                    new CreateOperationDto(FinancialMovement.INCOMING,
                            "order " + order.getId() + " desconto: " + discount*100 +"%",
                            order.getTotalPrice()-order.getTotalPrice()*discount)
            );
        }
        catch (InvalidArgumentException e) {
            throw e;
        }
    }
}
