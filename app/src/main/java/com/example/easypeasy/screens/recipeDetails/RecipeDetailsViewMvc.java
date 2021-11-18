package com.example.easypeasy.screens.recipeDetails;

import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.screens.common.views.ObservableViewMvc;

public interface RecipeDetailsViewMvc extends ObservableViewMvc<RecipeDetailsViewMvc.Listener> {

    interface Listener {
        void onNavigationUpClicked();
    }

    void bindRecipe(RecipeDetailsSchema recipeDetailsSchema);

    void showProgressIndication();

    void hideProgressIndication();
}
