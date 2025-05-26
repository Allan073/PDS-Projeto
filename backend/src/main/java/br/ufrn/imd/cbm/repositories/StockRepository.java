package br.ufrn.imd.cbm.repositories;

import br.ufrn.imd.cbm.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>  {
    
}
