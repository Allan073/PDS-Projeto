package br.ufrn.imd.cbm.dtos;

public record CreateItemDto(
        String name,
        String description,
        int quantity,
        double price
) {
}
