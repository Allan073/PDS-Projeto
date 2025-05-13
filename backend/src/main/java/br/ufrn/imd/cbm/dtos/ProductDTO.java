package br.ufrn.imd.cbm.dtos;

import java.util.Date;

public record ProductDTO(
        Long userId,
        Long orderId,
        String name,
        String description,
        Integer quantity,
        Double price,
        Date productionDate
) {
}
