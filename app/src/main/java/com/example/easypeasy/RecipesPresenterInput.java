package com.example.easypeasy;

import com.example.easypeasy.models.Recipe;

import java.util.List;

public interface RecipesPresenterInput {
    void presentRecipesData(List<Recipe> recipeList);
}
