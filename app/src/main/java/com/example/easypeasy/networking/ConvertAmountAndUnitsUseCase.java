package com.example.easypeasy.networking;

import android.util.Log;

import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.models.IngredientWithConvertedAmount;
import com.example.easypeasy.models.RecipeData;
import com.example.easypeasy.models.schemas.ConvertAmountSchema;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ConvertAmountAndUnitsUseCase extends BaseObservableViewMvc<ConvertAmountAndUnitsUseCase.Listener> {

    public interface Listener {
        void onConvertAmountsSuccess(List<IngredientWithConvertedAmount> ingredientWithConvertedAmountList);
    }

    private static final String TAG = ConvertAmountAndUnitsUseCase.class.getSimpleName();
    private final SpoonacularApi mSpoonacularApi;

    private Map<String, String> map = new HashMap<>();
    private final List<Observable<?>> mConvertRequests;

    public ConvertAmountAndUnitsUseCase(SpoonacularApi spoonacularApi) {
        mConvertRequests = new ArrayList<>();
        mSpoonacularApi = spoonacularApi;
    }

    public void addConvertedUnitsAndAmountRequest(String ingredientName, String responseIngredientUnit, Map<String, String> inputIngredientData, RecipeData currentRecipeData) {
        Log.d(TAG, "getConvertedAmountAndUnit is called");

        map = new HashMap<>();
        map.put("apiKey", Constants.API_KEY);
        map.put("ingredientName", ingredientName);
        map.put("sourceAmount", inputIngredientData.get("amount"));
        map.put("sourceUnit", inputIngredientData.get("unit"));
        map.put("targetUnit", responseIngredientUnit);

        mConvertRequests.add(mSpoonacularApi.convertAmountAndUnit(map)
                .onErrorReturn(throwable -> new ConvertAmountSchema())
                .map((Function<ConvertAmountSchema, Object>) convertAmountSchema -> {
                    IngredientWithConvertedAmount ingredient = new IngredientWithConvertedAmount(currentRecipeData);
                    ingredient.setIngredientName(ingredientName);
                    return ingredient;
                }));
    }

    public void makeRequests() {
        List<IngredientWithConvertedAmount> ingredientWithConvertedAmountList = new ArrayList<>();
        Observable.merge(mConvertRequests)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(mConvertRequests.size())
                // executed when the channel is closed or disposed
                .doFinally(() -> {
                    for (Listener listener : getListeners()) {
                        listener.onConvertAmountsSuccess(ingredientWithConvertedAmountList);
                    }
                })
                .subscribe(details -> {
                    Log.d(TAG, "convertAmountsResponse: " + details.toString());
                    ConvertAmountSchema convertAmountSchema = (ConvertAmountSchema) details;
                    IngredientWithConvertedAmount ingredient = new IngredientWithConvertedAmount();
                    ingredient.setSourceAmount(convertAmountSchema.getSourceAmount());
                    ingredient.setSourceUnit(convertAmountSchema.getSourceUnit());
                    ingredient.setTargetAmount(convertAmountSchema.getTargetAmount());
                    ingredient.setTargetUnit(convertAmountSchema.getTargetUnit());
                    ingredientWithConvertedAmountList.add(ingredient);
                }, e -> {
                    Log.d(TAG, "error: " + e.getMessage());
                });
    }
}
