package com.example.easypeasy;

import android.content.Context;
import android.util.Log;

import com.example.easypeasy.screens.common.SearchInput;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Recipe;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecipesPresenter implements RecipesPresenterInput {

    private static final String TAG = RecipesPresenter.class.getSimpleName();
    public WeakReference<SearchInput> output;


    @Override
    public void presentRecipesData(List<Recipe> recipesResponse, Context context) {
        Log.d(TAG, "in presentRecipesData");
        output.get().displayRecipesMetaData(recipesResponse);
    }

    @Override
    public void presentIngredientData(Ingredient ingredient) {
        output.get().displayIngredientUnits(ingredient.getPossibleUnits());
    }

    @Override
    public void presentIngredients(List<Ingredient> ingredientList) {

    }
}
