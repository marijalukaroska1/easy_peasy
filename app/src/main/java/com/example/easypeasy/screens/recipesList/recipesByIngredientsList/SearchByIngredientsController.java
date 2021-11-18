package com.example.easypeasy.screens.recipesList.recipesByIngredientsList;

import android.app.SearchManager;
import android.content.Intent;
import android.util.Log;

import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.recipes.FetchRecipesUseCase;
import com.example.easypeasy.recipes.ingredients.FetchIngredientMetaDataUseCase;
import com.example.easypeasy.recipes.ingredients.FetchIngredientsNamesUseCase;
import com.example.easypeasy.screens.common.HandleIntentDispatcher;
import com.example.easypeasy.screens.common.HandleIntentListener;
import com.example.easypeasy.screens.common.ScreenNavigator;
import com.example.easypeasy.screens.common.ToastHelper;
import com.example.easypeasy.screens.recipesList.common.RecipeClickListener;

import java.util.List;

public class SearchByIngredientsController implements SearchByIngredientsViewMvc.Listener,
        FetchRecipesUseCase.Listener, FetchIngredientsNamesUseCase.Listener,
        FetchIngredientMetaDataUseCase.Listener, RecipeClickListener, HandleIntentListener {

    private static final String TAG = SearchByIngredientsController.class.getSimpleName();
    private final FetchRecipesUseCase mFetchRecipesUseCase;
    private final FetchIngredientsNamesUseCase mFetchIngredientsNamesUseCase;
    private final FetchIngredientMetaDataUseCase mFetchIngredientMetaDataUseCase;
    private final ToastHelper mToastHelper;
    private final ScreenNavigator mScreenNavigator;
    private final HandleIntentDispatcher mHandleIntentDispatcher;
    private int mIngredientFetchDataPosition;
    private SearchByIngredientsViewMvc mSearchByIngredientsViewMvc;

    public SearchByIngredientsController(FetchRecipesUseCase fetchRecipesUseCase, FetchIngredientsNamesUseCase fetchIngredientsNamesUseCase,
                                         FetchIngredientMetaDataUseCase fetchIngredientMetaDataUseCase, ToastHelper toastHelper,
                                         ScreenNavigator screenNavigator, HandleIntentDispatcher handleIntentDispatcher) {
        mFetchRecipesUseCase = fetchRecipesUseCase;
        mFetchIngredientsNamesUseCase = fetchIngredientsNamesUseCase;
        mFetchIngredientMetaDataUseCase = fetchIngredientMetaDataUseCase;
        mToastHelper = toastHelper;
        mScreenNavigator = screenNavigator;
        mHandleIntentDispatcher = handleIntentDispatcher;
    }

    public void onStart() {
        mSearchByIngredientsViewMvc.registerListener(this);
        mFetchRecipesUseCase.registerListener(this);
        mFetchIngredientsNamesUseCase.registerListener(this);
        mFetchIngredientMetaDataUseCase.registerListener(this);
        mHandleIntentDispatcher.registerListener(this);
    }

    public void onStop() {
        mSearchByIngredientsViewMvc.unregisterListener(this);
        mFetchRecipesUseCase.unregisterListener(this);
        mFetchIngredientsNamesUseCase.unregisterListener(this);
        mFetchIngredientMetaDataUseCase.unregisterListener(this);
        mHandleIntentDispatcher.unregisterListener(this);
    }

    public void fetchRecipes() {
        mSearchByIngredientsViewMvc.showProgressIndication();
        mFetchRecipesUseCase.isSearchByIngredients = true;
        mFetchRecipesUseCase.fetchRecipesByIngredientsAndNotify(mSearchByIngredientsViewMvc.getIngredientList());
    }

    public void doSearchIngredients(String ingredientName) {
        mFetchIngredientsNamesUseCase.fetchIngredientsSearchMetaDataAndNotify(ingredientName);
    }

    @Override
    public void onFetchRecipesSuccess(List<RecipeDetailsSchema> recipeData) {
        Log.d(TAG, "in onRecipesFetchedSuccess");
        mSearchByIngredientsViewMvc.hideProgressIndication();
        mSearchByIngredientsViewMvc.bindRecipes(recipeData, this);
    }

    @Override
    public void onFetchRecipesFailure() {
        mToastHelper.showFetchRecipesFailureMsg();
    }


    @Override
    public void onFetchIngredientMetaDataSuccess(IngredientSchema ingredient) {
        String[] unitAmounts = new String[ingredient.getPossibleUnits().size()];
        for (int i = 0; i < ingredient.getPossibleUnits().size(); i++) {
            unitAmounts[i] = ingredient.getPossibleUnits().get(i);
        }
        mSearchByIngredientsViewMvc.bindIngredientPossibleUnits(unitAmounts, mIngredientFetchDataPosition);
    }

    @Override
    public void onFetchIngredientMetaDataFailure() {
        mToastHelper.showFetchIngredientMetaDataFailure();
    }

    @Override
    public void onFetchIngredientSearchMetaDataSuccess(long ingredientId) {
        mFetchIngredientMetaDataUseCase.fetchIngredientMetaDataAndNotify(ingredientId);
    }

    @Override
    public void onFetchIngredientSearchMetaDataFailure() {
        mToastHelper.showFetchIngredientSearchMetaDataFailure();
    }

    @Override
    public void searchRecipesButtonClicked() {
        mScreenNavigator.hideKeyboardOnCurrentScreen();
        if (mSearchByIngredientsViewMvc.getIngredientList().size() < 3) {
            mToastHelper.showMinimumIngredientsMesssage();
        } else {
            fetchRecipes();
        }
    }

    @Override
    public void onNavigationUpClicked() {
        Log.d(TAG, "in onNavigationUpClicked");
        mScreenNavigator.navigateUp();
    }


    public void bindView(SearchByIngredientsViewMvc searchByIngredientsViewMvc) {
        mSearchByIngredientsViewMvc = searchByIngredientsViewMvc;
    }

    public void setIngredientFetchDataPosition(int ingredientPositionInAdapter) {
        mIngredientFetchDataPosition = ingredientPositionInAdapter;
    }

    @Override
    public void onRecipeClicked(long recipeId) {
        Log.d(TAG, "onRecipeClicked()");
        mScreenNavigator.toRecipeDetails(recipeId);
    }

    @Override
    public void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            setIngredientFetchDataPosition(intent.getIntExtra("ingredientPositionInAdapter", 0));
            Log.d(TAG, "doSearchIngredients() is called: " + query);
            doSearchIngredients(query);
        }
    }
}
