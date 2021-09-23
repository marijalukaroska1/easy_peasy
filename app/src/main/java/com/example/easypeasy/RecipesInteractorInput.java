package com.example.easypeasy;

import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.spoonacular.ConvertAmountsRequest;
import com.example.easypeasy.spoonacular.IngredientRequest;
import com.example.easypeasy.spoonacular.RecipesRequest;
import com.example.easypeasy.spoonacular.SearchIngredientsRequest;

import java.util.List;
import java.util.Map;

public interface RecipesInteractorInput {
    void fetchRecipesData(RecipesRequest request, List<Ingredient> ingredientList);
    void fetchIngredientData(IngredientRequest request, long ingredientId);
    void fetchIngredientsSearchData(SearchIngredientsRequest request, String ingredientName);
    void convertAmountsAndUnitsRequest(ConvertAmountsRequest request, String ingredientName, Float inputIngredientAmount, String inputIngredientUnit, Map<String, String> responseIngredientData, List<Recipe> filteredRecipes, Recipe currentRecipe);
}
