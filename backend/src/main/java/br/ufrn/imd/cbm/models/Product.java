package br.ufrn.imd.cbm.models;

import java.util.Date;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "products") //cria o repositório disso quando puder. ou talvez a gente só bota date em item e deixa nullable?
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Product extends Item{
    @Column (nullable = false)
    private Date productionDate;

    public Date getProductionDate() {
        return productionDate;
    }
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }
}
