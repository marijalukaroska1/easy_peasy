package com.example.easypeasy.networking;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.common.utils.RecipesManager;
import com.example.easypeasy.common.utils.Utils;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Nutrient;
import com.example.easypeasy.models.RecipeDetails;
import com.example.easypeasy.models.schemas.RecipeResponseSchema;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchRecipesUseCase extends BaseObservableViewMvc<FetchRecipesUseCase.Listener> implements Callback<List<RecipeResponseSchema>>, RecipesManager.Listener {

    public interface Listener {
        void onFetchRecipesSuccess(List<RecipeDetails> recipeData);

        void onFetchRecipesFailure();
    }

    private static final String TAG = FetchRecipesUseCase.class.getSimpleName();
    public boolean isSearchByIngredients = false;
    private final SpoonacularApi mSpoonacularApi;
    private List<Ingredient> inputIngredientList;
    private final Map<String, String> map = new HashMap<>();
    private final RecipesManager mRecipesManager;

    public FetchRecipesUseCase(SpoonacularApi spoonacularApi) {
        mSpoonacularApi = spoonacularApi;
        mRecipesManager = new RecipesManager(spoonacularApi);
        mRecipesManager.registerListener(this);
    }

    public void fetchRecipesByIngredientsAndNotify(List<Ingredient> inputIngredientList) {
        mRecipesManager.setmIsSearchByIngredients(true);
        Log.d(TAG, "fetchRecipesByIngredientsAndNotify is called: " + inputIngredientList);
        this.inputIngredientList = inputIngredientList;
        String ingredients = Utils.getIngredientsUserInput(inputIngredientList);

        map.put("apiKey", Constants.API_KEY);
        map.put("ingredients", ingredients);

        Call<List<RecipeResponseSchema>> call = mSpoonacularApi.queryRecipesByIngredients(map);
        call.enqueue(this);
    }

    public void fetchRecipesByNutrientsAndNotify(List<Nutrient> nutrientList) {
        Log.d(TAG, "getNutrientsSearchMetaData is called");

        map.put("apiKey", Constants.API_KEY);

        for (Nutrient nutrient : nutrientList) {
            map.put(nutrient.getName(), String.valueOf(nutrient.getAmount()));
        }

        Call<List<RecipeResponseSchema>> call = mSpoonacularApi.queryRecipesByNutrients(map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<List<RecipeResponseSchema>> call, Response<List<RecipeResponseSchema>> response) {
        Log.d(TAG, "onResponse: " + response.isSuccessful());
        if (response.isSuccessful()) {
            Log.d(TAG, "onResponse successful: " + response.body());
            List<RecipeResponseSchema> recipesListResponse = response.body();
            List<RecipeDetails> recipeDetailsList = new ArrayList<>();
            if (recipesListResponse != null && recipesListResponse.size() > 0) {
                for (RecipeResponseSchema recipeResponseSchema : recipesListResponse) {
                    RecipeDetails recipeDetails = new RecipeDetails();
                    recipeDetails.setId(recipeResponseSchema.getId());
                    recipeDetails.setImageUrl(recipeResponseSchema.getImage());
                    recipeDetails.setMissedIngredientCount(recipeResponseSchema.getMissedIngredientCount());
                    recipeDetails.setMissedIngredients(recipeResponseSchema.getMissedIngredients());
                    recipeDetails.setUnusedIngredients(recipeResponseSchema.getUnusedIngredients());
                    recipeDetails.setTitle(recipeResponseSchema.getTitle());
                    recipeDetails.setUsedIngredientCount(recipeResponseSchema.getUsedIngredientCount());
                    recipeDetails.setUsedIngredients(recipeResponseSchema.getUsedIngredients());
                    recipeDetailsList.add(recipeDetails);
                }

                mRecipesManager.filterRecipes(recipeDetailsList, inputIngredientList);
            }
        } else {
            Log.d(TAG, "onResponse error: " + response.code());
            notifyFailure();
        }
    }

    @Override
    public void onFailure(@NonNull Call<List<RecipeResponseSchema>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
        notifyFailure();
    }

    @Override
    public void onRecipesFiltered(List<RecipeDetails> recipes) {
        notifySuccess(recipes);
    }

    private void notifySuccess(List<RecipeDetails> recipes) {
        for (Listener listener : getListeners()) {
            listener.onFetchRecipesSuccess(recipes);
        }
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onFetchRecipesFailure();
        }
    }
}
