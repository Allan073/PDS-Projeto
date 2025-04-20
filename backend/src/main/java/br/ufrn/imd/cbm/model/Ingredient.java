package br.ufrn.imd.cbm.model;

import jakarta.persistence.*;

@Entity
@Table (name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Item item; //"Ingredient" não existe no diagrama que eu tô olhando agora então me avisa se isso tá errado - Artur
    @Column (nullable = false)
    private double quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
