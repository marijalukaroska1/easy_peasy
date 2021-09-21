package com.example.easypeasy;

import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.spoonacular.IngredientRequest;
import com.example.easypeasy.spoonacular.IngredientsSearchRequest;
import com.example.easypeasy.spoonacular.RecipesRequest;

import java.util.List;

public interface RecipesInteractorInput {
    void fetchRecipesData(RecipesRequest request, List<Ingredient> ingredientList);
    void fetchIngredientData(IngredientRequest request, long ingredientId);
    void fetchIngredientsSearchData(IngredientsSearchRequest request, String ingredientName);
}
