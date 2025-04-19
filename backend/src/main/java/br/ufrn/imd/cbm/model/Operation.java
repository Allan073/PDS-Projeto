package br.ufrn.imd.cbm.model;

import br.ufrn.imd.cbm.enums.FinancialMovement;

import java.util.Date;

public class Operation {
    private Long id;
    private FinancialMovement type;
    private Date date;
    private String description;
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
