package br.ufrn.imd.cbm.dtos;

import java.util.Date;

public record ProductDTO(
        String name,
        String description,
        Integer quantity,
        Double price,
        Date productionDate
) {
}
