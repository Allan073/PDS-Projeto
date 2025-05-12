package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.cbm.dtos.CreateRecipeDTO;
import br.ufrn.imd.cbm.models.Recipe;
import br.ufrn.imd.cbm.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @PostMapping
    public ResponseEntity<Void> createRecipe(@RequestBody CreateRecipeDTO createRecipeDTO) {
        recipeService.createRecipe(createRecipeDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeService.findRecipeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(recipe);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> updateRecipeById(@PathVariable Long id, @RequestBody CreateRecipeDTO createRecipeDTO) {
        recipeService.updateRecipe(id,createRecipeDTO);
        return new ResponseEntity<>("Receita atualizada com sucesso",HttpStatus.OK);
    }

    @DeleteMapping("/{id}") public ResponseEntity<String> deleteRecipeById(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>("Receita deletada com sucesso",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all") public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.findAllRecipes();
        return ResponseEntity.status(HttpStatus.OK).body(recipes);
    }
}
