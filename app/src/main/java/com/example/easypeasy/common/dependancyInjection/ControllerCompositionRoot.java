package com.example.easypeasy.common.dependancyInjection;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import com.example.easypeasy.networking.FetchRecipeInformationUseCase;
import com.example.easypeasy.screens.common.ViewMvcFactory;
import com.example.easypeasy.networking.FetchIngredientMetaDataUseCase;
import com.example.easypeasy.networking.FetchIngredientsNamesUseCase;
import com.example.easypeasy.networking.FetchRecipesUseCase;
import com.example.easypeasy.networking.SpoonacularApi;

public class ControllerCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final Activity mActivity;

    public ControllerCompositionRoot(CompositionRoot mCompositionRoot, Activity activity) {
        this.mCompositionRoot = mCompositionRoot;
        mActivity = activity;
    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(getLayoutInflater());
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    public SpoonacularApi getSpoonacularApi() {
        return mCompositionRoot.getSpoonacularRecipesApi();
    }

    public FetchRecipesUseCase getFetchRecipesUseCase() {
        return new FetchRecipesUseCase(getSpoonacularApi());
    }

    public FetchIngredientsNamesUseCase getFetchIngredientsNamesUseCase() {
        return new FetchIngredientsNamesUseCase(getSpoonacularApi());
    }

    public FetchIngredientMetaDataUseCase getFetchIngredientMetaDataUseCase() {
        return new FetchIngredientMetaDataUseCase(getSpoonacularApi());
    }

    public FetchRecipeInformationUseCase getFetchRecipeInformationUseCase() {
        return new FetchRecipeInformationUseCase(getSpoonacularApi());
    }
}
