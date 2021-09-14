package com.example.easypeasy;

import com.example.easypeasy.spoonacular.IngredientRequest;
import com.example.easypeasy.spoonacular.RecipesRequest;
import com.example.easypeasy.models.Recipe;

public class RecipesInteractor implements RecipesInteractorInput {

    public RecipesPresenterInput output;

    @Override
    public void fetchRecipesData(RecipesRequest request, String userInput) {
        if (request.isSearchByIngredients) {
            request.getRecipesByIngredients(userInput, output);
        }
    }

    @Override
    public void fetchIngredientData(IngredientRequest request, int ingredientId) {
        request.getIngredientMetaData(ingredientId, output);
    }
}
