package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.framework.annotations.AdminOnly;
import br.ufrn.imd.framework.dtos.CreateRecipeDTO;
import br.ufrn.imd.framework.exceptions.NotFoundException;
import br.ufrn.imd.framework.models.Recipe;
import br.ufrn.imd.framework.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @AdminOnly
    @PostMapping
    public ResponseEntity<Void> createRecipe(@RequestBody CreateRecipeDTO createRecipeDTO) {
        recipeService.createRecipe(createRecipeDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @AdminOnly
    @PostMapping("/{id}")
    public ResponseEntity<String> addItemToRecipe(@PathVariable Long id, @RequestBody String itemname) {
        try {
            recipeService.addItemToRecipe(id, itemname);
            return new ResponseEntity<>("Item adicionado com sucesso", HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @AdminOnly
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        try {
            Recipe recipe = recipeService.findRecipeById(id);
            return ResponseEntity.status(HttpStatus.OK).body(recipe);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @AdminOnly
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecipeById(@PathVariable Long id, @RequestBody CreateRecipeDTO createRecipeDTO) {
        try {
            recipeService.updateRecipe(id, createRecipeDTO);
            return new ResponseEntity<>("Receita atualizada com sucesso", HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @AdminOnly
    @DeleteMapping("/{id}") public ResponseEntity<String> deleteRecipeById(@PathVariable Long id) {
        try {
            recipeService.deleteRecipe(id);
            return new ResponseEntity<>("Receita deletada com sucesso", HttpStatus.NO_CONTENT);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @AdminOnly
    @GetMapping("/all") public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.findAllRecipes();
        return ResponseEntity.status(HttpStatus.OK).body(recipes);
    }
}
