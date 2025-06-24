package br.ufrn.imd.framework.dtos;

import java.util.List;

//talvez mudar pra só "RecipeDTO"?
public record CreateRecipeDTO(
        String name,
        Double cost,
        List<String> items
) {

}
