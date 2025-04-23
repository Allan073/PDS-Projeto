package br.ufrn.imd.cbm.models;

import jakarta.persistence.*;

@Entity
public class Address extends AbstractEntity{
    @Column(nullable = false)
    private String street;
    @Column
    private String complement;
    @Column(nullable = false)
    private int number;


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
