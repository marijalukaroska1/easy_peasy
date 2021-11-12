package com.example.easypeasy.screens.recipesList.recipesByIngredientsList;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.easypeasy.R;
import com.example.easypeasy.common.utils.Utils;
import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.recipes.ingredients.FetchIngredientMetaDataUseCase;
import com.example.easypeasy.recipes.ingredients.FetchIngredientsNamesUseCase;
import com.example.easypeasy.recipes.FetchRecipesUseCase;
import com.example.easypeasy.screens.common.BaseActivity;
import com.example.easypeasy.screens.recipesList.recipesByNutrientsList.SearchByNutrientsActivity;

import java.util.List;

public class SearchByIngredientsActivity extends BaseActivity
        implements SearchByIngredientsViewMvc.Listener, FetchRecipesUseCase.Listener, FetchIngredientsNamesUseCase.Listener,
        FetchIngredientMetaDataUseCase.Listener {

    private static final String TAG = SearchByIngredientsActivity.class.getSimpleName();
    public SearchByIngredientsViewMvc mViewMvc;
    public int ingredientFetchDataPosition;
    private FetchRecipesUseCase mFetchRecipesUseCase;
    private FetchIngredientsNamesUseCase mFetchIngredientsNamesUseCase;
    private FetchIngredientMetaDataUseCase mFetchIngredientMetaDataUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(): " + getIntent());
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mViewMvc = getCompositionRoot().getViewMvcFactory().getSearchByIngredientsViewMvc(null, searchManager.getSearchableInfo(getComponentName()));
        mViewMvc.bindIngredient(new IngredientSchema());
        mFetchRecipesUseCase = getCompositionRoot().getFetchRecipesUseCase();
        mFetchIngredientsNamesUseCase = getCompositionRoot().getFetchIngredientsNamesUseCase();
        mFetchIngredientMetaDataUseCase = getCompositionRoot().getFetchIngredientMetaDataUseCase();
        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "in onStart");
        mViewMvc.registerListener(this);
        mFetchRecipesUseCase.registerListener(this);
        mFetchIngredientsNamesUseCase.registerListener(this);
        mFetchIngredientMetaDataUseCase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "in onStop");
        mViewMvc.unregisterListener(this);
        mFetchRecipesUseCase.unregisterListener(this);
        mFetchIngredientsNamesUseCase.unregisterListener(this);
        mFetchIngredientMetaDataUseCase.unregisterListener(this);
    }

    private void doSearchIngredients(String ingredientName) {
        mFetchIngredientsNamesUseCase.fetchIngredientsSearchMetaDataAndNotify(ingredientName);
    }

    public void fetchRecipes() {
        mViewMvc.showProgressIndication();
        mFetchRecipesUseCase.isSearchByIngredients = true;
        mFetchRecipesUseCase.fetchRecipesByIngredientsAndNotify(mViewMvc.getIngredientList());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            ingredientFetchDataPosition = intent.getIntExtra("ingredientPositionInAdapter", 0);
            Log.d(TAG, "doSearchIngredients() is called: " + query);
            doSearchIngredients(query);
        }
    }


    @Override
    public void searchRecipesButtonClicked() {
        Utils.hideKeyboard(this);
        if (mViewMvc.getIngredientList().size() < 3) {
            Toast.makeText(SearchByIngredientsActivity.this, R.string.message_minimum_ingredients, Toast.LENGTH_LONG).show();
        } else {
            fetchRecipes();
        }
    }

    @Override
    public void onNavigationUpClicked() {
        Log.d(TAG, "in onNavigationUpClicked");
        onBackPressed();
    }

    @Override
    public void selectSearchByIngredientsItemClicked() {
        //This is the select by ingredients screen - nothing to do in this method
    }

    @Override
    public void selectSearchByNutrientsItemClicked() {
        finish();
        startActivity(new Intent(this, SearchByNutrientsActivity.class));
    }

    @Override
    public void onFetchRecipesSuccess(List<RecipeDetailsSchema> recipeData) {
        Log.d(TAG, "in onRecipesFetchedSuccess");
        mViewMvc.hideProgressIndication();
        mViewMvc.bindRecipes(recipeData);
    }

    @Override
    public void onFetchRecipesFailure() {
        Toast.makeText(this, "Fetch Recipes Failure", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFetchIngredientSearchMetaDataSuccess(long ingredientId) {
        mFetchIngredientMetaDataUseCase.fetchIngredientMetaDataAndNotify(ingredientId);
    }

    @Override
    public void onFetchIngredientSearchMetaDataFailure() {
        Toast.makeText(this, "Fetch Ingredient Names Failure", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFetchIngredientMetaDataSuccess(IngredientSchema ingredient) {
        String[] unitAmounts = new String[ingredient.getPossibleUnits().size()];
        for (int i = 0; i < ingredient.getPossibleUnits().size(); i++) {
            unitAmounts[i] = ingredient.getPossibleUnits().get(i);
        }
        mViewMvc.bindIngredientPossibleUnits(unitAmounts, ingredientFetchDataPosition);
    }

    @Override
    public void onFetchIngredientMetaDataFailure() {
        Toast.makeText(this, "Fetch Ingredient Names Failure", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        if (mViewMvc.isDrawerOpen()) {
            mViewMvc.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}