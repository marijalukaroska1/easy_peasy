package com.example.easypeasy.networking;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.RecipeData;
import com.example.easypeasy.models.schemas.IngredientSchema;
import com.example.easypeasy.models.schemas.RecipeDataSchema;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchRecipeInformationUseCase extends BaseObservableViewMvc<FetchRecipeInformationUseCase.Listener> implements Callback<RecipeDataSchema> {

    public interface Listener {
        void onFetchRecipeInformationSuccess(RecipeData response);

        void onFetchRecipeInformationFailure();
    }

    private static final String TAG = FetchRecipeInformationUseCase.class.getSimpleName();
    private final SpoonacularApi mSpoonacularApi;
    private final Map<String, String> map = new HashMap<>();

    public FetchRecipeInformationUseCase(SpoonacularApi spoonacularApi) {
        mSpoonacularApi = spoonacularApi;
    }

    public void getRecipeInformationMetaData(long recipeId) {
        Log.d(TAG, "getRecipeInformationMetaData is called");
        map.put("apiKey", Constants.API_KEY);

        Call<RecipeDataSchema> call = mSpoonacularApi.queryRecipeInformation(recipeId, map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<RecipeDataSchema> call, Response<RecipeDataSchema> response) {
        Log.d(TAG, "onResponse: " + response.body());
        if (response.isSuccessful() && response.body() != null) {
            RecipeDataSchema recipeDataSchema = response.body();
            RecipeData recipeData = new RecipeData();
            recipeData.setImageUrl(recipeDataSchema.getImageUrl());
            recipeData.setReadyInMinutes(recipeDataSchema.getReadyInMinutes());
            recipeData.setServings(recipeDataSchema.getServings());
            recipeData.setSummary(recipeDataSchema.getSummary());
            List<Ingredient> ingredientList = new ArrayList<>();
            for (IngredientSchema ingredientSchema : recipeDataSchema.getUsedIngredients()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setNameWithAmount(ingredientSchema.getNameWithAmount());
                ingredient.setPossibleUnits(ingredientSchema.getPossibleUnits());
                ingredient.setId(ingredientSchema.getId());
                ingredient.setAmount(ingredientSchema.getAmount());
                ingredient.setUnit(ingredientSchema.getUnit());
                ingredient.setName(ingredientSchema.getName());
                ingredientList.add(ingredient);
            }
            recipeData.setTitle(recipeDataSchema.getTitle());
            recipeData.setUsedIngredients(ingredientList);
            recipeData.setSourceUrl(recipeDataSchema.getSourceUrl());
            for (Listener listener : getListeners()) {
                listener.onFetchRecipeInformationSuccess(recipeData);
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<RecipeDataSchema> call, @NonNull Throwable t) {
        for (Listener listener : getListeners()) {
            listener.onFetchRecipeInformationFailure();
        }
    }
}
