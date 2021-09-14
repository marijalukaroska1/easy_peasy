package com.example.easypeasy.spoonacular;

import android.util.Log;

import com.example.easypeasy.Constants;
import com.example.easypeasy.RecipesPresenterInput;
import com.example.easypeasy.models.Recipe;

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

    RecipesPresenterInput output;
    Map<String, String> map = new HashMap<>();

    public RecipesRequest() {
        spoonacularApi = buildRecipesUrl();
    }

    public void getRecipesByIngredients(String ingredients, RecipesPresenterInput output) {
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
            output.presentRecipesData(recipes);
        } else {
            Log.d(TAG, "onResponse error: " + response.code());
        }
    }


    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
    }
}
