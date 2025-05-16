package br.ufrn.imd.cbm.dtos;

public record AddressDTO(
        String street,
        String complement,
        Integer number
) {
}
