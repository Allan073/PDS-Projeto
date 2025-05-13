package br.ufrn.imd.cbm.dtos;

import br.ufrn.imd.cbm.enums.DeliveryState;

import java.util.Date;

public record OrderDTO(
        Date orderDate,
        String description,
        DeliveryState orderState,
        Double totalPrice
) {
}
