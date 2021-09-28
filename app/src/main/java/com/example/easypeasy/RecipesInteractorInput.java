package com.example.easypeasy;

import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Nutrient;
import com.example.easypeasy.spoonacular.IngredientRequest;
import com.example.easypeasy.spoonacular.RecipesRequest;
import com.example.easypeasy.spoonacular.SearchIngredientsRequest;

import java.util.List;

public interface RecipesInteractorInput {
    void fetchRecipesData(RecipesRequest request, List<Ingredient> ingredientList, List<Nutrient> nutrientList);

    void fetchIngredientData(IngredientRequest request, long ingredientId);

    void fetchIngredientsSearchData(SearchIngredientsRequest request, String ingredientName);
}
