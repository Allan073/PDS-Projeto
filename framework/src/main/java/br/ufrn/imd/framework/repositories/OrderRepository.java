package br.ufrn.imd.framework.repositories;

import br.ufrn.imd.framework.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>  {
    List<Order> findByUser_Id(Long userId);
}