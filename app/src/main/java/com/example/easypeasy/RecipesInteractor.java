package com.example.easypeasy;

import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.spoonacular.ConvertAmountsRequest;
import com.example.easypeasy.spoonacular.IngredientRequest;
import com.example.easypeasy.spoonacular.SearchIngredientsRequest;
import com.example.easypeasy.spoonacular.RecipesRequest;

import java.util.List;
import java.util.Map;

public class RecipesInteractor implements RecipesInteractorInput {

    public RecipesPresenterInput output;

    @Override
    public void fetchRecipesData(RecipesRequest request, List<Ingredient> ingredientList) {
        if (request.isSearchByIngredients) {
            request.getRecipesByIngredients(ingredientList, output, this);
        }
    }

    @Override
    public void fetchIngredientData(IngredientRequest request, long ingredientId) {
        request.getIngredientMetaData(ingredientId, output);
    }

    @Override
    public void fetchIngredientsSearchData(SearchIngredientsRequest request, String ingredientName) {
        request.getIngredientsSearchMetaData(output, this, ingredientName);
    }

    @Override
    public void convertAmountsAndUnitsRequest(ConvertAmountsRequest request, String ingredientName, Float responseIngredientAmount, String responseIngredientUnit, Map<String, String> inputIngredientData, List<Recipe> filteredRecipes, Recipe currentRecipe) {
        request.getConvertedAmountAndUnit(output, ingredientName, responseIngredientAmount, responseIngredientUnit, inputIngredientData, filteredRecipes, currentRecipe);
    }
}
