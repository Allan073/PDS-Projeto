package br.ufrn.imd.cbm.dtos;

public record AddressDTO(
        Long userId,
        String street,
        String complement,
        Integer number
) {
}
