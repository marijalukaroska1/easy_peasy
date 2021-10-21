package com.example.easypeasy.spoonacular;

import android.content.Context;
import android.util.Log;

import com.example.easypeasy.RecipesPresenterInput;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Nutrient;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.utils.Constants;
import com.example.easypeasy.utils.RecipesManager;
import com.example.easypeasy.utils.Utils;

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
    List<Ingredient> inputIngredientList;

    RecipesPresenterInput output;
    Map<String, String> map = new HashMap<>();
    Context context;

    public RecipesRequest(Context context) {
        spoonacularApi = buildRecipesUrl();
        this.context = context;
    }

    public void getRecipesByIngredients(List<Ingredient> inputIngredientList, RecipesPresenterInput output) {
        this.output = output;
        this.inputIngredientList = inputIngredientList;
        String ingredients = Utils.getIngredientsUserInput(inputIngredientList);
        Log.d(TAG, "getRecipesByIngredients is called: " + ingredients);

        map.put("apiKey", Constants.API_KEY);
        map.put("ingredients", ingredients);

        Call<List<Recipe>> call = spoonacularApi.queryRecipesByIngredients(map);
        call.enqueue(this);
    }

    public void getRecipesByNutrients(List<Nutrient> nutrientList, RecipesPresenterInput output) {
        Log.d(TAG, "getNutrientsSearchMetaData is called");
        this.output = output;

        map.put("apiKey", Constants.API_KEY);

        for (Nutrient nutrient : nutrientList) {
            map.put(nutrient.getName(), String.valueOf(nutrient.getAmount()));
        }

        Call<List<Recipe>> call = spoonacularApi.queryRecipesByNutrients(map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        Log.d(TAG, "onResponse: " + response.isSuccessful());
        if (response.isSuccessful()) {
            Log.d(TAG, "onResponse successful: " + response.body());
            List<Recipe> recipes = response.body();
            if (recipes != null && recipes.size() > 0) {
                recipes.forEach(recipe -> Log.d(TAG, "onResponse successful: " + recipe));
                    RecipesManager recipesManager = new RecipesManager(context, output, isSearchByIngredients);
                    recipesManager.filterRecipes(recipes, inputIngredientList);
                    output.presentRecipesData(recipes, context);
            }
        } else {
            Log.d(TAG, "onResponse error: " + response.code());
        }
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
    }
}
