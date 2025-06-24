package br.ufrn.imd.framework.dtos;

public record AddressDTO(
        String street,
        String complement,
        Integer number
) {
}
