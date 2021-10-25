package com.example.easypeasy.screens.common;


import com.example.easypeasy.models.RecipeData;

import java.util.List;

public interface SearchInput {
    void displayRecipesMetaData(List<RecipeData> recipeData);
    void displayIngredientUnits(List<String> possibleUnits);
}
