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
    private int discount=20;

    @Autowired
    private OperationService operationService;

    @Override
    public void payment(Order order) throws NotFoundException {
        try {
            operationService.createOperation(
                    new CreateOperationDto(FinancialMovement.INCOMING,
                            "order " + order.getId() + " desconto: " + discount +"%",
                            order.getTotalPrice()-order.getTotalPrice()*discount/100)
            );
        }
        catch (InvalidArgumentException e) {
            throw e;
        }
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
