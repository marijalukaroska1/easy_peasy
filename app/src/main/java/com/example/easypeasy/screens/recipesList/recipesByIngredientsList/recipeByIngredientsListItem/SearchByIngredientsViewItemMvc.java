package com.example.easypeasy.screens.recipesList.recipesByIngredientsList.recipeByIngredientsListItem;

import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.screens.common.views.ObservableViewMvc;

import java.util.List;

public interface SearchByIngredientsViewItemMvc extends ObservableViewMvc<SearchByIngredientsViewItemMvc.Listener> {

    List<IngredientSchema> getIngredients();

    interface Listener {
        void insertNewIngredientButtonClicked(IngredientSchema ingredient);
    }

    void bindIngredientToView(int position);

    void bindPossibleUnits(String[] unitsList);

    void setInsertIngredientFieldVisibility(int visibility);

    void bindIngredients(List<IngredientSchema> ingredients);

}
