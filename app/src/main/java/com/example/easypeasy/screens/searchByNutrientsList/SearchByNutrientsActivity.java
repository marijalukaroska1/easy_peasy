package com.example.easypeasy.screens.searchByNutrientsList;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.easypeasy.common.utils.Utils;
import com.example.easypeasy.models.Nutrient;
import com.example.easypeasy.models.RecipeDetails;
import com.example.easypeasy.networking.FetchRecipesUseCase;
import com.example.easypeasy.screens.common.BaseActivity;

import java.util.List;

public class SearchByNutrientsActivity extends BaseActivity implements SearchByNutrientsViewMvc.Listener, FetchRecipesUseCase.Listener {
    private static final String TAG = SearchByNutrientsActivity.class.getSimpleName();
    SearchByNutrientsViewMvc mViewMvc;
    FetchRecipesUseCase mFetchRecipesUseCase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getCompositionRoot().getViewMvcFactory().getSearchByNutrientsViewMvc(null);
        mViewMvc.bindNutrient(new Nutrient("", 0.0f));
        mViewMvc.registerListener(this);

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
    public void onFetchRecipesSuccess(List<RecipeDetails> recipeData) {
        Utils.hideKeyboard(this);
        mViewMvc.bindRecipes(recipeData);
    }

    @Override
    public void onFetchRecipesFailure() {
        Toast.makeText(this, "Fetch Recipes Failure", Toast.LENGTH_LONG).show();
    }
}
