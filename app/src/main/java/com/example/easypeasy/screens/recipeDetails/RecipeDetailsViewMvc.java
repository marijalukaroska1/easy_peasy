package com.example.easypeasy.screens.recipeDetails;

import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.screens.common.ObservableListViewMvc;
import com.example.easypeasy.screens.navDrawer.NavDrawerViewMvc;

public interface RecipeDetailsViewMvc extends ObservableListViewMvc<RecipeDetailsViewMvc.Listener>, NavDrawerViewMvc {

    interface Listener {
        void onNavigationUpClicked();

        void selectSearchByIngredientsItemClicked();

        void selectSearchByNutrientsItemClicked();
    }

    void bindRecipe(RecipeDetailsSchema recipeDetailsSchema);

    void showProgressIndication();

    void hideProgressIndication();
}
