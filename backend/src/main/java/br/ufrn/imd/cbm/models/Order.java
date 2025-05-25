package br.ufrn.imd.cbm.models;

import br.ufrn.imd.cbm.enums.DeliveryState;
import br.ufrn.imd.cbm.serializers.AbstractEntityListSerializer;
import br.ufrn.imd.cbm.serializers.AbstractEntitySerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders") //cria o repo disso
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
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
    private Date orderDate;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private DeliveryState orderState;

    @Column(nullable = false)
    private Double totalPrice;

    @OneToMany(mappedBy = "order")
    @Column(nullable = false)
    @JsonSerialize(using = AbstractEntityListSerializer.class)
    private List<Product> products;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
