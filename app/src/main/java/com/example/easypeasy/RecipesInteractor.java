package com.example.easypeasy;

import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Nutrient;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.spoonacular.ConvertAmountsRequest;
import com.example.easypeasy.spoonacular.IngredientRequest;
import com.example.easypeasy.spoonacular.RecipesRequest;
import com.example.easypeasy.spoonacular.SearchIngredientsRequest;

import java.util.List;
import java.util.Map;

public class RecipesInteractor implements RecipesInteractorInput {

    public RecipesPresenterInput output;

    @Override
    public void fetchRecipesData(RecipesRequest request, List<Ingredient> ingredientList, List<Nutrient> nutrientList) {
        if (request.isSearchByIngredients) {
            request.getRecipesByIngredients(ingredientList, output);
        } else {
            request.getRecipesByNutrients(nutrientList, output);
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
}
