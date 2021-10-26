package com.example.easypeasy.screens.common;


import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;

import java.util.List;

public interface SearchInput {
    void displayRecipesMetaData(List<RecipeDetailsSchema> recipeData);
    void displayIngredientUnits(List<String> possibleUnits);
}
