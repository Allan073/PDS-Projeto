package br.ufrn.imd.cbm.dtos;

import br.ufrn.imd.cbm.enums.FinancialMovement;

public record CreateOperationDto(
        FinancialMovement type,
        String description,
        Double amount
) {
}
