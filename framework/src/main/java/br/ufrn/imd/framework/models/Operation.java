package br.ufrn.imd.framework.models;

import br.ufrn.imd.framework.enums.FinancialMovement;
import jakarta.persistence.*;
import lombok.*;

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
