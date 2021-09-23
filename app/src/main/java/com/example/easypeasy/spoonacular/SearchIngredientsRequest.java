package com.example.easypeasy.spoonacular;

import android.util.Log;

import com.example.easypeasy.Constants;
import com.example.easypeasy.RecipesInteractorInput;
import com.example.easypeasy.RecipesPresenterInput;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.SearchIngredientsResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchIngredientsRequest extends BaseRequest implements Callback<SearchIngredientsResponse> {

    private static final String TAG = SearchIngredientsRequest.class.getSimpleName();
    public SpoonacularRecipesApi spoonacularApi;

    RecipesPresenterInput output;
    RecipesInteractorInput interactor;
    Map<String, String> map = new HashMap<>();


    public SearchIngredientsRequest() {
        spoonacularApi = buildRecipesUrl();
    }


    public void getIngredientsSearchMetaData(RecipesPresenterInput output, RecipesInteractorInput interactor, String ingredientName) {
        Log.d(TAG, "getIngredientsSearchMetaData is called");
        this.output = output;
        this.interactor = interactor;

        map.put("apiKey", Constants.API_KEY);
        map.put("query", ingredientName);

        Call<SearchIngredientsResponse> call = spoonacularApi.searchIngredients(map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<SearchIngredientsResponse> call, Response<SearchIngredientsResponse> response) {
        Log.d(TAG, "onResponse: " + response.isSuccessful());
        if (response.isSuccessful()) {
            Log.d(TAG, "onResponse successful: " + response.body());
            List<Ingredient> ingredientList = response.body().getIngredientList();
            if (ingredientList != null && ingredientList.size() > 0) {
                IngredientRequest ingredientRequest = new IngredientRequest();
                interactor.fetchIngredientData(ingredientRequest, ingredientList.get(0).getId());
            }
        } else {
            Log.d(TAG, "onResponse error: " + response.code());
        }

    }

    @Override
    public void onFailure(Call<SearchIngredientsResponse> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
