package com.example.easypeasy.screens.recipesList.recipesByIngredientsList;

import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.screens.common.ObservableListViewMvc;
import com.example.easypeasy.screens.recipesList.common.RecipeClickListener;

import java.util.List;

public interface SearchByIngredientsViewMvc extends ObservableListViewMvc<SearchByIngredientsViewMvc.Listener> {

    interface Listener {
        void searchRecipesButtonClicked();

        void onNavigationUpClicked();
    }

    List<IngredientSchema> getIngredientList();

    void bindRecipes(List<RecipeDetailsSchema> recipeData, RecipeClickListener recipeClickListener);

    void bindIngredient(IngredientSchema ingredient);

    void bindIngredientPossibleUnits(String[] possibleUnits, int ingredientFetchDataPosition);

    void showProgressIndication();

    void hideProgressIndication();
}
