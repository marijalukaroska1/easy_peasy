package com.example.easypeasy.spoonacular;

import com.example.easypeasy.models.Recipe;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface SpoonacularApi {

    @GET("findByIngredients")
    Call<List<Recipe>> queryRecipesByIngredients(@QueryMap Map<String, String> options);

    @GET("recipes/findByNutrients")
    Call<List<Recipe>> queryRecipesByNutrients(@QueryMap Map<String, String> options);
}
