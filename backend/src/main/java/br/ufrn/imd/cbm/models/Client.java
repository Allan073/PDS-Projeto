package br.ufrn.imd.cbm.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Table(name = "clients") //cria o repo disso também
@Entity(name = "Client")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Client extends User {
    @OneToMany
    @Column(nullable = false)
    private List<Address> address;

    @OneToMany
    @Column(nullable = false)
    private List<Order> orders;

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
