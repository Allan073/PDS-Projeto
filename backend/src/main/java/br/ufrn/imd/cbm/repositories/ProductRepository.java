package br.ufrn.imd.cbm.repositories;

import br.ufrn.imd.cbm.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>  {
    Optional<List<Product>> findByOrder_Id(Long orderId);
    Optional<List<Product>> findByOrder_UserId(Long userId);
}