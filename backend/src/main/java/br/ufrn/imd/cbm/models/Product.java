package br.ufrn.imd.cbm.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Product extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    @Column //vou deixar como nullable por enquanto - Artur
    private String description;
    @Column (nullable = false)
    private int quantity;
    @Column (nullable = false)
    private double price;
    @Column (nullable = false)
    private Date productionDate;

    @ManyToOne
    @JoinColumn(nullable = false, name = "order_id")
    @JsonBackReference
    private Order order;

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
    public Date getProductionDate() {
        return productionDate;
    }
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
}
