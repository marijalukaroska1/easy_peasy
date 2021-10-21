package com.example.easypeasy.screens.common;


import com.example.easypeasy.models.Recipe;

import java.util.List;

public interface SearchInput {
    void displayRecipesMetaData(List<Recipe> recipes);
    void displayIngredientUnits(List<String> possibleUnits);
}
