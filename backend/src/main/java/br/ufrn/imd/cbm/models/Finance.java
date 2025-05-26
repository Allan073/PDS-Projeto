package br.ufrn.imd.cbm.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table (name = "finances")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Finance extends AbstractEntity{
    @OneToMany(mappedBy = "finance")
    private List<Operation> operations;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private Double totalIncome;
    
    @Column(nullable = false)
    private Double totalExpense;
}
