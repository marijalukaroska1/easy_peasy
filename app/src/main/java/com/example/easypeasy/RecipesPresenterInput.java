package com.example.easypeasy;

import android.content.Context;

import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Recipe;

import java.util.List;

public interface RecipesPresenterInput {
    void presentRecipesData(List<Recipe> recipeList, Context context);
    void presentIngredientData(Ingredient ingredient);
    void presentIngredients(List<Ingredient> ingredientList);
    void convertAmountResponse();
}
