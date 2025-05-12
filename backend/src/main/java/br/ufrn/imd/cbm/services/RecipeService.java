package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.CreateRecipeDTO;
import br.ufrn.imd.cbm.models.Item;
import br.ufrn.imd.cbm.models.Recipe;
import br.ufrn.imd.cbm.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


//Saudades .h
@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    public void createRecipe(CreateRecipeDTO createRecipeDTO) {
        Recipe newRecipe = Recipe.builder()
                .name(createRecipeDTO.name())
                .items(createRecipeDTO.items())
                .cost(createRecipeDTO.cost())
                .build();
        recipeRepository.save(newRecipe);
    }

    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }

    public void updateRecipe(Long id, CreateRecipeDTO createRecipeDTO) {
        Recipe updatingrecipe = findRecipeById(id);
        if (createRecipeDTO.name() != null) updatingrecipe.setName(createRecipeDTO.name());
        if (createRecipeDTO.items() != null) updatingrecipe.setItems(createRecipeDTO.items());
        if (createRecipeDTO.cost() != null) updatingrecipe.setCost(createRecipeDTO.cost());
        recipeRepository.save(updatingrecipe);
    }

    public void deleteRecipe(Long id) {
        findRecipeById(id);
        recipeRepository.deleteById(id);
    }

    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll();
    }
}
