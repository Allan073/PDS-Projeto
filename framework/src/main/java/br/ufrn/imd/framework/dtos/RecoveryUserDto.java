package br.ufrn.imd.framework.dtos;

import br.ufrn.imd.framework.models.Role;

import java.util.List;

public record RecoveryUserDto(

        Long id,
        String email,
        List<Role> roles

) {
}
