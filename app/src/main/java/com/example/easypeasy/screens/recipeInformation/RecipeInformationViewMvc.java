package com.example.easypeasy.screens.recipeInformation;

import com.example.easypeasy.models.RecipeData;
import com.example.easypeasy.screens.common.ViewMvc;

public interface RecipeInformationViewMvc extends ViewMvc {
    void bindRecipe(RecipeData recipeData);
}
