package com.example.easypeasy;

import com.example.easypeasy.spoonacular.RecipeInformationRequest;

public interface RecipeInformationInteractorInput {
    void fetchRecipeInformation(RecipeInformationRequest recipeInformationRequest);
}
