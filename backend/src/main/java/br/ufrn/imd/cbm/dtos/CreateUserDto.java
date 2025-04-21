package br.ufrn.imd.cbm.dtos;

import br.ufrn.imd.cbm.enums.RoleName;

public record CreateUserDto(
        String name,
        String email,
        String password,
        RoleName role
) {
}
