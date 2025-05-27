package br.ufrn.imd.cbm.models;

import br.ufrn.imd.cbm.enums.FinancialMovement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table (name = "operations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Operation extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    private FinancialMovement type;

    @Column(nullable = false)
    private LocalDate date;

    @Column
    private String description;

    @Column(nullable = false)
    private double amount;

}
