package com.example.easypeasy.spoonacular;

import android.content.Context;
import android.util.Log;

import com.example.easypeasy.Constants;
import com.example.easypeasy.RecipesPresenterInput;
import com.example.easypeasy.Utils;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesRequest extends BaseRequest implements Callback<List<Recipe>> {

    private static final String TAG = RecipesRequest.class.getSimpleName();
    public boolean isSearchByIngredients = false;
    public SpoonacularRecipesApi spoonacularApi;
    List<Ingredient> ingredientList;

    RecipesPresenterInput output;
    Map<String, String> map = new HashMap<>();
    Context context;

    public RecipesRequest(Context context) {
        spoonacularApi = buildRecipesUrl();
        this.context = context;
    }

    public void getRecipesByIngredients(List<Ingredient> ingredientsList, RecipesPresenterInput output) {
        ingredientList = ingredientsList;
        String ingredients = Utils.getIngredientsUserInput(ingredientsList);
        Log.d(TAG, "getRecipesByIngredients is called: " + ingredients);
        this.output = output;

        map.put("apiKey", Constants.API_KEY);
        map.put("ingredients", ingredients);


        Call<List<Recipe>> call = spoonacularApi.queryRecipesByIngredients(map);
        call.enqueue(this);
    }

    public void getRecipesByNutrients(String nutrients) {

        map.put("nutrients", nutrients);
        map.put("apiKey", Constants.API_KEY);

        Call<List<Recipe>> call = spoonacularApi.queryRecipesByNutrients(map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        Log.d(TAG, "onResponse: " + response);
        if (response.isSuccessful()) {
            Log.d(TAG, "onResponse successful: " + response.message());
            List<Recipe> recipes = response.body();
            recipes.forEach(recipe -> Log.d(TAG, "onResponse successful: " + recipe));
            List<Recipe> filteredRecipes = filterRecipesByIngredientsAmount(recipes);
            output.presentRecipesData(filteredRecipes, context);
        } else {
            Log.d(TAG, "onResponse error: " + response.code());
        }
    }

    private List<Recipe> filterRecipesByIngredientsAmount(List<Recipe> recipes) {
        Map<String, Map<String, Float>> inputIngredientsMap = Utils.mapIngredientsNamesAndAmounts(ingredientList);
        List<Recipe> filteredRecipes = new ArrayList<>(recipes);
        for (Recipe recipe : recipes) {
            Log.d(TAG, "recipe name: " + recipe.getTitle());
            Log.d(TAG, "=================================================");
            for (Ingredient ingredient : recipe.getUsedIngredients()) {
                if (inputIngredientsMap.containsKey(ingredient.getName())) {
                    Log.d(TAG, "ingredient name: " + ingredient.getName());
                    Log.d(TAG, "input ingredient amount: " + inputIngredientsMap.get(ingredient.getName()));
                    Log.d(TAG, "response ingredient amount: " + ingredient.getAmount() + " unit: " + ingredient.getUnit());
//                    if (ingredient.getAmount() > inputIngredientsMap.get(ingredient.getName()).get(ingredient.getUnit())) {
//                        filteredRecipes.remove(recipe);
//                    }
                }
            }
            Log.d(TAG, "=================================================");
        }
        return filteredRecipes;
    }


    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
    }
}
