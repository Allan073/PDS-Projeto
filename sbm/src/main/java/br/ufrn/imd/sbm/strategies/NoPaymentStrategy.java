package br.ufrn.imd.sbm.strategies;

import br.ufrn.imd.framework.interfaces.OrderStrategy;
import br.ufrn.imd.framework.models.Order;
import org.springframework.stereotype.Component;

@Component
public class NoPaymentStrategy implements OrderStrategy {

    @Override
    public void payment(Order order) {} //este espaço deixado em branco propositalmente
}
