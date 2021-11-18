package com.example.easypeasy.screens.recipesList.recipesByNutrientsList;

import com.example.easypeasy.networking.nutrients.NutrientSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.screens.common.ObservableListViewMvc;
import com.example.easypeasy.screens.navDrawer.NavDrawerViewMvc;
import com.example.easypeasy.screens.recipesList.common.RecipeClickListener;

import java.util.List;

public interface SearchByNutrientsViewMvc extends ObservableListViewMvc<SearchByNutrientsViewMvc.Listener> {

    List<NutrientSchema> getNutrients();

    interface Listener {
        void searchRecipes();

        void onNavigationUpClicked();
    }

    void bindNutrient(NutrientSchema nutrient);

    void removeNutrient(NutrientSchema nutrient);

    void bindRecipes(List<RecipeDetailsSchema> recipeData, RecipeClickListener recipeClickListener);
}
