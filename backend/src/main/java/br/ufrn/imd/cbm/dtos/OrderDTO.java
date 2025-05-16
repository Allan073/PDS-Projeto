package br.ufrn.imd.cbm.dtos;

import br.ufrn.imd.cbm.enums.DeliveryState;

import java.util.Date;

public record OrderDTO(
        Date orderdate,
        String description,
        DeliveryState orderstate,
        Double totalprice
) {
}
