package br.ufrn.imd.framework.models;

import br.ufrn.imd.framework.enums.DeliveryState;
import br.ufrn.imd.framework.serializers.AbstractEntityListSerializer;
import br.ufrn.imd.framework.serializers.AbstractEntitySerializer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") //cria o repo disso
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Order extends AbstractEntity {
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    @JsonSerialize(using = AbstractEntitySerializer.class)
    private User user;

    @Column(nullable = false)
    //@JsonSerialize(using = LocalDateTimeSerializer.class)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDate orderDate;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private DeliveryState orderState;

    @Column(nullable = false)
    private Double totalPrice;


    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL, mappedBy = "id")
    @Column(nullable = false)
    @JsonSerialize(using = AbstractEntityListSerializer.class)
    private List<Item> items;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DeliveryState getOrderState() {
        return orderState;
    }

    public void setOrderState(DeliveryState orderState) {
        this.orderState = orderState;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
