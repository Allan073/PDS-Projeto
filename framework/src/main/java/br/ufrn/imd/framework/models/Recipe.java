package br.ufrn.imd.framework.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table (name = "recipes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe extends AbstractEntity {

    @Column (nullable = false)
    private String name;
    @OneToMany //Note: OneToMany não aceita arrays.
    @Column (nullable = false)
    private List<Item> items;
    @Column (nullable = false)
    private double cost; //não seria essa derivada? ou isso é o preço a ser apresentado ao cliente? - Artur


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
