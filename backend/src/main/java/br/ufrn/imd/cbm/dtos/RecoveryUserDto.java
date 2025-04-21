package br.ufrn.imd.cbm.dtos;

import br.ufrn.imd.cbm.models.Role;

import java.util.List;

public record RecoveryUserDto(

        Long id,
        String email,
        List<Role> roles

) {
}
