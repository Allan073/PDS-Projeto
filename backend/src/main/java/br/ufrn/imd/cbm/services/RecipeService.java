package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.CreateRecipeDTO;
import br.ufrn.imd.cbm.models.Recipe;
import br.ufrn.imd.cbm.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//Saudades .h
@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    public void createRecipe(CreateRecipeDTO createRecipeDTO) {
        Recipe newRecipe = Recipe.builder()
                .name(createRecipeDTO.name())
                .ingredients(createRecipeDTO.ingredients())
                .cost(createRecipeDTO.cost())
                .build();
        recipeRepository.save(newRecipe);
    }

    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }

    /*public void updateRecipe(Long id, CreateRecipeDTO createRecipeDTO) {
        Recipe updatingrecipe = findRecipeById(id);
        //talvez checar se todos os campos são nulos e jogar um erro pra esse caso?
        if (createRecipeDTO.name() != null) updatingrecipe.setName(createRecipeDTO.name());
        if (createRecipeDTO.ingredients() != null) updatingrecipe.setIngredients(createRecipeDTO.ingredients());
        if (createRecipeDTO.cost() != null) updatingrecipe.setCost(createRecipeDTO.cost());
    }*/

    public void deleteRecipe(Long id) {
        findRecipeById(id);
        recipeRepository.deleteById(id);
    }
}
