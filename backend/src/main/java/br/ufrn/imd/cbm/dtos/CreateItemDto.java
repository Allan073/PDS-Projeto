package br.ufrn.imd.cbm.dtos;

public record CreateItemDto(
        String name,
        String description,
        Integer quantity,
        Double price
) {
}
