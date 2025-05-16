package br.ufrn.imd.cbm.dtos;

import java.util.Date;

public record ProductDTO(
        Long userid,
        Long orderid,
        String name,
        String description,
        Integer quantity,
        Double price,
        Date productiondate
) {
}
