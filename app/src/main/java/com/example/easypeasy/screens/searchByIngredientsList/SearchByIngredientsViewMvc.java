package com.example.easypeasy.screens.searchByIngredientsList;

import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.RecipeData;
import com.example.easypeasy.screens.common.ObservableListViewMvc;

import java.util.List;

public interface SearchByIngredientsViewMvc extends ObservableListViewMvc<SearchByIngredientsViewMvc.Listener> {

    interface Listener {
        void searchRecipesButtonClicked();
    }

    List<Ingredient> getIngredientList();

    void bindRecipes(List<RecipeData> recipeData);

    void bindIngredient(Ingredient ingredient);

    void bindIngredientPossibleUnits(String[] possibleUnits, int ingredientFetchDataPosition);
}
