package com.example.easypeasy.screens.searchByIngredientsList;

import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.screens.common.ObservableListViewMvc;

import java.util.List;

public interface SearchByIngredientsViewMvc extends ObservableListViewMvc<SearchByIngredientsViewMvc.Listener> {

    interface Listener {
        void searchRecipes();
    }

    List<Ingredient> getIngredientList();

    void bindRecipes(List<Recipe> recipes);

    void bindIngredient(Ingredient ingredient);

    void bindPossibleUnits(String[] possibleUnits, int ingredientFetchDataPosition);
}
