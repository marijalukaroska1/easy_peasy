package com.example.easypeasy.common.utils;

import android.util.Log;

import com.example.easypeasy.models.IngredientWithConvertedAmount;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.RecipeData;
import com.example.easypeasy.networking.ConvertAmountAndUnitsUseCase;
import com.example.easypeasy.networking.SpoonacularApi;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipesManager extends BaseObservableViewMvc<RecipesManager.Listener> implements ConvertAmountAndUnitsUseCase.Listener {

    public interface Listener {
        void onRecipesFiltered(List<RecipeData> recipeData);
    }

    private static final String TAG = RecipesManager.class.getSimpleName();
    private boolean mIsSearchByIngredients = false;
    private List<RecipeData> mFilteredRecipeData;
    ConvertAmountAndUnitsUseCase convertAmountAndUnitsUseCase;

    public RecipesManager(SpoonacularApi spoonacularApi) {
        convertAmountAndUnitsUseCase = new ConvertAmountAndUnitsUseCase(spoonacularApi);
        convertAmountAndUnitsUseCase.registerListener(this);
    }

    public void filterRecipes(List<RecipeData> recipeDataList, List<Ingredient> ingredientList) {
        mFilteredRecipeData = new ArrayList<>(recipeDataList);
        if (mIsSearchByIngredients) {
            Map<String, Map<String, String>> inputIngredientsMap = Utils.mapIngredientNameWithAmountAndUnit(ingredientList);
            for (RecipeData recipeData : recipeDataList) {
                Log.d(TAG, "recipe name: " + recipeData.getTitle() + " number of ingredients: " + recipeData.getUsedIngredients().size());
                Log.d(TAG, "=================================================");
                for (Ingredient responseIngredient : recipeData.getUsedIngredients()) {
                    if (inputIngredientsMap.containsKey(responseIngredient.getName())) {
                        if (!responseIngredient.getUnit().isEmpty() && !responseIngredient.getUnit().equalsIgnoreCase(inputIngredientsMap.get(responseIngredient.getName()).get("unit"))) {
                            Log.d(TAG, "different unit for both ingredients: " + inputIngredientsMap.get(responseIngredient.getName()).get("unit") + " " + inputIngredientsMap.get(responseIngredient.getName()).get("amount") + " " + responseIngredient.getUnit() + " " + responseIngredient.getAmount());
                            convertAmountAndUnitsUseCase.addConvertedUnitsAndAmountRequest(responseIngredient.getName(), responseIngredient.getUnit(), inputIngredientsMap.get(responseIngredient.getName()), recipeData);
                        } else {
                            Log.d(TAG, "same unit for both ingredients: " + inputIngredientsMap.get(responseIngredient.getName()).get("unit") + " " + responseIngredient.getUnit());
                            if (responseIngredient.getAmount() > Float.parseFloat(inputIngredientsMap.get(responseIngredient.getName()).get("amount"))) {
                                mFilteredRecipeData.remove(recipeData);
                            }
                        }
                    }
                }
            }
            convertAmountAndUnitsUseCase.makeRequests();
        } else {
            for (Listener listener : getListeners()) {
                listener.onRecipesFiltered(mFilteredRecipeData);
            }
        }
    }

    @Override
    public void onConvertAmountsSuccess(List<IngredientWithConvertedAmount> ingredientWithConvertedAmountList) {
        for (IngredientWithConvertedAmount ingredientWIthConvertedAmount : ingredientWithConvertedAmountList) {
            for (Ingredient ingredient : ingredientWIthConvertedAmount.getRecipe().getUsedIngredients()) {
                if (ingredient.getName().equalsIgnoreCase(ingredientWIthConvertedAmount.getIngredientName())) {
                    if (ingredient.getUnit().equalsIgnoreCase(ingredientWIthConvertedAmount.getTargetUnit())) {
                        if (ingredient.getAmount() > ingredientWIthConvertedAmount.getTargetAmount()) {
                            Log.d(TAG, "Removing Recipe: " + ingredientWIthConvertedAmount.getRecipe().getTitle() + "\n " +
                                    "insert ingredient name: " + ingredientWIthConvertedAmount.getIngredientName() + "\n" +
                                    "insert ingredient unit: " + ingredientWIthConvertedAmount.getTargetUnit() + "\n" +
                                    "recipe ingredient unit: " + ingredient.getUnit() + "\n" +
                                    "insert ingredient amount: " + ingredientWIthConvertedAmount.getTargetAmount() + "\n" +
                                    "recipe ingredient amount: " + ingredient.getAmount() + "\n" +
                                    mFilteredRecipeData.remove(ingredientWIthConvertedAmount.getRecipe()));
                        }
                    }
                }
            }
        }
        for (Listener listener : getListeners()) {
            listener.onRecipesFiltered(mFilteredRecipeData);
        }
    }

    public void setmIsSearchByIngredients(boolean mIsSearchByIngredients) {
        this.mIsSearchByIngredients = mIsSearchByIngredients;
    }
}
