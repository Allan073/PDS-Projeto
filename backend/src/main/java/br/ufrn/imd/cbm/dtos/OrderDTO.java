package br.ufrn.imd.cbm.dtos;

import br.ufrn.imd.cbm.enums.DeliveryState;

import java.util.Date;

public record OrderDTO(
        Long userId,
        Date orderDate,
        String description,
        DeliveryState orderState,
        Double totalPrice
) {
}
