package com.example.easypeasy.common.dependancyInjection;

import android.app.SearchManager;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.easypeasy.networking.SpoonacularApi;
import com.example.easypeasy.recipes.FetchRecipeDetailsUseCase;
import com.example.easypeasy.recipes.FetchRecipesUseCase;
import com.example.easypeasy.recipes.ingredients.FetchIngredientMetaDataUseCase;
import com.example.easypeasy.recipes.ingredients.FetchIngredientsNamesUseCase;
import com.example.easypeasy.screens.categoriesList.CategoryListController;
import com.example.easypeasy.screens.common.HandleIntentDispatcher;
import com.example.easypeasy.screens.common.ScreenNavigator;
import com.example.easypeasy.screens.common.ToastHelper;
import com.example.easypeasy.screens.common.ViewMvcFactory;
import com.example.easypeasy.screens.common.fragmentframehelper.FragmentContainerWrapper;
import com.example.easypeasy.screens.common.fragmentframehelper.FragmentHelper;
import com.example.easypeasy.screens.navDrawer.NavDrawerHelper;
import com.example.easypeasy.screens.recipeDetails.RecipeDetailsController;
import com.example.easypeasy.screens.recipesList.recipesByIngredientsList.SearchByIngredientsController;
import com.example.easypeasy.screens.recipesList.recipesByNutrientsList.SearchByNutrientsController;

public class ControllerCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final FragmentActivity mActivity;

    public ControllerCompositionRoot(CompositionRoot mCompositionRoot, FragmentActivity activity) {
        this.mCompositionRoot = mCompositionRoot;
        mActivity = activity;
    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(getLayoutInflater(), getNavDrawerHelper());
    }

    private NavDrawerHelper getNavDrawerHelper() {
        return (NavDrawerHelper) getActivity();
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

    private FragmentActivity getActivity() {
        return mActivity;
    }

    private FragmentManager getFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    private FragmentContainerWrapper getFragmentFrameWrapper() {
        return (FragmentContainerWrapper) getActivity();
    }

    private HandleIntentDispatcher getHandleIntentDispatcher() {
        return (HandleIntentDispatcher) getActivity();
    }

    private FragmentHelper getFragmentHelper() {
        return new FragmentHelper(getActivity(), getFragmentFrameWrapper(), getFragmentManager());
    }

    public ScreenNavigator getScreenNavigator() {
        return new ScreenNavigator(getFragmentHelper(), getActivity());
    }

    private ToastHelper getToastHelper() {
        return new ToastHelper(getContext());
    }

    public RecipeDetailsController getRecipeDetailsController() {
        return new RecipeDetailsController(getScreenNavigator(), getFetchRecipeDetailsUseCase(), getToastHelper());
    }

    public SearchManager getSearchManager() {
        return (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
    }

    public SearchByIngredientsController getSearchByIngredientsController() {
        return new SearchByIngredientsController(getFetchRecipesUseCase(), getFetchIngredientsNamesUseCase(),
                getFetchIngredientMetaDataUseCase(),
                getToastHelper(), getScreenNavigator(), getHandleIntentDispatcher());
    }

    public SearchByNutrientsController getSearchByNutrientsController() {
        return new SearchByNutrientsController(getFetchRecipesUseCase(), getScreenNavigator(),
                getToastHelper());
    }
}
