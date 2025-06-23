package br.ufrn.imd.cbm.dtos;

import br.ufrn.imd.cbm.enums.DeliveryState;

import java.util.Date;
import java.util.List;

public record OrderDTO(
        Date orderdate,
        String description,
        List<Long> items,
        Integer orderstate,
        Double totalprice
) {
}
