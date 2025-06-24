package br.ufrn.imd.framework.repositories;

import br.ufrn.imd.framework.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>  {

    Optional<Item> findByName(String name);

    List<Item> findByOrderable(boolean orderable);
}
