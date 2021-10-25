package com.example.easypeasy.networking;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.schemas.IngredientSchema;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchIngredientMetaDataUseCase extends BaseObservableViewMvc<FetchIngredientMetaDataUseCase.Listener> implements Callback<IngredientSchema> {

    public interface Listener {
        void fetchIngredientMetaDataSuccess(Ingredient ingredient);

        void fetchIngredientMetaDataFailure();
    }

    private static final String TAG = "IngredientRequest";
    public SpoonacularApi mSoonacularApi;
    Map<String, String> map = new HashMap<>();

    public FetchIngredientMetaDataUseCase(SpoonacularApi spoonacularApi) {
        mSoonacularApi = spoonacularApi;
    }

    public void getIngredientMetaData(long ingredientId) {
        Log.d(TAG, "getIngredientMetaData is called");

        map.put("id", String.valueOf(ingredientId));
        map.put("apiKey", Constants.API_KEY);

        Call<IngredientSchema> call = mSoonacularApi.queryIngredientData(ingredientId, map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<IngredientSchema> call, Response<IngredientSchema> response) {
        Log.d(TAG, "onResponse: " + response.isSuccessful());
        if (response.isSuccessful()) {
            Log.d(TAG, "onResponse successful: " + response.body());
            IngredientSchema ingredientSchema = response.body();
            if (ingredientSchema != null) {
                Ingredient ingredient = new Ingredient();
                ingredient.setName(ingredientSchema.getName());
                ingredient.setUnit(ingredientSchema.getUnit());
                ingredient.setAmount(ingredientSchema.getAmount());
                ingredient.setId(ingredientSchema.getId());
                ingredient.setNameWithAmount(ingredientSchema.getNameWithAmount());
                ingredient.setPossibleUnits(ingredientSchema.getPossibleUnits());
                ingredient.setUnit(ingredientSchema.getUnitShort());
                for (Listener listener : getListeners()) {
                    listener.fetchIngredientMetaDataSuccess(ingredient);
                }
            } else {
                for (Listener listener : getListeners()) {
                    listener.fetchIngredientMetaDataFailure();
                }
            }
        } else {
            Log.d(TAG, "onResponse error: " + response.code());
        }
    }

    @Override
    public void onFailure(@NonNull Call<IngredientSchema> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
        for (Listener listener : getListeners()) {
            listener.fetchIngredientMetaDataFailure();
        }
    }
}
