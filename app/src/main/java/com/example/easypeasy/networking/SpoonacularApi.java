package com.example.easypeasy.networking;

import com.example.easypeasy.models.schemas.ConvertAmountResponseSchema;
import com.example.easypeasy.models.schemas.IngredientResponseSchema;
import com.example.easypeasy.models.schemas.RecipeDetailsResponseSchema;
import com.example.easypeasy.models.schemas.RecipeResponseSchema;
import com.example.easypeasy.models.schemas.SearchIngredientDetailsResponseSchema;

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
    Observable<ConvertAmountResponseSchema> convertAmountAndUnit(@QueryMap Map<String, String> options);

    @GET("recipes/{id}/information")
    Call<RecipeDetailsResponseSchema> queryRecipeInformation(@Path("id") long id, @QueryMap Map<String, String> options);

}
