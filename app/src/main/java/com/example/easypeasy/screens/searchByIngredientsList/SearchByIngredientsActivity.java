package com.example.easypeasy.screens.searchByIngredientsList;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.easypeasy.R;
import com.example.easypeasy.RecipesInteractorInput;
import com.example.easypeasy.screens.common.SearchInput;
import com.example.easypeasy.configurators.Configurator;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.spoonacular.RecipesRequest;
import com.example.easypeasy.spoonacular.SearchIngredientsRequest;
import com.example.easypeasy.utils.Utils;

import java.util.List;

public class SearchByIngredientsActivity extends Activity implements SearchByIngredientsViewMvc.Listener, SearchInput {

    private static final String TAG = SearchByIngredientsActivity.class.getSimpleName();

    public SearchByIngredientsViewMvc mViewMvc;
    public RecipesInteractorInput output;
    int ingredientFetchDataPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(): " + getIntent());
        if (getIntent() != null && Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            handleIntent(getIntent());
        } else {
            Configurator.INSTANCE.configure(this);
        }
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mViewMvc = new SearchByIngredientsViewMvcImpl(LayoutInflater.from(this), null, searchManager.getSearchableInfo(getComponentName()));
        mViewMvc.registerListener(this);
        mViewMvc.bindIngredient(new Ingredient());
        setContentView(mViewMvc.getRootView());
    }

    private void doSearchIngredients(String ingredientName) {
        SearchIngredientsRequest ingredientsSearchRequest = new SearchIngredientsRequest();
        output.fetchIngredientsSearchData(ingredientsSearchRequest, ingredientName);
    }

    public void fetchMetaData() {
        RecipesRequest recipesRequest = new RecipesRequest(this);
        recipesRequest.isSearchByIngredients = true;
        output.fetchRecipesData(recipesRequest, mViewMvc.getIngredientList(), null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
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
    public void displayIngredientUnits(List<String> possibleUnits) {
        String[] unitAmounts = new String[possibleUnits.size()];
        for (int i = 0; i < possibleUnits.size(); i++) {
            unitAmounts[i] = possibleUnits.get(i);
        }
        mViewMvc.bindPossibleUnits(unitAmounts, ingredientFetchDataPosition);
    }

    @Override
    public void searchRecipes() {
        Utils.hideKeyboard(this);
        if (mViewMvc.getIngredientList().size() < 3) {
            Toast.makeText(SearchByIngredientsActivity.this, R.string.message_minimum_ingredients, Toast.LENGTH_LONG).show();
        } else {
            fetchMetaData();
        }
    }

    @Override
    public void displayRecipesMetaData(List<Recipe> recipes) {
        Log.d(TAG, "in displayRecipesMetaData");
        mViewMvc.bindRecipes(recipes);
    }
}