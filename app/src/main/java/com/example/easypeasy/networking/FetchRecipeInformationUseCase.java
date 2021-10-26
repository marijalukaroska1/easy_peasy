package com.example.easypeasy.networking;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.RecipeDetails;
import com.example.easypeasy.models.schemas.IngredientResponseSchema;
import com.example.easypeasy.models.schemas.RecipeDetailsResponseSchema;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchRecipeInformationUseCase extends BaseObservableViewMvc<FetchRecipeInformationUseCase.Listener> implements Callback<RecipeDetailsResponseSchema> {

    public interface Listener {
        void onFetchRecipeDetailsSuccess(RecipeDetails response);

        void onFetchRecipeDetailsFailure();
    }

    private static final String TAG = FetchRecipeInformationUseCase.class.getSimpleName();
    private final SpoonacularApi mSpoonacularApi;
    private final Map<String, String> map = new HashMap<>();

    public FetchRecipeInformationUseCase(SpoonacularApi spoonacularApi) {
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
            RecipeDetails recipeDetails = new RecipeDetails();
            recipeDetails.setImageUrl(recipeDetailsResponseSchema.getImageUrl());
            recipeDetails.setReadyInMinutes(recipeDetailsResponseSchema.getReadyInMinutes());
            recipeDetails.setServings(recipeDetailsResponseSchema.getServings());
            recipeDetails.setSummary(recipeDetailsResponseSchema.getSummary());
            List<Ingredient> ingredientList = new ArrayList<>();
            for (IngredientResponseSchema ingredientResponseSchema : recipeDetailsResponseSchema.getUsedIngredients()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setNameWithAmount(ingredientResponseSchema.getNameWithAmount());
                ingredient.setPossibleUnits(ingredientResponseSchema.getPossibleUnits());
                ingredient.setId(ingredientResponseSchema.getId());
                ingredient.setAmount(ingredientResponseSchema.getAmount());
                ingredient.setUnit(ingredientResponseSchema.getUnit());
                ingredient.setName(ingredientResponseSchema.getName());
                ingredientList.add(ingredient);
            }
            recipeDetails.setTitle(recipeDetailsResponseSchema.getTitle());
            recipeDetails.setUsedIngredients(ingredientList);
            recipeDetails.setSourceUrl(recipeDetailsResponseSchema.getSourceUrl());
            notifySuccess(recipeDetails);
        } else {
            notifyFailure();
        }
    }

    @Override
    public void onFailure(@NonNull Call<RecipeDetailsResponseSchema> call, @NonNull Throwable t) {
        notifyFailure();
    }

    private void notifySuccess(RecipeDetails recipeData) {
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
