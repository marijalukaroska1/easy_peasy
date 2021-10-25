package com.example.easypeasy.networking;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.schemas.SearchIngredientNameSchema;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FetchIngredientsNamesUseCase extends BaseObservableViewMvc<FetchIngredientsNamesUseCase.Listener> implements Callback<SearchIngredientNameSchema> {

    public interface Listener {
        void onFetchIngredientsNamesSuccess(long ingredientId);

        void onFetchIngredientsNamesFailure();
    }

    private static final String TAG = FetchIngredientsNamesUseCase.class.getSimpleName();
    private final SpoonacularApi mSpoonacularApi;
    Map<String, String> map = new HashMap<>();

    public FetchIngredientsNamesUseCase(SpoonacularApi spoonacularApi) {
        mSpoonacularApi = spoonacularApi;
    }


    public void getIngredientsSearchMetaData(String ingredientName) {
        Log.d(TAG, "getIngredientsSearchMetaData is called");

        map.put("apiKey", Constants.API_KEY);
        map.put("query", ingredientName);

        Call<SearchIngredientNameSchema> call = mSpoonacularApi.searchIngredients(map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<SearchIngredientNameSchema> call, Response<SearchIngredientNameSchema> response) {
        Log.d(TAG, "onResponse: " + response.isSuccessful());
        if (response.isSuccessful() && response.body() != null) {
            Log.d(TAG, "onResponse successful: " + response.body());
            List<Ingredient> ingredientList = response.body().getIngredientList();
            if (ingredientList != null && ingredientList.size() > 0) {
                for (Listener listener : getListeners()) {
                    listener.onFetchIngredientsNamesSuccess(ingredientList.get(0).getId());
                }
            }
        } else {
            Log.d(TAG, "onResponse error: " + response.code());
        }

    }

    @Override
    public void onFailure(@NonNull Call<SearchIngredientNameSchema> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
        for (Listener listener : getListeners()) {
            listener.onFetchIngredientsNamesFailure();
        }
    }
}
