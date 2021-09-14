package com.example.easypeasy;

import com.example.easypeasy.spoonacular.IngredientRequest;
import com.example.easypeasy.spoonacular.RecipesRequest;

public interface RecipesInteractorInput {
    void fetchRecipesData(RecipesRequest request, String userInput);
    void fetchIngredientData(IngredientRequest request, int ingredientId);
}
