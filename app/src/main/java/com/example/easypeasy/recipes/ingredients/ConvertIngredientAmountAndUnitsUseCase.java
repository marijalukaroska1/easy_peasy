package com.example.easypeasy.recipes.ingredients;

import android.util.Log;

import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.networking.SpoonacularApi;
import com.example.easypeasy.networking.ingredients.IngredientWithConvertedAmountSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.networking.ingredients.ConvertIngredientAmountResponseSchema;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ConvertIngredientAmountAndUnitsUseCase extends BaseObservableViewMvc<ConvertIngredientAmountAndUnitsUseCase.Listener> {

    public interface Listener {
        void onConvertAmountsSuccess(List<IngredientWithConvertedAmountSchema> ingredientWithConvertedAmountSchemaList);
    }

    private static final String TAG = ConvertIngredientAmountAndUnitsUseCase.class.getSimpleName();
    private final SpoonacularApi mSpoonacularApi;

    private Map<String, String> map = new HashMap<>();
    private final List<Observable<?>> mConvertRequests;

    public ConvertIngredientAmountAndUnitsUseCase(SpoonacularApi spoonacularApi) {
        mConvertRequests = new ArrayList<>();
        mSpoonacularApi = spoonacularApi;
    }

    public void addConvertedUnitsAndAmountRequest(String ingredientName, String responseIngredientUnit, Map<String, String> inputIngredientData, RecipeDetailsSchema currentRecipeDetailsSchema) {
        Log.d(TAG, "getConvertedAmountAndUnit is called");

        map = new HashMap<>();
        map.put("apiKey", Constants.API_KEY);
        map.put("ingredientName", ingredientName);
        map.put("sourceAmount", inputIngredientData.get("amount"));
        map.put("sourceUnit", inputIngredientData.get("unit"));
        map.put("targetUnit", responseIngredientUnit);

        mConvertRequests.add(mSpoonacularApi.convertAmountAndUnit(map)
                .onErrorReturn(throwable -> new ConvertIngredientAmountResponseSchema())
                .map((Function<ConvertIngredientAmountResponseSchema, Object>) convertAmountResponseSchema -> {
                    IngredientWithConvertedAmountSchema ingredient = new IngredientWithConvertedAmountSchema(currentRecipeDetailsSchema);
                    ingredient.setIngredientName(ingredientName);
                    return ingredient;
                }));
    }

    public void makeRequests() {
        List<IngredientWithConvertedAmountSchema> ingredientWithConvertedAmountSchemaList = new ArrayList<>();
        Observable.merge(mConvertRequests)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(mConvertRequests.size())
                // executed when the channel is closed or disposed
                .doFinally(() -> {
                    for (Listener listener : getListeners()) {
                        listener.onConvertAmountsSuccess(ingredientWithConvertedAmountSchemaList);
                    }
                })
                .subscribe(details -> {
                    Log.d(TAG, "convertAmountsResponse: " + details.toString());
                    ConvertIngredientAmountResponseSchema convertAmountResponseSchema = (ConvertIngredientAmountResponseSchema) details;
                    IngredientWithConvertedAmountSchema ingredient = new IngredientWithConvertedAmountSchema();
                    ingredient.setSourceAmount(convertAmountResponseSchema.getSourceAmount());
                    ingredient.setSourceUnit(convertAmountResponseSchema.getSourceUnit());
                    ingredient.setTargetAmount(convertAmountResponseSchema.getTargetAmount());
                    ingredient.setTargetUnit(convertAmountResponseSchema.getTargetUnit());
                    ingredientWithConvertedAmountSchemaList.add(ingredient);
                }, e -> {
                    Log.d(TAG, "error: " + e.getMessage());
                });
    }
}
