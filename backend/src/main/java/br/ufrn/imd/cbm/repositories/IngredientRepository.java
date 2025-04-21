package br.ufrn.imd.cbm.repositories;

import br.ufrn.imd.cbm.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface IngredientRepository extends JpaRepository<Ingredient, Long>  {
    
}
