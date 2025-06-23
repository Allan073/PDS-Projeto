package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.CreateRecipeDTO;
import br.ufrn.imd.cbm.exceptions.InvalidArgumentException;
import br.ufrn.imd.cbm.exceptions.NotFoundException;
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
    @Autowired
    ItemService itemService;

    public void createRecipe(CreateRecipeDTO createRecipeDTO) throws InvalidArgumentException {
        if (createRecipeDTO.name() == null || createRecipeDTO.name().isEmpty()) {
            throw new InvalidArgumentException("Nome vazio!");
        }
        Recipe newRecipe = Recipe.builder()
                .name(createRecipeDTO.name())
                .cost(createRecipeDTO.cost())
                .build();
        recipeRepository.save(newRecipe);
    }

    public void createRecipeItems(CreateRecipeDTO createRecipeDTO) throws NotFoundException {
        try {
            Recipe newRecipe = Recipe.builder()
                    .name(createRecipeDTO.name())
                    .items(itemService.findAllInList(createRecipeDTO.items()))
                    .cost(createRecipeDTO.cost())
                    .build();
            recipeRepository.save(newRecipe);
        }
        catch (NotFoundException e) {
            throw e;
        }
    }

    public void addItemToRecipe(Long id, String itemname) throws NotFoundException {
        try {
            Recipe recipe = findRecipeById(id);
            Item item = itemService.findItemByName(itemname);
            recipe.getItems().add(item);
            recipeRepository.save(recipe);
        }
        catch (NotFoundException e) {
            throw e;
        }
    }



    public Recipe findRecipeById(Long id) throws NotFoundException {
        return recipeRepository.findById(id).orElseThrow(() -> new NotFoundException("Receita não encontrada"));
    }

    public void updateRecipe(Long id, CreateRecipeDTO createRecipeDTO) throws NotFoundException {
        try {
            Recipe updatingrecipe = findRecipeById(id);
            if (updatingrecipe == null) {
                throw new RuntimeException("Raceita não encontrado");
            }
            if (createRecipeDTO.name() != null) updatingrecipe.setName(createRecipeDTO.name());
            if (createRecipeDTO.items() != null)
                updatingrecipe.setItems(itemService.findAllInList(createRecipeDTO.items()));
            if (createRecipeDTO.cost() != null) updatingrecipe.setCost(createRecipeDTO.cost());
            recipeRepository.save(updatingrecipe);
        }
        catch (NotFoundException e) {
            throw e;
        }
    }

    public void deleteRecipe(Long id) throws NotFoundException {
        try {
            findRecipeById(id);
            recipeRepository.deleteById(id);
        }
        catch (NotFoundException e) {
            throw e;
        }
    }

    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll();
    }
}
