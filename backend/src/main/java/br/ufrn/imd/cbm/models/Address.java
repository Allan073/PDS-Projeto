package br.ufrn.imd.cbm.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends AbstractEntity{
    @ManyToOne
    @JoinColumn(nullable = false, name = "userid")
    private User user;
    @Column(nullable = false)
    private String street;
    @Column
    private String complement;
    @Column(nullable = false)
    private int number;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
