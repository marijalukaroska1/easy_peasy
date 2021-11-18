package com.example.easypeasy.screens.recipeDetails;

import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.screens.common.ObservableListViewMvc;
import com.example.easypeasy.screens.navDrawer.NavDrawerViewMvc;

public interface RecipeDetailsViewMvc extends ObservableListViewMvc<RecipeDetailsViewMvc.Listener> {

    interface Listener {
        void onNavigationUpClicked();
    }

    void bindRecipe(RecipeDetailsSchema recipeDetailsSchema);

    void showProgressIndication();

    void hideProgressIndication();
}
