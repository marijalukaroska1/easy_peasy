package com.example.easypeasy.recipes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.common.utils.RecipesManager;
import com.example.easypeasy.common.utils.Utils;
import com.example.easypeasy.networking.SpoonacularApi;
import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.networking.nutrients.NutrientSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.networking.recipes.RecipeResponseSchema;
import com.example.easypeasy.screens.common.views.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchRecipesUseCase extends BaseObservableViewMvc<FetchRecipesUseCase.Listener> implements Callback<List<RecipeResponseSchema>>, RecipesManager.Listener {

    public interface Listener {
        void onFetchRecipesSuccess(List<RecipeDetailsSchema> recipeData);

        void onFetchRecipesFailure();
    }

    private static final String TAG = FetchRecipesUseCase.class.getSimpleName();
    public boolean isSearchByIngredients = false;
    private final SpoonacularApi mSpoonacularApi;
    private List<IngredientSchema> inputIngredientList;
    private final Map<String, String> map = new HashMap<>();
    private final RecipesManager mRecipesManager;

    public FetchRecipesUseCase(SpoonacularApi spoonacularApi) {
        mSpoonacularApi = spoonacularApi;
        mRecipesManager = new RecipesManager(spoonacularApi);
        mRecipesManager.registerListener(this);
    }

    public void fetchRecipesByIngredientsAndNotify(List<IngredientSchema> inputIngredientList) {
        mRecipesManager.setmIsSearchByIngredients(true);
        Log.d(TAG, "fetchRecipesByIngredientsAndNotify is called: " + inputIngredientList);
        this.inputIngredientList = inputIngredientList;
        String ingredients = Utils.getIngredientsUserInput(inputIngredientList);

        map.put("apiKey", Constants.API_KEY);
        map.put("ingredients", ingredients);

        Call<List<RecipeResponseSchema>> call = mSpoonacularApi.queryRecipesByIngredients(map);
        call.enqueue(this);
    }

    public void fetchRecipesByNutrientsAndNotify(List<NutrientSchema> nutrientList) {
        Log.d(TAG, "getNutrientsSearchMetaData is called");

        map.put("apiKey", Constants.API_KEY);

        for (NutrientSchema nutrient : nutrientList) {
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
            List<RecipeDetailsSchema> recipeDetailsSchemaList = new ArrayList<>();
            if (recipesListResponse != null && recipesListResponse.size() > 0) {
                for (RecipeResponseSchema recipeResponseSchema : recipesListResponse) {
                    RecipeDetailsSchema recipeDetailsSchema = new RecipeDetailsSchema();
                    recipeDetailsSchema.setId(recipeResponseSchema.getId());
                    recipeDetailsSchema.setImageUrl(recipeResponseSchema.getImage());
                    recipeDetailsSchema.setMissedIngredientCount(recipeResponseSchema.getMissedIngredientCount());
                    recipeDetailsSchema.setMissedIngredients(recipeResponseSchema.getMissedIngredients());
                    recipeDetailsSchema.setUnusedIngredients(recipeResponseSchema.getUnusedIngredients());
                    recipeDetailsSchema.setTitle(recipeResponseSchema.getTitle());
                    recipeDetailsSchema.setUsedIngredientCount(recipeResponseSchema.getUsedIngredientCount());
                    recipeDetailsSchema.setUsedIngredients(recipeResponseSchema.getUsedIngredients());
                    recipeDetailsSchemaList.add(recipeDetailsSchema);
                }

                mRecipesManager.filterRecipes(recipeDetailsSchemaList, inputIngredientList);
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
    public void onRecipesFiltered(List<RecipeDetailsSchema> recipes) {
        notifySuccess(recipes);
    }

    private void notifySuccess(List<RecipeDetailsSchema> recipes) {
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
