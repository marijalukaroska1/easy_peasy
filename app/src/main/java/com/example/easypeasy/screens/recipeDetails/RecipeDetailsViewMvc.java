package com.example.easypeasy.screens.recipeDetails;

import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.screens.common.ViewMvc;

public interface RecipeDetailsViewMvc extends ViewMvc {
    void bindRecipe(RecipeDetailsSchema recipeDetailsSchema);

    void showProgressIndication();

    void hideProgressIndication();
}
