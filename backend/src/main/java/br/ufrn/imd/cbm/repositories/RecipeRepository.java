package br.ufrn.imd.cbm.repositories;

import br.ufrn.imd.cbm.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface RecipeRepository extends JpaRepository<Recipe, Long>  {
    
}
