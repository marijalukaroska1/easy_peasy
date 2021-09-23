package com.example.easypeasy;

import android.content.Context;
import android.util.Log;

import com.example.easypeasy.activities.SearchInput;
import com.example.easypeasy.adapters.RecipesAdapter;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.spoonacular.RecipesRequest;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecipesPresenter implements RecipesPresenterInput {

    private static final String TAG = RecipesPresenter.class.getSimpleName();
    public WeakReference<SearchInput> output;
    int convertAmountResponsesNumber = 0;
    int convertAmountRequestsNumber = 0;

    @Override
    public void presentRecipesData(List<Recipe> recipesResponse, Context context) {
        Log.d(TAG, "convertAmountRequestsNumber: " + RecipesRequest.getConvertAmountRequestsNumber() + " convertAmountResponsesNumber: " + convertAmountResponsesNumber);
        if (this.convertAmountRequestsNumber == convertAmountResponsesNumber) {
            RecipesAdapter recipesAdapter = new RecipesAdapter(recipesResponse, context);
            output.get().displayRecipesMetaData(recipesAdapter);
        }
    }

    @Override
    public void presentIngredientData(Ingredient ingredient) {
        output.get().displayIngredientUnits(ingredient.getPossibleUnits());
    }

    @Override
    public void presentIngredients(List<Ingredient> ingredientList) {

    }

    @Override
    public void convertAmountResponse() {
        convertAmountResponsesNumber++;
    }
}
