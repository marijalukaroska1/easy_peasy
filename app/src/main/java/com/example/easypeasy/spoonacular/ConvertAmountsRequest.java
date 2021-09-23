package com.example.easypeasy.spoonacular;

import android.content.Context;
import android.util.Log;

import com.example.easypeasy.Constants;
import com.example.easypeasy.RecipesPresenterInput;
import com.example.easypeasy.models.ConvertAmountsResponse;
import com.example.easypeasy.models.Recipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConvertAmountsRequest extends BaseRequest implements Callback<ConvertAmountsResponse> {
    private static final String TAG = ConvertAmountsRequest.class.getSimpleName();
    public SpoonacularRecipesApi spoonacularApi;
    RecipesPresenterInput output;
    Map<String, String> map = new HashMap<>();
    Context context;
    float responseIngredientAmount;
    Map<String, String> inputIngredientData;
    List<Recipe> filteredRecipes;
    Recipe currentRecipe;
    int convertAmountRequestsNumber;

    public ConvertAmountsRequest(Context context) {
        spoonacularApi = buildRecipesUrl();
        this.context = context;
    }

    public void getConvertedAmountAndUnit(RecipesPresenterInput output, String ingredientName, Float responseIngredientAmount, String responseIngredientUnit, Map<String, String> inputIngredientData, List<Recipe> filteredRecipes, Recipe currentRecipe) {
        Log.d(TAG, "getConvertedAmountAndUnit is called");
        this.output = output;
        this.inputIngredientData = inputIngredientData;
        this.responseIngredientAmount = responseIngredientAmount;
        this.filteredRecipes = filteredRecipes;
        this.currentRecipe = currentRecipe;

        map.put("apiKey", Constants.API_KEY);
        map.put("ingredientName", ingredientName);
        map.put("sourceAmount", inputIngredientData.get("amount"));
        map.put("sourceUnit", inputIngredientData.get("unit"));
        map.put("targetUnit", responseIngredientUnit);

        Call<ConvertAmountsResponse> call = spoonacularApi.convertAmountAndUnit(map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ConvertAmountsResponse> call, Response<ConvertAmountsResponse> response) {
        Log.d(TAG, "onResponse: " + response.isSuccessful());
        output.convertAmountResponse();
        if (response.isSuccessful()) {
            Log.d(TAG, "onResponse: " + currentRecipe.getTitle() + " " + response.body() + " convertAmountRequestsNumber" + convertAmountRequestsNumber);
            ConvertAmountsResponse convertInputIngredientAmountResponse = response.body();
            if (convertInputIngredientAmountResponse.getTargetAmount() != 0.0) {
                if (responseIngredientAmount > convertInputIngredientAmountResponse.getTargetAmount()) {
                    filteredRecipes.remove(currentRecipe);
                }
            }
        }
        output.presentRecipesData(filteredRecipes, context);
    }

    @Override
    public void onFailure(Call<ConvertAmountsResponse> call, Throwable t) {
        output.convertAmountResponse();
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
