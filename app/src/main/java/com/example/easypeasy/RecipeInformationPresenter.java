package com.example.easypeasy;

import com.example.easypeasy.activities.RecipeInformationInput;
import com.example.easypeasy.adapters.RecipeInformationUsedIngredientsAdapter;
import com.example.easypeasy.models.RecipeInformationResponse;

import java.lang.ref.WeakReference;

public class RecipeInformationPresenter implements RecipeInformationPresenterInput {

    private static final String TAG = RecipeInformationPresenter.class.getSimpleName();
    public WeakReference<RecipeInformationInput> output;

    @Override
    public void presentRecipeInformation(RecipeInformationResponse recipeInformationResponse) {
        RecipeInformationUsedIngredientsAdapter recipeInformationUsedIngredientsAdapter = new RecipeInformationUsedIngredientsAdapter(recipeInformationResponse.getUsedIngredients());
        output.get().displayRecipeInformation(recipeInformationResponse, recipeInformationUsedIngredientsAdapter);
    }
}
