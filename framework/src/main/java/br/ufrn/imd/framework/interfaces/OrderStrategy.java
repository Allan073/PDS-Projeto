package br.ufrn.imd.framework.interfaces;

import br.ufrn.imd.framework.exceptions.NotFoundException;
import br.ufrn.imd.framework.models.Order;

public interface OrderStrategy {
    public void payment(Order order) throws NotFoundException;
}
