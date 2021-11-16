package com.example.easypeasy.screens.recipeDetails;

import android.content.Intent;

import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.recipes.FetchRecipeDetailsUseCase;
import com.example.easypeasy.screens.common.MessagesDisplayer;
import com.example.easypeasy.screens.common.ScreenNavigator;

public class RecipeDetailsController implements FetchRecipeDetailsUseCase.Listener, RecipeDetailsViewMvc.Listener {

    private RecipeDetailsViewMvc mRecipeDetailsViewMvc;
    private final ScreenNavigator mScreenNavigator;
    private final FetchRecipeDetailsUseCase mFetchRecipeDetailsUseCase;
    private final MessagesDisplayer mMessagesDisplayer;
    long recipeId = 0L;

    public RecipeDetailsController(ScreenNavigator screenNavigator, FetchRecipeDetailsUseCase fetchRecipeDetailsUseCase, MessagesDisplayer messagesDisplayer) {
        mScreenNavigator = screenNavigator;
        mFetchRecipeDetailsUseCase = fetchRecipeDetailsUseCase;
        mMessagesDisplayer = messagesDisplayer;
    }

    public void bindView(RecipeDetailsViewMvc recipeDetailsViewMvc) {
        mRecipeDetailsViewMvc = recipeDetailsViewMvc;
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
        mMessagesDisplayer.showFetchRecipeDetailsFailureMsg();
    }

    @Override
    public void onNavigationUpClicked() {
        onBackPressed();
    }

    @Override
    public void selectSearchByIngredientsItemClicked() {
        mScreenNavigator.toSearchByIngredients();
    }

    @Override
    public void selectSearchByNutrientsItemClicked() {
        mScreenNavigator.toSearchByNutrients();
    }


    public boolean onBackPressed() {
        if (mRecipeDetailsViewMvc.isDrawerOpen()) {
            mRecipeDetailsViewMvc.closeDrawer();
            return true;
        } else {
            return false;
        }
    }

    public void handleIntent(Intent intent) {
        recipeId = intent.getLongExtra("recipeId", 0L);
        mRecipeDetailsViewMvc.showProgressIndication();
        mFetchRecipeDetailsUseCase.fetchRecipeDetailsAndNotify(recipeId);
    }
}
