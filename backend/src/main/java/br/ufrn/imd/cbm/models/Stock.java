package br.ufrn.imd.cbm.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="stock")
public class Stock extends AbstractEntity{

    @Column(nullable=false) //supondo que isso é a quantidade total de itens, não seria melhor fazer disso um derivado?
    private int quantityItems;
    @OneToMany //Mesma coisa que eu falei sobre arrays em Recipe
    @Column(nullable=false)
    private List<Item> items;


    public int getQuantityItems() {
        return quantityItems;
    }

    public void setQuantityItems(int quantityItems) {
        this.quantityItems = quantityItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
