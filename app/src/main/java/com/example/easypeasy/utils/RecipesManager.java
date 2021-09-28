package com.example.easypeasy.utils;

import android.content.Context;
import android.util.Log;

import com.example.easypeasy.RecipesPresenterInput;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.spoonacular.ConvertAmountsRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipesManager {

    private static final String TAG = RecipesManager.class.getSimpleName();
    Context context;
    RecipesPresenterInput output;
    boolean isSearchByIngredients;

    public RecipesManager(Context context, RecipesPresenterInput output, boolean isSearchByIngredients) {
        this.context = context;
        this.output = output;
        this.isSearchByIngredients = isSearchByIngredients;
    }


    public void filterRecipes(List<Recipe> recipeList, List<Ingredient> ingredientList) {
        if (isSearchByIngredients) {
            List<Recipe> filteredRecipes = new ArrayList(recipeList);
            ConvertAmountsRequest convertAmountsRequest = new ConvertAmountsRequest(context);
            Map<String, Map<String, String>> inputIngredientsMap = Utils.mapIngredientNameWithAmountAndUnit(ingredientList);
            for (Recipe recipe : recipeList) {
                Log.d(TAG, "recipe name: " + recipe.getTitle() + " number of ingredients: " + recipe.getUsedIngredients().size());
                Log.d(TAG, "=================================================");
                for (Ingredient responseIngredient : recipe.getUsedIngredients()) {
                    if (inputIngredientsMap.containsKey(responseIngredient.getName())) {
                        if (!responseIngredient.getUnit().isEmpty() && !responseIngredient.getUnit().equalsIgnoreCase(inputIngredientsMap.get(responseIngredient.getName()).get("unit"))) {
                            Log.d(TAG, "different unit for both ingredients: " + inputIngredientsMap.get(responseIngredient.getName()).get("unit") + " " + inputIngredientsMap.get(responseIngredient.getName()).get("amount") + " " + responseIngredient.getUnit() + " " + responseIngredient.getAmount());
                            convertAmountsRequest.addConvertedUnitsAndAmountRequest(responseIngredient.getName(), responseIngredient.getUnit(), inputIngredientsMap.get(responseIngredient.getName()), recipe);
                        } else {
                            Log.d(TAG, "same unit for both ingredients: " + inputIngredientsMap.get(responseIngredient.getName()).get("unit") + " " + responseIngredient.getUnit());
                            if (responseIngredient.getAmount() > Float.parseFloat(inputIngredientsMap.get(responseIngredient.getName()).get("amount"))) {
                                filteredRecipes.remove(recipe);
                            }
                        }
                    }
                }
            }
            convertAmountsRequest.makeRequests(output, filteredRecipes);
        } else {
            output.presentRecipesData(recipeList, context);
        }
    }
}
