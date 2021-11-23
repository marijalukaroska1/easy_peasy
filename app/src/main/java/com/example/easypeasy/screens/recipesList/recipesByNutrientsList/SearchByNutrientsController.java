package com.example.easypeasy.screens.recipesList.recipesByNutrientsList;

import android.util.Log;

import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.recipes.FetchRecipesUseCase;
import com.example.easypeasy.screens.common.dialogs.DialogManager;
import com.example.easypeasy.screens.common.dialogs.DialogsEventBus;
import com.example.easypeasy.screens.common.dialogs.promptdialog.PromptDialogEvent;
import com.example.easypeasy.screens.common.screennavigator.ScreenNavigator;
import com.example.easypeasy.screens.common.toasthelper.ToastHelper;
import com.example.easypeasy.screens.recipesList.common.RecipeClickListener;

import java.io.Serializable;
import java.util.List;

import static com.example.easypeasy.screens.recipesList.recipesByNutrientsList.SearchByNutrientsController.ScreenState.FETCHING_RECIPES;
import static com.example.easypeasy.screens.recipesList.recipesByNutrientsList.SearchByNutrientsController.ScreenState.IDLE;
import static com.example.easypeasy.screens.recipesList.recipesByNutrientsList.SearchByNutrientsController.ScreenState.NETWORK_ERROR;
import static com.example.easypeasy.screens.recipesList.recipesByNutrientsList.SearchByNutrientsController.ScreenState.RECIPES_SHOWN;

public class SearchByNutrientsController implements SearchByNutrientsViewMvc.Listener,
        FetchRecipesUseCase.Listener, RecipeClickListener, DialogsEventBus.Listener {

    public enum ScreenState {
        IDLE, FETCHING_RECIPES, RECIPES_SHOWN, NETWORK_ERROR
    }

    private static final String TAG = SearchByNutrientsController.class.getSimpleName();
    private final FetchRecipesUseCase mFetchRecipesUseCase;
    private final ScreenNavigator mScreenNavigator;
    private final ToastHelper mToastHelper;
    private SearchByNutrientsViewMvc mSearchByNutrientsViewMvc;
    private DialogManager mDialogManager;
    private DialogsEventBus mDialogEventBus;
    private ScreenState mScreenState = IDLE;

    public SearchByNutrientsController(FetchRecipesUseCase fetchRecipesUseCase, ScreenNavigator screenNavigator,
                                       ToastHelper toastHelper, DialogManager dialogManager, DialogsEventBus dialogsEventBus) {
        mFetchRecipesUseCase = fetchRecipesUseCase;
        mScreenNavigator = screenNavigator;
        mToastHelper = toastHelper;
        mDialogManager = dialogManager;
        mDialogEventBus = dialogsEventBus;
    }

    public SavedState getSavedState() {
        return new SavedState(mScreenState);
    }

    public void restoreSavedState(SavedState savedState) {
        mScreenState = savedState.mScreenState;
    }

    public void bindView(SearchByNutrientsViewMvc searchByNutrientsViewMvc) {
        mSearchByNutrientsViewMvc = searchByNutrientsViewMvc;
    }

    public void onStop() {
        Log.d(TAG, "in onStop");
        mSearchByNutrientsViewMvc.unregisterListener(this);
        mFetchRecipesUseCase.unregisterListener(this);
        mDialogEventBus.unregisterListener(this);
    }

    public void onStart() {
        Log.d(TAG, "in onStart");
        mSearchByNutrientsViewMvc.registerListener(this);
        mFetchRecipesUseCase.registerListener(this);
        mDialogEventBus.registerListener(this);
    }

    public void fetchRecipesData() {
        mScreenState = FETCHING_RECIPES;
        mFetchRecipesUseCase.isSearchByIngredients = false;
        mFetchRecipesUseCase.fetchRecipesByNutrientsAndNotify(mSearchByNutrientsViewMvc.getNutrients());
    }

    @Override
    public void onFetchRecipesSuccess(List<RecipeDetailsSchema> recipeData) {
        mScreenState = RECIPES_SHOWN;
        Log.d(TAG, "onFetchRecipesSuccess: " + recipeData.size());
        mScreenNavigator.hideKeyboardOnCurrentScreen();
        mSearchByNutrientsViewMvc.bindRecipes(recipeData, this);
    }

    @Override
    public void onFetchRecipesFailure() {
        mScreenState = NETWORK_ERROR;
        Log.d(TAG, "onFetchRecipesFailure");
        mScreenNavigator.hideKeyboardOnCurrentScreen();
        mDialogManager.showRecipesFetchErrorDialog(null);
    }

    @Override
    public void searchRecipes() {
        Log.d(TAG, "searchRecipes fetchMetaData is called");
        if (mScreenState != NETWORK_ERROR) {
            fetchRecipesData();
        }
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

    @Override
    public void onDialogEvent(Object event) {
        PromptDialogEvent promptDialogEvent = (PromptDialogEvent) event;
        Log.d(TAG, "onDialogEvent: " + promptDialogEvent.getClickedButton());
        switch ((promptDialogEvent).getClickedButton()) {
            case NEGATIVE:
                mScreenState = IDLE;
                break;
            case POSITIVE:
                fetchRecipesData();
                break;
        }
    }

    public static class SavedState implements Serializable {
        private final ScreenState mScreenState;

        public SavedState(ScreenState screenState) {
            mScreenState = screenState;
        }
    }
}
