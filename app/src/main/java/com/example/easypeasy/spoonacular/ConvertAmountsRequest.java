package com.example.easypeasy.spoonacular;

import android.content.Context;
import android.util.Log;

import com.example.easypeasy.RecipesPresenterInput;
import com.example.easypeasy.models.ConvertAmountsResponse;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ConvertAmountsRequest extends BaseRequest {
    private static final String TAG = ConvertAmountsRequest.class.getSimpleName();
    public SpoonacularRecipesApi spoonacularApi;
    RecipesPresenterInput output;
    Map<String, String> map = new HashMap<>();
    Context context;
    Map<String, String> inputIngredientData;
    List<Observable<?>> convertRequests;

    public ConvertAmountsRequest(Context context) {
        convertRequests = new ArrayList<>();
        spoonacularApi = buildRecipesUrl();
        this.context = context;
    }

    public void addConvertedUnitsAndAmountRequest(String ingredientName, String responseIngredientUnit, Map<String, String> inputIngredientData, Recipe currentRecipe) {
        Log.d(TAG, "getConvertedAmountAndUnit is called");

        this.inputIngredientData = inputIngredientData;
        map = new HashMap<>();
        map.put("apiKey", Constants.API_KEY);
        map.put("ingredientName", ingredientName);
        map.put("sourceAmount", inputIngredientData.get("amount"));
        map.put("sourceUnit", inputIngredientData.get("unit"));
        map.put("targetUnit", responseIngredientUnit);

        convertRequests.add(spoonacularApi.convertAmountAndUnit(map)
                .onErrorReturn(throwable -> new ConvertAmountsResponse(currentRecipe))
                .map((Function<ConvertAmountsResponse, Object>) convertAmountsResponse -> {
                    convertAmountsResponse.setRecipe(currentRecipe);
                    return convertAmountsResponse;
                }));
    }

    public void makeRequests(RecipesPresenterInput output, List<Recipe> filteredRecipes) {
        this.output = output;
        Observable.merge(convertRequests)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(convertRequests.size())
                // executed when the channel is closed or disposed
                .doFinally(() -> {
                    output.presentRecipesData(filteredRecipes, context);
                })
                .subscribe(details -> {
                    ConvertAmountsResponse convertAmountsResponse = (ConvertAmountsResponse) details;
                    Log.d(TAG, "convertAmountsResponse: " + details.toString());

                    if (convertAmountsResponse.getTargetUnit() != null) {
                        for (Ingredient ingredient : convertAmountsResponse.getRecipe().getUsedIngredients()) {
                            if (ingredient.getUnit().equalsIgnoreCase(convertAmountsResponse.getTargetUnit())) {
                                if (convertAmountsResponse.getTargetAmount() < ingredient.getAmount()) {
                                    Log.d(TAG, "removing: " + convertAmountsResponse.getRecipe() + " convertAmountsResponse.getTargetUnit(): "
                                            + convertAmountsResponse.getTargetUnit() + " convertAmountsResponse.getTargetAmount(): " +
                                            convertAmountsResponse.getTargetAmount() + " ingredient.getUnit(): " + ingredient.getUnit() +
                                            " ingredient.getAmount(): " + ingredient.getAmount());
                                    filteredRecipes.remove(convertAmountsResponse.getRecipe());
                                }
                            }
                        }
                    }
                }, e -> {
                    Log.d(TAG, "error: " + e.getMessage());
                });
    }
}
