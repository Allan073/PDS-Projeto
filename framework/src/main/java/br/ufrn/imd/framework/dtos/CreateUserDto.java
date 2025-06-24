package br.ufrn.imd.framework.dtos;

import br.ufrn.imd.framework.enums.RoleName;

public record CreateUserDto(
        String name,
        String email,
        String password,
        RoleName role //Isso não deveria ser list?
) {
}
