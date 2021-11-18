package com.example.easypeasy.screens.recipesList.common;

import android.content.Context;

import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.screens.common.views.ObservableViewMvc;

public interface RecipeListViewItemMvc extends ObservableViewMvc<RecipeListViewItemMvc.Listener> {

    interface Listener {
        void onRecipeClicked(Context context, RecipeDetailsSchema recipe);
    }

    void bindRecipe(RecipeDetailsSchema recipe);

    void showNoRecipesFoundMsg();
}
