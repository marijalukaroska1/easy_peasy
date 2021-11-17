package com.example.easypeasy.common.dependancyInjection;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.view.LayoutInflater;

import com.example.easypeasy.networking.SpoonacularApi;
import com.example.easypeasy.recipes.FetchRecipeDetailsUseCase;
import com.example.easypeasy.recipes.FetchRecipesUseCase;
import com.example.easypeasy.recipes.ingredients.FetchIngredientMetaDataUseCase;
import com.example.easypeasy.recipes.ingredients.FetchIngredientsNamesUseCase;
import com.example.easypeasy.screens.categoriesList.CategoryListController;
import com.example.easypeasy.screens.common.MessagesDisplayer;
import com.example.easypeasy.screens.common.ScreenNavigator;
import com.example.easypeasy.screens.common.ViewMvcFactory;
import com.example.easypeasy.screens.recipeDetails.RecipeDetailsController;
import com.example.easypeasy.screens.recipesList.recipesByIngredientsList.SearchByIngredientsController;
import com.example.easypeasy.screens.recipesList.recipesByNutrientsList.SearchByNutrientsController;

public class ControllerCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final Activity mActivity;

    public ControllerCompositionRoot(CompositionRoot mCompositionRoot, Activity activity) {
        this.mCompositionRoot = mCompositionRoot;
        mActivity = activity;
    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(getLayoutInflater());
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    private SpoonacularApi getSpoonacularApi() {
        return mCompositionRoot.getSpoonacularRecipesApi();
    }

    public FetchRecipesUseCase getFetchRecipesUseCase() {
        return new FetchRecipesUseCase(getSpoonacularApi());
    }

    public FetchIngredientsNamesUseCase getFetchIngredientsNamesUseCase() {
        return new FetchIngredientsNamesUseCase(getSpoonacularApi());
    }

    public FetchIngredientMetaDataUseCase getFetchIngredientMetaDataUseCase() {
        return new FetchIngredientMetaDataUseCase(getSpoonacularApi());
    }

    public FetchRecipeDetailsUseCase getFetchRecipeDetailsUseCase() {
        return new FetchRecipeDetailsUseCase(getSpoonacularApi());
    }

    public CategoryListController getCategoryListController() {
        return new CategoryListController(getScreenNavigator());
    }

    private Context getContext() {
        return mActivity;
    }

    private Activity getActivity() {
        return mActivity;
    }

    private ScreenNavigator getScreenNavigator() {
        return new ScreenNavigator(getContext(), getActivity());
    }

    private MessagesDisplayer getMessageDisplayer() {
        return new MessagesDisplayer(getContext());
    }

    public RecipeDetailsController getRecipeDetailsController() {
        return new RecipeDetailsController(getScreenNavigator(), getFetchRecipeDetailsUseCase(), getMessageDisplayer());
    }

    public SearchManager getSearchManager() {
        return (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
    }

    public SearchByIngredientsController getSearchByIngredientsController() {
        return new SearchByIngredientsController(getFetchRecipesUseCase(), getFetchIngredientsNamesUseCase(), getFetchIngredientMetaDataUseCase(), getMessageDisplayer(), getScreenNavigator());
    }

    public SearchByNutrientsController getSearchByNutrientsController() {
        return new SearchByNutrientsController(getFetchRecipesUseCase(), getScreenNavigator(), getMessageDisplayer());
    }
}
