package br.ufrn.imd.cbm.models;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "recipes")
public class Recipe extends AbstractEntity {

    @Column (nullable = false)
    private String name;
    @OneToMany //Note: OneToMany não aceita arrays.
    @Column (nullable = false)
    private List<Ingredient> ingredients;
    @Column (nullable = false)
    private double cost; //não seria essa derivada? ou isso é o preço a ser apresentado ao cliente? - Artur


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
