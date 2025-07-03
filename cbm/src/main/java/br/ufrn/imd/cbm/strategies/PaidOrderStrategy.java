package br.ufrn.imd.cbm.strategies;

import br.ufrn.imd.framework.exceptions.InvalidArgumentException;
import br.ufrn.imd.framework.exceptions.NotFoundException;
import br.ufrn.imd.framework.interfaces.OrderStrategy;
import br.ufrn.imd.framework.models.Order;
import br.ufrn.imd.framework.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaidOrderStrategy implements OrderStrategy {
    @Autowired
    private OperationService operationService;

    @Override
    public void payment(Order order) throws NotFoundException {
        try {
            operationService.createFromOrder(order);
        } catch (InvalidArgumentException e) {
            throw e;
        }
    }
}
