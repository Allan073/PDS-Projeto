package br.ufrn.imd.framework.dtos;

public record CreateItemDto(
        String name,
        String description,
        Integer quantity,
        Double price,
        Boolean orderable
) {
}
