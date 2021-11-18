package com.example.easypeasy.recipes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.networking.ingredients.IngredientResponseSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsResponseSchema;
import com.example.easypeasy.networking.SpoonacularApi;
import com.example.easypeasy.screens.common.views.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchRecipeDetailsUseCase extends BaseObservableViewMvc<FetchRecipeDetailsUseCase.Listener> implements Callback<RecipeDetailsResponseSchema> {

    public interface Listener {
        void onFetchRecipeDetailsSuccess(RecipeDetailsSchema response);

        void onFetchRecipeDetailsFailure();
    }

    private static final String TAG = FetchRecipeDetailsUseCase.class.getSimpleName();
    private final SpoonacularApi mSpoonacularApi;
    private final Map<String, String> map = new HashMap<>();

    public FetchRecipeDetailsUseCase(SpoonacularApi spoonacularApi) {
        mSpoonacularApi = spoonacularApi;
    }

    public void fetchRecipeDetailsAndNotify(long recipeId) {
        Log.d(TAG, "fetchRecipeDetailsAndNotify is called");
        map.put("apiKey", Constants.API_KEY);

        Call<RecipeDetailsResponseSchema> call = mSpoonacularApi.queryRecipeInformation(recipeId, map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<RecipeDetailsResponseSchema> call, Response<RecipeDetailsResponseSchema> response) {
        Log.d(TAG, "onResponse: " + response.body());
        if (response.isSuccessful() && response.body() != null) {
            RecipeDetailsResponseSchema recipeDetailsResponseSchema = response.body();
            RecipeDetailsSchema recipeDetailsSchema = new RecipeDetailsSchema();
            recipeDetailsSchema.setImageUrl(recipeDetailsResponseSchema.getImageUrl());
            recipeDetailsSchema.setReadyInMinutes(recipeDetailsResponseSchema.getReadyInMinutes());
            recipeDetailsSchema.setServings(recipeDetailsResponseSchema.getServings());
            recipeDetailsSchema.setSummary(recipeDetailsResponseSchema.getSummary());
            List<IngredientSchema> ingredientList = new ArrayList<>();
            for (IngredientResponseSchema ingredientResponseSchema : recipeDetailsResponseSchema.getUsedIngredients()) {
                IngredientSchema ingredient = new IngredientSchema();
                ingredient.setNameWithAmount(ingredientResponseSchema.getNameWithAmount());
                ingredient.setPossibleUnits(ingredientResponseSchema.getPossibleUnits());
                ingredient.setId(ingredientResponseSchema.getId());
                ingredient.setAmount(ingredientResponseSchema.getAmount());
                ingredient.setUnit(ingredientResponseSchema.getUnit());
                ingredient.setName(ingredientResponseSchema.getName());
                ingredientList.add(ingredient);
            }
            recipeDetailsSchema.setTitle(recipeDetailsResponseSchema.getTitle());
            recipeDetailsSchema.setUsedIngredients(ingredientList);
            recipeDetailsSchema.setSourceUrl(recipeDetailsResponseSchema.getSourceUrl());
            notifySuccess(recipeDetailsSchema);
        } else {
            notifyFailure();
        }
    }

    @Override
    public void onFailure(@NonNull Call<RecipeDetailsResponseSchema> call, @NonNull Throwable t) {
        notifyFailure();
    }

    private void notifySuccess(RecipeDetailsSchema recipeData) {
        for (Listener listener : getListeners()) {
            listener.onFetchRecipeDetailsSuccess(recipeData);
        }
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onFetchRecipeDetailsFailure();
        }
    }
}
