package com.example.easypeasy.networking;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.schemas.SearchIngredientDetailsResponseSchema;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FetchIngredientsNamesUseCase extends BaseObservableViewMvc<FetchIngredientsNamesUseCase.Listener> implements Callback<SearchIngredientDetailsResponseSchema> {

    public interface Listener {
        void onFetchIngredientSearchMetaDataSuccess(long ingredientId);

        void onFetchIngredientSearchMetaDataFailure();
    }

    private static final String TAG = FetchIngredientsNamesUseCase.class.getSimpleName();
    private final SpoonacularApi mSpoonacularApi;
    private final Map<String, String> map = new HashMap<>();

    public FetchIngredientsNamesUseCase(SpoonacularApi spoonacularApi) {
        mSpoonacularApi = spoonacularApi;
    }


    public void fetchIngredientsSearchMetaDataAndNotify(String ingredientName) {
        Log.d(TAG, "fetchIngredientsSearchMetaDataAndNotify is called");

        map.put("apiKey", Constants.API_KEY);
        map.put("query", ingredientName);

        Call<SearchIngredientDetailsResponseSchema> call = mSpoonacularApi.searchIngredients(map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<SearchIngredientDetailsResponseSchema> call, Response<SearchIngredientDetailsResponseSchema> response) {
        Log.d(TAG, "onResponse: " + response.isSuccessful());
        if (response.isSuccessful() && response.body() != null) {
            Log.d(TAG, "onResponse successful: " + response.body());
            List<Ingredient> ingredientList = response.body().getIngredientList();
            if (ingredientList != null && ingredientList.size() > 0) {
                notifySuccess(ingredientList.get(0).getId());
            }
        } else {
            Log.d(TAG, "onResponse error: " + response.code());
            notifyFailure();
        }

    }

    @Override
    public void onFailure(@NonNull Call<SearchIngredientDetailsResponseSchema> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
        notifyFailure();
    }

    private void notifySuccess(long ingredientId) {
        for (Listener listener : getListeners()) {
            listener.onFetchIngredientSearchMetaDataSuccess(ingredientId);
        }
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onFetchIngredientSearchMetaDataFailure();
        }
    }
}
