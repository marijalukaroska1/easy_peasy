package com.example.easypeasy.networking;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.common.utils.RecipesManager;
import com.example.easypeasy.common.utils.Utils;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Nutrient;
import com.example.easypeasy.models.RecipeData;
import com.example.easypeasy.models.schemas.RecipeSchema;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchRecipesUseCase extends BaseObservableViewMvc<FetchRecipesUseCase.Listener> implements Callback<List<RecipeSchema>>, RecipesManager.Listener {

    public interface Listener {
        void onRecipesFetchedSuccess(List<RecipeData> recipeData);

        void onRecipesFetchedFailure();
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

        Call<List<RecipeSchema>> call = mSpoonacularApi.queryRecipesByIngredients(map);
        call.enqueue(this);
    }

    public void getRecipesByNutrients(List<Nutrient> nutrientList) {
        Log.d(TAG, "getNutrientsSearchMetaData is called");

        map.put("apiKey", Constants.API_KEY);

        for (Nutrient nutrient : nutrientList) {
            map.put(nutrient.getName(), String.valueOf(nutrient.getAmount()));
        }

        Call<List<RecipeSchema>> call = mSpoonacularApi.queryRecipesByNutrients(map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<List<RecipeSchema>> call, Response<List<RecipeSchema>> response) {
        Log.d(TAG, "onResponse: " + response.isSuccessful());
        if (response.isSuccessful()) {
            Log.d(TAG, "onResponse successful: " + response.body());
            List<RecipeSchema> recipesListResponse = response.body();
            List<RecipeData> recipeDataList = new ArrayList<>();
            if (recipesListResponse != null && recipesListResponse.size() > 0) {
                for (RecipeSchema recipeSchema : recipesListResponse) {
                    RecipeData recipeData = new RecipeData();
                    recipeData.setId(recipeSchema.getId());
                    recipeData.setImageUrl(recipeSchema.getImage());
                    recipeData.setMissedIngredientCount(recipeSchema.getMissedIngredientCount());
                    recipeData.setMissedIngredients(recipeSchema.getMissedIngredients());
                    recipeData.setUnusedIngredients(recipeSchema.getUnusedIngredients());
                    recipeData.setTitle(recipeSchema.getTitle());
                    recipeData.setUsedIngredientCount(recipeSchema.getUsedIngredientCount());
                    recipeData.setUsedIngredients(recipeSchema.getUsedIngredients());
                    recipeDataList.add(recipeData);
                }

                mRecipesManager.filterRecipes(recipeDataList, inputIngredientList);
            }
        } else {
            Log.d(TAG, "onResponse error: " + response.code());
        }
    }

    @Override
    public void onFailure(@NonNull Call<List<RecipeSchema>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
        for (Listener listener : getListeners()) {
            listener.onRecipesFetchedFailure();
        }
    }

    @Override
    public void onRecipesFiltered(List<RecipeData> recipeData) {
        for (Listener listener : getListeners()) {
            listener.onRecipesFetchedSuccess(recipeData);
        }
    }
}
