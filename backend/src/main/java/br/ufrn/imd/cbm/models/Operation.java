package br.ufrn.imd.cbm.models;

import br.ufrn.imd.cbm.enums.FinancialMovement;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table (name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private FinancialMovement type;
    @Column(nullable = false)
    //Por via de dúvidas, existe algum motivo pra a gente tar usando column pra os tipos e
    private Date date;
    @Column
    private String description;
    @Column(nullable = false)
    private double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FinancialMovement getType() {
        return type;
    }

    public void setType(FinancialMovement type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
