package com.example.easypeasy.screens.recipesList.recipesByIngredientsList;

import android.app.SearchManager;
import android.content.Intent;
import android.util.Log;

import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.recipes.FetchRecipesUseCase;
import com.example.easypeasy.recipes.ingredients.FetchIngredientMetaDataUseCase;
import com.example.easypeasy.recipes.ingredients.FetchIngredientsNamesUseCase;
import com.example.easypeasy.screens.common.controllers.HandleIntentDispatcher;
import com.example.easypeasy.screens.common.controllers.HandleIntentListener;
import com.example.easypeasy.screens.common.dialogs.DialogManager;
import com.example.easypeasy.screens.common.dialogs.DialogsEventBus;
import com.example.easypeasy.screens.common.dialogs.promptdialog.PromptDialogEvent;
import com.example.easypeasy.screens.common.screennavigator.ScreenNavigator;
import com.example.easypeasy.screens.common.toasthelper.ToastHelper;
import com.example.easypeasy.screens.recipesList.common.RecipeClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.easypeasy.screens.recipesList.recipesByIngredientsList.SearchByIngredientsController.ScreenState.FETCHING_RECIPES;
import static com.example.easypeasy.screens.recipesList.recipesByIngredientsList.SearchByIngredientsController.ScreenState.IDLE;
import static com.example.easypeasy.screens.recipesList.recipesByIngredientsList.SearchByIngredientsController.ScreenState.NETWORK_ERROR;
import static com.example.easypeasy.screens.recipesList.recipesByIngredientsList.SearchByIngredientsController.ScreenState.RECIPES_SHOWN;

public class SearchByIngredientsController implements SearchByIngredientsViewMvc.Listener,
        FetchRecipesUseCase.Listener, FetchIngredientsNamesUseCase.Listener,
        FetchIngredientMetaDataUseCase.Listener, RecipeClickListener, HandleIntentListener,
        DialogsEventBus.Listener {

    public enum ScreenState {
        IDLE, FETCHING_RECIPES, RECIPES_SHOWN, NETWORK_ERROR
    }

    private static final String TAG = SearchByIngredientsController.class.getSimpleName();
    private final FetchRecipesUseCase mFetchRecipesUseCase;
    private final FetchIngredientsNamesUseCase mFetchIngredientsNamesUseCase;
    private final FetchIngredientMetaDataUseCase mFetchIngredientMetaDataUseCase;
    private final ToastHelper mToastHelper;
    private final ScreenNavigator mScreenNavigator;
    private final HandleIntentDispatcher mHandleIntentDispatcher;
    private final DialogManager mDialogManager;
    private final DialogsEventBus mDialogsEventBus;
    private List<IngredientSchema> mIngredients = new ArrayList<>();
    private int mIngredientFetchDataPosition;
    private SearchByIngredientsViewMvc mSearchByIngredientsViewMvc;
    private ScreenState mScreenState = IDLE;

    public SearchByIngredientsController(FetchRecipesUseCase fetchRecipesUseCase,
                                         FetchIngredientsNamesUseCase fetchIngredientsNamesUseCase,
                                         FetchIngredientMetaDataUseCase fetchIngredientMetaDataUseCase,
                                         ToastHelper toastHelper,
                                         ScreenNavigator screenNavigator,
                                         HandleIntentDispatcher handleIntentDispatcher,
                                         DialogManager dialogManager,
                                         DialogsEventBus dialogsEventBus) {
        mFetchRecipesUseCase = fetchRecipesUseCase;
        mFetchIngredientsNamesUseCase = fetchIngredientsNamesUseCase;
        mFetchIngredientMetaDataUseCase = fetchIngredientMetaDataUseCase;
        mToastHelper = toastHelper;
        mScreenNavigator = screenNavigator;
        mHandleIntentDispatcher = handleIntentDispatcher;
        mDialogManager = dialogManager;
        mDialogsEventBus = dialogsEventBus;
    }

    public void onStart() {
        mSearchByIngredientsViewMvc.registerListener(this);
        mFetchRecipesUseCase.registerListener(this);
        mFetchIngredientsNamesUseCase.registerListener(this);
        mFetchIngredientMetaDataUseCase.registerListener(this);
        mHandleIntentDispatcher.registerListener(this);
        mDialogsEventBus.registerListener(this);
    }

    public void onStop() {
        mSearchByIngredientsViewMvc.unregisterListener(this);
        mFetchRecipesUseCase.unregisterListener(this);
        mFetchIngredientsNamesUseCase.unregisterListener(this);
        mFetchIngredientMetaDataUseCase.unregisterListener(this);
        mHandleIntentDispatcher.unregisterListener(this);
        mDialogsEventBus.unregisterListener(this);
    }

    public void fetchRecipes() {
        mScreenState = FETCHING_RECIPES;
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
        mScreenState = RECIPES_SHOWN;
        mSearchByIngredientsViewMvc.hideProgressIndication();
        mSearchByIngredientsViewMvc.bindRecipes(recipeData, this);
    }

    @Override
    public void onFetchRecipesFailure() {
        Log.d(TAG, "in onFetchRecipesFailure");
        mScreenState = NETWORK_ERROR;
        mSearchByIngredientsViewMvc.hideProgressIndication();
        mDialogManager.showRecipesFetchErrorDialog(null);
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
            if (mScreenState != NETWORK_ERROR)
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
        mSearchByIngredientsViewMvc.bindIngredients(mIngredients);
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

    public SavedState getSavedState() {
        return new SavedState(mScreenState);
    }

    public void restoreSavedState(SavedState savedState, List<IngredientSchema> ingredients) {
        mScreenState = savedState.mScreenState;
        mIngredients = ingredients;
    }

    @Override
    public void onDialogEvent(Object event) {
        PromptDialogEvent promptDialogEvent = (PromptDialogEvent) event;
        switch (promptDialogEvent.getClickedButton()) {
            case POSITIVE:
                fetchRecipes();
                break;
            case NEGATIVE:
                mScreenState = IDLE;
                break;
        }
    }

    public static class SavedState implements Serializable {
        private final ScreenState mScreenState;

        public SavedState(ScreenState screenState) {
            mScreenState = screenState;
        }
    }

    public List<IngredientSchema> getIngredientList() {
        return mIngredients;
    }
}
