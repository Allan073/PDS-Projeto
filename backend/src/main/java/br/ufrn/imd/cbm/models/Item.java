package br.ufrn.imd.cbm.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Item extends AbstractEntity{
    @Column(nullable = false)
    private String name;
    @Column //vou deixar como nullable por enquanto - Artur
    private String description;
    @Column (nullable = false)
    private int quantity;
    @Column (nullable = false)
    private double price;
    @Column (nullable = false)
    private boolean orderable = false;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isOrderable() {
        return orderable;
    }

    public void setOrderable(boolean orderable) {
        this.orderable = orderable;
    }
}
