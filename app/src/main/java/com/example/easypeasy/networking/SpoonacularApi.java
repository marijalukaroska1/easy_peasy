package com.example.easypeasy.networking;

import com.example.easypeasy.networking.ingredients.ConvertIngredientAmountResponseSchema;
import com.example.easypeasy.networking.ingredients.IngredientResponseSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsResponseSchema;
import com.example.easypeasy.networking.recipes.RecipeResponseSchema;
import com.example.easypeasy.networking.ingredients.SearchIngredientDetailsResponseSchema;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface SpoonacularApi {

    @GET("recipes/findByIngredients")
    Call<List<RecipeResponseSchema>> queryRecipesByIngredients(@QueryMap Map<String, String> options);

    @GET("recipes/findByNutrients")
    Call<List<RecipeResponseSchema>> queryRecipesByNutrients(@QueryMap Map<String, String> options);

    @GET("food/ingredients/{id}/information")
    Call<IngredientResponseSchema> queryIngredientData(@Path("id") long id, @QueryMap Map<String, String> options);

    @GET("food/ingredients/search")
    Call<SearchIngredientDetailsResponseSchema> searchIngredients(@QueryMap Map<String, String> options);

    @GET("recipes/convert")
    Observable<ConvertIngredientAmountResponseSchema> convertAmountAndUnit(@QueryMap Map<String, String> options);

    @GET("recipes/{id}/information")
    Call<RecipeDetailsResponseSchema> queryRecipeInformation(@Path("id") long id, @QueryMap Map<String, String> options);

}
