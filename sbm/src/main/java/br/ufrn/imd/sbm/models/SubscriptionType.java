package br.ufrn.imd.sbm.models;

import br.ufrn.imd.framework.models.AbstractEntity;
import br.ufrn.imd.framework.models.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "subscriptiontypes")
@Entity(name = "SubscriptionType")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SubscriptionType extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @OneToMany
    @Column(nullable = false)
    private List<Item> items = new ArrayList<>();
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer frequency;

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }
}
