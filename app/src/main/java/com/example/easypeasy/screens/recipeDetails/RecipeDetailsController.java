package com.example.easypeasy.screens.recipeDetails;

import android.util.Log;

import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.recipes.FetchRecipeDetailsUseCase;
import com.example.easypeasy.screens.common.ScreenNavigator;
import com.example.easypeasy.screens.common.ToastHelper;

public class RecipeDetailsController implements FetchRecipeDetailsUseCase.Listener,
        RecipeDetailsViewMvc.Listener {

    private static final String TAG = RecipeDetailsController.class.getSimpleName();
    private RecipeDetailsViewMvc mRecipeDetailsViewMvc;
    private final ScreenNavigator mScreenNavigator;
    private final FetchRecipeDetailsUseCase mFetchRecipeDetailsUseCase;
    private final ToastHelper mToastHelper;
    long mRecipeId = 0L;

    public RecipeDetailsController(ScreenNavigator screenNavigator,
                                   FetchRecipeDetailsUseCase fetchRecipeDetailsUseCase,
                                   ToastHelper toastHelper) {
        mScreenNavigator = screenNavigator;
        mFetchRecipeDetailsUseCase = fetchRecipeDetailsUseCase;
        mToastHelper = toastHelper;
    }

    public void bindView(RecipeDetailsViewMvc recipeDetailsViewMvc) {
        mRecipeDetailsViewMvc = recipeDetailsViewMvc;
        mRecipeDetailsViewMvc.showProgressIndication();
        mFetchRecipeDetailsUseCase.fetchRecipeDetailsAndNotify(mRecipeId);
    }

    public void onStop() {
        mFetchRecipeDetailsUseCase.unregisterListener(this);
        mRecipeDetailsViewMvc.unregisterListener(this);
    }

    public void onStart() {
        mFetchRecipeDetailsUseCase.registerListener(this);
        mRecipeDetailsViewMvc.registerListener(this);
    }

    @Override
    public void onFetchRecipeDetailsSuccess(RecipeDetailsSchema response) {
        mRecipeDetailsViewMvc.hideProgressIndication();
        mRecipeDetailsViewMvc.bindRecipe(response);
    }

    @Override
    public void onFetchRecipeDetailsFailure() {
        mToastHelper.showFetchRecipeDetailsFailureMsg();
    }

    @Override
    public void onNavigationUpClicked() {
        Log.d(TAG, "in onNavigationUpClicked");
        mScreenNavigator.navigateUp();
    }

    public void setRecipeId(long recipeId) {
        mRecipeId = recipeId;
    }
}
