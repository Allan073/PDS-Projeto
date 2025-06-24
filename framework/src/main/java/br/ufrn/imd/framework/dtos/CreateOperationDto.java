package br.ufrn.imd.framework.dtos;

import br.ufrn.imd.framework.enums.FinancialMovement;

public record CreateOperationDto(
        FinancialMovement type,
        String description,
        Double amount
) {
}
