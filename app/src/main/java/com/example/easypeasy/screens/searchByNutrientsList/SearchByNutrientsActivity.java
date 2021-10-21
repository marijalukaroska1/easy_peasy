package com.example.easypeasy.screens.searchByNutrientsList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.example.easypeasy.RecipesInteractorInput;
import com.example.easypeasy.configurators.Configurator;
import com.example.easypeasy.models.Nutrient;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.screens.common.SearchInput;
import com.example.easypeasy.spoonacular.RecipesRequest;
import com.example.easypeasy.utils.Utils;

import java.util.List;

public class SearchByNutrientsActivity extends Activity implements SearchByNutrientsViewMvc.Listener, SearchInput {
    private static final String TAG = SearchByNutrientsActivity.class.getSimpleName();

    public RecipesInteractorInput output;

    SearchByNutrientsViewMvc mViewMvc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configurator.INSTANCE.configure(this);
        mViewMvc = new SearchByNutrientsViewMvcImpl(LayoutInflater.from(this), null);
        setContentView(mViewMvc.getRootView());
        mViewMvc.bindNutrient(new Nutrient("", 0.0f));
    }


    public void fetchMetaData() {
        RecipesRequest recipesRequest = new RecipesRequest(this);
        recipesRequest.isSearchByIngredients = false;
        output.fetchRecipesData(recipesRequest, null, mViewMvc.getNutrients());
    }


    @Override
    public void displayRecipesMetaData(List<Recipe> recipeList) {
        Utils.hideKeyboard(this);
        mViewMvc.bindRecipes(recipeList);
    }

    @Override
    public void displayIngredientUnits(List<String> possibleUnits) {

    }

    @Override
    public void searchRecipes() {
        Log.d(TAG, "searchRecipes fetchMetaData is called");
        fetchMetaData();
    }
}
