package com.example.easypeasy.screens.common;


import com.example.easypeasy.models.RecipeDetails;

import java.util.List;

public interface SearchInput {
    void displayRecipesMetaData(List<RecipeDetails> recipeData);
    void displayIngredientUnits(List<String> possibleUnits);
}
