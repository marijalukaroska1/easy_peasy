package com.example.easypeasy;

import com.example.easypeasy.spoonacular.RecipeInformationRequest;

public class RecipeInformationInteractor implements RecipeInformationInteractorInput {
    public RecipeInformationPresenterInput output;

    @Override
    public void fetchRecipeInformation(RecipeInformationRequest recipeInformationRequest) {
        recipeInformationRequest.getRecipeInformationMetaData(output);
    }
}
