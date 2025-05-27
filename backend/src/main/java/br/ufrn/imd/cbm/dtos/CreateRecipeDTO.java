package br.ufrn.imd.cbm.dtos;

import br.ufrn.imd.cbm.models.Item;

import java.util.List;

//talvez mudar pra só "RecipeDTO"?
public record CreateRecipeDTO(
        String name,
        Double cost,
        List<String> items
) {

}
