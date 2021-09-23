package com.example.easypeasy.activities;

import com.example.easypeasy.adapters.RecipeInformationUsedIngredientsAdapter;
import com.example.easypeasy.models.RecipeInformationResponse;

public interface RecipeInformationInput {
    void displayRecipeInformation(RecipeInformationResponse recipeInformationResponse, RecipeInformationUsedIngredientsAdapter adapter);
}
