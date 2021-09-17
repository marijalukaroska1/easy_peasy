package com.example.easypeasy;

import android.content.Context;

import com.example.easypeasy.activities.SearchInput;
import com.example.easypeasy.adapters.RecipesAdapter;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Recipe;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecipesPresenter implements RecipesPresenterInput {

    public WeakReference<SearchInput> output;

    @Override
    public void presentRecipesData(List<Recipe> recipesResponse, Context context) {
        RecipesAdapter recipesAdapter = new RecipesAdapter(recipesResponse, context);
        output.get().displayRecipesMetaData(recipesAdapter);
    }

    @Override
    public void presentIngredientData(Ingredient ingredient) {

    }
}
