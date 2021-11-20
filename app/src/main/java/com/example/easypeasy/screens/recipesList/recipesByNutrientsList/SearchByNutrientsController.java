package com.example.easypeasy.screens.recipesList.recipesByNutrientsList;

import android.util.Log;

import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.recipes.FetchRecipesUseCase;
import com.example.easypeasy.screens.common.dialogs.DialogManager;
import com.example.easypeasy.screens.common.screennavigator.ScreenNavigator;
import com.example.easypeasy.screens.common.toasthelper.ToastHelper;
import com.example.easypeasy.screens.recipesList.common.RecipeClickListener;

import java.util.List;

public class SearchByNutrientsController implements SearchByNutrientsViewMvc.Listener,
        FetchRecipesUseCase.Listener, RecipeClickListener {

    private static final String TAG = SearchByNutrientsController.class.getSimpleName();
    private final FetchRecipesUseCase mFetchRecipesUseCase;
    private final ScreenNavigator mScreenNavigator;
    private final ToastHelper mToastHelper;
    private SearchByNutrientsViewMvc mSearchByNutrientsViewMvc;
    private DialogManager mDialogManager;

    public SearchByNutrientsController(FetchRecipesUseCase fetchRecipesUseCase, ScreenNavigator screenNavigator,
                                       ToastHelper toastHelper, DialogManager dialogManager) {
        mFetchRecipesUseCase = fetchRecipesUseCase;
        mScreenNavigator = screenNavigator;
        mToastHelper = toastHelper;
        mDialogManager = dialogManager;
    }

    public void bindView(SearchByNutrientsViewMvc searchByNutrientsViewMvc) {
        mSearchByNutrientsViewMvc = searchByNutrientsViewMvc;
    }

    public void onStop() {
        Log.d(TAG, "in onStop");
        mSearchByNutrientsViewMvc.unregisterListener(this);
        mFetchRecipesUseCase.unregisterListener(this);
    }

    public void onStart() {
        Log.d(TAG, "in onStart");
        mSearchByNutrientsViewMvc.registerListener(this);
        mFetchRecipesUseCase.registerListener(this);
    }

    public void fetchRecipesData() {
        mFetchRecipesUseCase.isSearchByIngredients = false;
        mFetchRecipesUseCase.fetchRecipesByNutrientsAndNotify(mSearchByNutrientsViewMvc.getNutrients());
    }

    @Override
    public void onFetchRecipesSuccess(List<RecipeDetailsSchema> recipeData) {
        Log.d(TAG, "onFetchRecipesSuccess: " + recipeData.size());
        mScreenNavigator.hideKeyboardOnCurrentScreen();
        mSearchByNutrientsViewMvc.bindRecipes(recipeData, this);
    }

    @Override
    public void onFetchRecipesFailure() {
        Log.d(TAG, "onFetchRecipesFailure");
        mScreenNavigator.hideKeyboardOnCurrentScreen();
        //mToastHelper.showFetchRecipesFailureMsg();
        mDialogManager.showRecipesFetchErrorDialog(null);
    }

    @Override
    public void searchRecipes() {
        Log.d(TAG, "searchRecipes fetchMetaData is called");
        fetchRecipesData();
    }

    @Override
    public void onNavigationUpClicked() {
        Log.d(TAG, "in onNavigationUpClicked");
        mScreenNavigator.navigateUp();
    }

    @Override
    public void onRecipeClicked(long recipeId) {
        Log.d(TAG, "onRecipeClicked() recipeId: " + recipeId);
        mScreenNavigator.toRecipeDetails(recipeId);
    }
}
