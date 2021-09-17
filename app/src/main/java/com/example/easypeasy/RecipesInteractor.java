package com.example.easypeasy;

import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.spoonacular.IngredientRequest;
import com.example.easypeasy.spoonacular.RecipesRequest;
import com.example.easypeasy.models.Recipe;

import java.util.List;

public class RecipesInteractor implements RecipesInteractorInput {

    public RecipesPresenterInput output;

    @Override
    public void fetchRecipesData(RecipesRequest request, List<Ingredient> ingredientList) {
        if (request.isSearchByIngredients) {
            request.getRecipesByIngredients(ingredientList, output);
        }
    }

    @Override
    public void fetchIngredientData(IngredientRequest request, int ingredientId) {
        request.getIngredientMetaData(ingredientId, output);
    }
}
