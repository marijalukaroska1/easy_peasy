package com.example.easypeasy.screens.recipesList.recipesByIngredientsList;

import android.util.Log;

import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.recipes.FetchRecipesUseCase;
import com.example.easypeasy.recipes.ingredients.FetchIngredientMetaDataUseCase;
import com.example.easypeasy.recipes.ingredients.FetchIngredientsNamesUseCase;
import com.example.easypeasy.screens.common.MessagesDisplayer;
import com.example.easypeasy.screens.common.ScreenNavigator;

import java.util.List;

public class SearchByIngredientsController implements SearchByIngredientsViewMvc.Listener, FetchRecipesUseCase.Listener, FetchIngredientsNamesUseCase.Listener,
        FetchIngredientMetaDataUseCase.Listener {

    private static final String TAG = SearchByIngredientsController.class.getSimpleName();
    private final FetchRecipesUseCase mFetchRecipesUseCase;
    private final FetchIngredientsNamesUseCase mFetchIngredientsNamesUseCase;
    private final FetchIngredientMetaDataUseCase mFetchIngredientMetaDataUseCase;
    private final MessagesDisplayer mMessagesDisplayer;
    private final ScreenNavigator mScreenNavigator;
    private int mIngredientFetchDataPosition;
    private SearchByIngredientsViewMvc mSearchByIngredientsViewMvc;

    public SearchByIngredientsController(FetchRecipesUseCase fetchRecipesUseCase, FetchIngredientsNamesUseCase fetchIngredientsNamesUseCase, FetchIngredientMetaDataUseCase fetchIngredientMetaDataUseCase, MessagesDisplayer messagesDisplayer, ScreenNavigator screenNavigator) {
        mFetchRecipesUseCase = fetchRecipesUseCase;
        mFetchIngredientsNamesUseCase = fetchIngredientsNamesUseCase;
        mFetchIngredientMetaDataUseCase = fetchIngredientMetaDataUseCase;
        mMessagesDisplayer = messagesDisplayer;
        mScreenNavigator = screenNavigator;
    }

    public void onStart() {
        mSearchByIngredientsViewMvc.registerListener(this);
        mFetchRecipesUseCase.registerListener(this);
        mFetchIngredientsNamesUseCase.registerListener(this);
        mFetchIngredientMetaDataUseCase.registerListener(this);
    }

    public void onStop() {
        mSearchByIngredientsViewMvc.unregisterListener(this);
        mFetchRecipesUseCase.unregisterListener(this);
        mFetchIngredientsNamesUseCase.unregisterListener(this);
        mFetchIngredientMetaDataUseCase.unregisterListener(this);
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
        mSearchByIngredientsViewMvc.bindRecipes(recipeData);
    }

    @Override
    public void onFetchRecipesFailure() {
        mMessagesDisplayer.showFetchRecipesFailureMsg();
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
        mMessagesDisplayer.showFetchIngredientMetaDataFailure();
    }

    @Override
    public void onFetchIngredientSearchMetaDataSuccess(long ingredientId) {
        mFetchIngredientMetaDataUseCase.fetchIngredientMetaDataAndNotify(ingredientId);
    }

    @Override
    public void onFetchIngredientSearchMetaDataFailure() {
        mMessagesDisplayer.showFetchIngredientSearchMetaDataFailure();
    }

    @Override
    public void searchRecipesButtonClicked() {
        mScreenNavigator.hideKeyboardOnCurrentScreen();
        if (mSearchByIngredientsViewMvc.getIngredientList().size() < 3) {
            mMessagesDisplayer.showMinimumIngredientsMesssage();
        } else {
            fetchRecipes();
        }
    }

    @Override
    public void onNavigationUpClicked() {
        Log.d(TAG, "in onNavigationUpClicked");
        onBackPress();
    }

    @Override
    public void selectSearchByIngredientsItemClicked() {
        //This is the select by ingredients screen - nothing to do in this method
        Log.d(TAG, "selectSearchByIngredientsItemClicked");
    }

    @Override
    public void selectSearchByNutrientsItemClicked() {
        Log.d(TAG, "selectSearchByNutrientsItemClicked");
        mScreenNavigator.toSearchByNutrients();
    }

    public boolean onBackPress() {
        if (mSearchByIngredientsViewMvc.isDrawerOpen()) {
            mSearchByIngredientsViewMvc.closeDrawer();
            return true;
        } else {
            return false;
        }
    }

    public void bindView(SearchByIngredientsViewMvc searchByIngredientsViewMvc) {
        mSearchByIngredientsViewMvc = searchByIngredientsViewMvc;
    }

    public void setIngredientFetchDataPosition(int ingredientPositionInAdapter) {
        mIngredientFetchDataPosition = ingredientPositionInAdapter;
    }
}
