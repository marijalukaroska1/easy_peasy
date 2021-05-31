package com.example.easypeasy.spoonacular;

import android.util.Log;

import com.example.easypeasy.Constants;
import com.example.easypeasy.RecipesPresenterInput;
import com.example.easypeasy.models.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesRequest implements Callback<List<Recipe>> {

    private static final String TAG = RecipesRequest.class.getSimpleName();
    public boolean isSearchByIngredients = false;
    public SpoonacularApi spoonacularApi;

    static final String BASE_URL = "https://api.spoonacular.com/recipes/";
    RecipesPresenterInput output;
    Map<String, String> map = new HashMap<>();

    public RecipesRequest() {
        spoonacularApi = buildRecipesUrl();
    }

    public void getRecipesByIngredients(String ingredients, RecipesPresenterInput output) {
        Log.d(TAG, "getRecipesByIngredients is called");
        this.output = output;

        map.put("ingredients", ingredients);
        map.put("apiKey", "d26efdad5be347db9528c724054c4324");

        Call<List<Recipe>> call = spoonacularApi.queryRecipesByIngredients(map);
        call.enqueue(this);
    }

    public void getRecipesByNutrients(String nutrients) {

        map.put("nutrients", nutrients);
        map.put("apiKey", Constants.API_KEY);

        Call<List<Recipe>> call = spoonacularApi.queryRecipesByNutrients(map);
        call.enqueue(this);
    }


    private SpoonacularApi buildRecipesUrl() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(SpoonacularApi.class);
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        Log.d(TAG, "onResponse: " + response);
        if (response.isSuccessful()) {
            Log.d(TAG, "onResponse successful: " + response.message());
            List<Recipe> recipes = response.body();
            recipes.forEach(recipe ->    Log.d(TAG, "onResponse successful: " + recipe));
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
