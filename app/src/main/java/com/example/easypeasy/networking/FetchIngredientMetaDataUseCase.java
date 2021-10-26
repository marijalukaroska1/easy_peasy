package com.example.easypeasy.networking;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.schemas.IngredientResponseSchema;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Use cases are just objects that encapsulate application flows and processes.
// Each use case handles a single flow
// In this case the flow is server request followed
// by data conversion and then by notification
public class FetchIngredientMetaDataUseCase extends BaseObservableViewMvc<FetchIngredientMetaDataUseCase.Listener> implements Callback<IngredientResponseSchema> {

    public interface Listener {
        void onFetchIngredientMetaDataSuccess(Ingredient ingredient);

        void onFetchIngredientMetaDataFailure();
    }

    private static final String TAG = "IngredientRequest";
    public SpoonacularApi mSoonacularApi;
    Map<String, String> map = new HashMap<>();

    public FetchIngredientMetaDataUseCase(SpoonacularApi spoonacularApi) {
        mSoonacularApi = spoonacularApi;
    }

    public void fetchIngredientMetaDataAndNotify(long ingredientId) {
        Log.d(TAG, "fetchIngredientMetaDataAndNotify is called");

        map.put("id", String.valueOf(ingredientId));
        map.put("apiKey", Constants.API_KEY);

        Call<IngredientResponseSchema> call = mSoonacularApi.queryIngredientData(ingredientId, map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<IngredientResponseSchema> call, Response<IngredientResponseSchema> response) {
        Log.d(TAG, "onResponse: " + response.isSuccessful());
        if (response.isSuccessful()) {
            Log.d(TAG, "onResponse successful: " + response.body());
            IngredientResponseSchema ingredientResponseSchema = response.body();
            if (ingredientResponseSchema != null) {
                Ingredient ingredient = new Ingredient();
                ingredient.setName(ingredientResponseSchema.getName());
                ingredient.setUnit(ingredientResponseSchema.getUnit());
                ingredient.setAmount(ingredientResponseSchema.getAmount());
                ingredient.setId(ingredientResponseSchema.getId());
                ingredient.setNameWithAmount(ingredientResponseSchema.getNameWithAmount());
                ingredient.setPossibleUnits(ingredientResponseSchema.getPossibleUnits());
                ingredient.setUnit(ingredientResponseSchema.getUnitShort());
                notifySuccess(ingredient);
            } else {
                notifyFailure();
            }
        } else {
            Log.d(TAG, "onResponse error: " + response.code());
        }
    }

    @Override
    public void onFailure(@NonNull Call<IngredientResponseSchema> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
        notifyFailure();
    }

    private void notifySuccess(Ingredient ingredient) {
        for (Listener listener : getListeners()) {
            listener.onFetchIngredientMetaDataSuccess(ingredient);
        }
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onFetchIngredientMetaDataFailure();
        }
    }

}
