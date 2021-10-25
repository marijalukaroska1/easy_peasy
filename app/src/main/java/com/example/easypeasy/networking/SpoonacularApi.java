package com.example.easypeasy.networking;

import com.example.easypeasy.models.schemas.ConvertAmountSchema;
import com.example.easypeasy.models.schemas.IngredientSchema;
import com.example.easypeasy.models.schemas.RecipeDataSchema;
import com.example.easypeasy.models.schemas.RecipeSchema;
import com.example.easypeasy.models.schemas.SearchIngredientNameSchema;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface SpoonacularApi {

    @GET("recipes/findByIngredients")
    Call<List<RecipeSchema>> queryRecipesByIngredients(@QueryMap Map<String, String> options);

    @GET("recipes/findByNutrients")
    Call<List<RecipeSchema>> queryRecipesByNutrients(@QueryMap Map<String, String> options);

    @GET("food/ingredients/{id}/information")
    Call<IngredientSchema> queryIngredientData(@Path("id") long id, @QueryMap Map<String, String> options);

    @GET("food/ingredients/search")
    Call<SearchIngredientNameSchema> searchIngredients(@QueryMap Map<String, String> options);

    @GET("recipes/convert")
    Observable<ConvertAmountSchema> convertAmountAndUnit(@QueryMap Map<String, String> options);

    @GET("recipes/{id}/information")
    Call<RecipeDataSchema> queryRecipeInformation(@Path("id") long id, @QueryMap Map<String, String> options);

}
