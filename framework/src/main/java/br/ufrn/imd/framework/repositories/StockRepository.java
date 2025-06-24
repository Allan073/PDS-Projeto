package br.ufrn.imd.framework.repositories;

import br.ufrn.imd.framework.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>  {
    
}
