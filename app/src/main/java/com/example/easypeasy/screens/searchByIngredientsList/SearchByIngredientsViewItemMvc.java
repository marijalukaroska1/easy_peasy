package com.example.easypeasy.screens.searchByIngredientsList;

import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.screens.common.ObservableListViewMvc;

import java.util.List;

public interface SearchByIngredientsViewItemMvc extends ObservableListViewMvc<SearchByIngredientsViewItemMvc.Listener> {

    List<Ingredient> getIngredients();

    interface Listener {
        void insertNewIngredientButtonClicked(Ingredient ingredient);
    }

    void bindIngredientToView(int position);

    void bindPossibleUnits(String[] unitsList);

    void setInsertIngredientFieldVisibility(int visibility);

}
