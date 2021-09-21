package com.example.easypeasy.activities;


import com.example.easypeasy.adapters.RecipesAdapter;

import java.util.List;

public interface SearchInput {
    void displayRecipesMetaData(RecipesAdapter object);
    void displayIngredientUnits(List<String> possibleUnits);
}
