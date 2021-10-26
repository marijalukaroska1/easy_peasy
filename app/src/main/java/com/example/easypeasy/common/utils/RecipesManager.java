package com.example.easypeasy.common.utils;

import android.util.Log;

import com.example.easypeasy.networking.ingredients.IngredientWithConvertedAmountSchema;
import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.recipes.ingredients.ConvertIngredientAmountAndUnitsUseCase;
import com.example.easypeasy.networking.SpoonacularApi;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipesManager extends BaseObservableViewMvc<RecipesManager.Listener> implements ConvertIngredientAmountAndUnitsUseCase.Listener {

    public interface Listener {
        void onRecipesFiltered(List<RecipeDetailsSchema> recipeData);
    }

    private static final String TAG = RecipesManager.class.getSimpleName();
    private boolean mIsSearchByIngredients = false;
    private List<RecipeDetailsSchema> mFilteredRecipeData;
    ConvertIngredientAmountAndUnitsUseCase convertAmountAndUnitsUseCase;

    public RecipesManager(SpoonacularApi spoonacularApi) {
        convertAmountAndUnitsUseCase = new ConvertIngredientAmountAndUnitsUseCase(spoonacularApi);
        convertAmountAndUnitsUseCase.registerListener(this);
    }

    public void filterRecipes(List<RecipeDetailsSchema> recipeDetailsSchemaList, List<IngredientSchema> ingredientList) {
        mFilteredRecipeData = new ArrayList<>(recipeDetailsSchemaList);
        if (mIsSearchByIngredients) {
            Map<String, Map<String, String>> inputIngredientsMap = Utils.mapIngredientNameWithAmountAndUnit(ingredientList);
            for (RecipeDetailsSchema recipeDetailsSchema : recipeDetailsSchemaList) {
                Log.d(TAG, "recipe name: " + recipeDetailsSchema.getTitle() + " number of ingredients: " + recipeDetailsSchema.getUsedIngredients().size());
                Log.d(TAG, "=================================================");
                for (IngredientSchema responseIngredient : recipeDetailsSchema.getUsedIngredients()) {
                    if (inputIngredientsMap.containsKey(responseIngredient.getName())) {
                        if (!responseIngredient.getUnit().isEmpty() && !responseIngredient.getUnit().equalsIgnoreCase(inputIngredientsMap.get(responseIngredient.getName()).get("unit"))) {
                            Log.d(TAG, "different unit for both ingredients: " + inputIngredientsMap.get(responseIngredient.getName()).get("unit") + " " + inputIngredientsMap.get(responseIngredient.getName()).get("amount") + " " + responseIngredient.getUnit() + " " + responseIngredient.getAmount());
                            convertAmountAndUnitsUseCase.addConvertedUnitsAndAmountRequest(responseIngredient.getName(), responseIngredient.getUnit(), inputIngredientsMap.get(responseIngredient.getName()), recipeDetailsSchema);
                        } else {
                            Log.d(TAG, "same unit for both ingredients: " + inputIngredientsMap.get(responseIngredient.getName()).get("unit") + " " + responseIngredient.getUnit());
                            if (responseIngredient.getAmount() > Float.parseFloat(inputIngredientsMap.get(responseIngredient.getName()).get("amount"))) {
                                mFilteredRecipeData.remove(recipeDetailsSchema);
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
    public void onConvertAmountsSuccess(List<IngredientWithConvertedAmountSchema> ingredientWithConvertedAmountSchemaList) {
        for (IngredientWithConvertedAmountSchema ingredientWIthConvertedAmountSchema : ingredientWithConvertedAmountSchemaList) {
            for (IngredientSchema ingredient : ingredientWIthConvertedAmountSchema.getRecipe().getUsedIngredients()) {
                if (ingredient.getName().equalsIgnoreCase(ingredientWIthConvertedAmountSchema.getIngredientName())) {
                    if (ingredient.getUnit().equalsIgnoreCase(ingredientWIthConvertedAmountSchema.getTargetUnit())) {
                        if (ingredient.getAmount() > ingredientWIthConvertedAmountSchema.getTargetAmount()) {
                            Log.d(TAG, "Removing Recipe: " + ingredientWIthConvertedAmountSchema.getRecipe().getTitle() + "\n " +
                                    "insert ingredient name: " + ingredientWIthConvertedAmountSchema.getIngredientName() + "\n" +
                                    "insert ingredient unit: " + ingredientWIthConvertedAmountSchema.getTargetUnit() + "\n" +
                                    "recipe ingredient unit: " + ingredient.getUnit() + "\n" +
                                    "insert ingredient amount: " + ingredientWIthConvertedAmountSchema.getTargetAmount() + "\n" +
                                    "recipe ingredient amount: " + ingredient.getAmount() + "\n" +
                                    mFilteredRecipeData.remove(ingredientWIthConvertedAmountSchema.getRecipe()));
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
