package com.example.easypeasy.screens.recipeInformation;

import com.example.easypeasy.models.RecipeDetails;
import com.example.easypeasy.screens.common.ViewMvc;

public interface RecipeInformationViewMvc extends ViewMvc {
    void bindRecipe(RecipeDetails recipeDetails);

    void showProgressIndication();

    void hideProgressIndication();
}
