package com.example.easypeasy.screens.searchByNutrientsList;

import com.example.easypeasy.models.Nutrient;
import com.example.easypeasy.models.RecipeData;
import com.example.easypeasy.screens.common.ObservableListViewMvc;

import java.util.List;

public interface SearchByNutrientsViewMvc extends ObservableListViewMvc<SearchByNutrientsViewMvc.Listener> {

    List<Nutrient> getNutrients();

    interface Listener {
        void searchRecipes();
    }

    void bindNutrient(Nutrient nutrient);
    void removeNutrient(Nutrient nutrient);
    void bindRecipes(List<RecipeData> recipeData);
}
