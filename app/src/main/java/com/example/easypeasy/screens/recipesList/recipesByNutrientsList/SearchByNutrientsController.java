package com.example.easypeasy.screens.recipesList.recipesByNutrientsList;

import android.util.Log;

import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.recipes.FetchRecipesUseCase;
import com.example.easypeasy.screens.common.BackPressListener;
import com.example.easypeasy.screens.common.MessagesDisplayer;
import com.example.easypeasy.screens.common.ScreenNavigator;

import java.util.List;

public class SearchByNutrientsController implements SearchByNutrientsViewMvc.Listener,
        FetchRecipesUseCase.Listener, BackPressListener {

    private static final String TAG = SearchByNutrientsController.class.getSimpleName();
    private final FetchRecipesUseCase mFetchRecipesUseCase;
    private final ScreenNavigator mScreenNavigator;
    private final MessagesDisplayer mMessagesDisplayer;
    private SearchByNutrientsViewMvc mSearchByNutrientsViewMvc;

    public SearchByNutrientsController(FetchRecipesUseCase fetchRecipesUseCase, ScreenNavigator screenNavigator, MessagesDisplayer messagesDisplayer) {
        mFetchRecipesUseCase = fetchRecipesUseCase;
        mScreenNavigator = screenNavigator;
        mMessagesDisplayer = messagesDisplayer;
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
        mSearchByNutrientsViewMvc.bindRecipes(recipeData);
    }

    @Override
    public void onFetchRecipesFailure() {
        mMessagesDisplayer.showFetchRecipesFailureMsg();
    }

    @Override
    public void searchRecipes() {
        Log.d(TAG, "searchRecipes fetchMetaData is called");
        fetchRecipesData();
    }

    @Override
    public void onNavigationUpClicked() {
        onBackPress();
    }

    @Override
    public void selectByIngredientsItemClicked() {
        mScreenNavigator.toSearchByIngredients();
    }

    @Override
    public void selectSearchByNutrientsItemClicked() {
        // this is the search by nutrients screen - nothing to do in this method
    }

    @Override
    public boolean onBackPress() {
        if (mSearchByNutrientsViewMvc.isDrawerOpen()) {
            mSearchByNutrientsViewMvc.closeDrawer();
            return true;
        } else {
            return false;
        }
    }
}
