package br.ufrn.imd.cbm.models;

import br.ufrn.imd.cbm.enums.DeliveryState;
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
public class Order extends AbstractEntity {
    @Column(nullable = false)
    private Date orderDate;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private DeliveryState orderState;

    @Column(nullable = false)
    private Double totalPrice;

    @OneToMany
    @Column(nullable = false)
    private List<Product> products;

}
