package com.example.easypeasy.spoonacular;

import android.util.Log;

import com.example.easypeasy.Constants;
import com.example.easypeasy.RecipesPresenterInput;
import com.example.easypeasy.models.Ingredient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientRequest extends BaseRequest implements Callback<Ingredient> {

    private static final String TAG = "IngredientRequest";

    public SpoonacularRecipesApi spoonacularApi;
    RecipesPresenterInput output;
    Map<String, String> map = new HashMap<>();


    public IngredientRequest() {
        spoonacularApi = buildRecipesUrl();
    }

    public void getIngredientMetaData(int id, RecipesPresenterInput output) {
        Log.d(TAG, "getRecipesByIngredients is called");
        this.output = output;

        map.put("id", String.valueOf(id));
        map.put("apiKey", Constants.API_KEY);

        Call<Ingredient> call = spoonacularApi.queryIngredientData(map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Ingredient> call, Response<Ingredient> response) {
        Log.d(TAG, "onResponse: " + response);
        if (response.isSuccessful()) {
            Log.d(TAG, "onResponse successful: " + response.message());
            Ingredient ingredient = response.body();
            output.presentIngredientData(ingredient);
        } else {
            Log.d(TAG, "onResponse error: " + response.code());
        }
    }

    @Override
    public void onFailure(Call<Ingredient> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
    }
}