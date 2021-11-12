package com.example.easypeasy.screens.recipesList.recipesByNutrientsList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.easypeasy.common.utils.Utils;
import com.example.easypeasy.networking.nutrients.NutrientSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.recipes.FetchRecipesUseCase;
import com.example.easypeasy.screens.common.BaseActivity;
import com.example.easypeasy.screens.recipesList.recipesByIngredientsList.SearchByIngredientsActivity;

import java.util.List;

public class SearchByNutrientsActivity extends BaseActivity
        implements SearchByNutrientsViewMvc.Listener, FetchRecipesUseCase.Listener {
    private static final String TAG = SearchByNutrientsActivity.class.getSimpleName();
    SearchByNutrientsViewMvc mViewMvc;
    FetchRecipesUseCase mFetchRecipesUseCase;

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "in onStop");
        mViewMvc.unregisterListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "in onStart");
        mViewMvc.registerListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "in onCreate");
        mViewMvc = getCompositionRoot().getViewMvcFactory().getSearchByNutrientsViewMvc(null);
        mViewMvc.bindNutrient(new NutrientSchema("", 0.0f));

        mFetchRecipesUseCase = getCompositionRoot().getFetchRecipesUseCase();
        mFetchRecipesUseCase.registerListener(this);
        setContentView(mViewMvc.getRootView());
    }

    public void fetchRecipesData() {
        mFetchRecipesUseCase.isSearchByIngredients = false;
        mFetchRecipesUseCase.fetchRecipesByNutrientsAndNotify(mViewMvc.getNutrients());
    }

    @Override
    public void searchRecipes() {
        Log.d(TAG, "searchRecipes fetchMetaData is called");
        fetchRecipesData();
    }

    @Override
    public void onNavigationUpClicked() {
        onBackPressed();
    }



    @Override
    public void selectByIngredientsItemClicked() {
        finish();
        startActivity(new Intent(this, SearchByIngredientsActivity.class));
    }

    @Override
    public void selectSearchByNutrientsItemClicked() {
        // this is the search by nutrients screen - nothing to do in this method
    }

    @Override
    public void onFetchRecipesSuccess(List<RecipeDetailsSchema> recipeData) {
        Utils.hideKeyboard(this);
        mViewMvc.bindRecipes(recipeData);
    }

    @Override
    public void onFetchRecipesFailure() {
        Toast.makeText(this, "Fetch Recipes Failure", Toast.LENGTH_LONG).show();
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
