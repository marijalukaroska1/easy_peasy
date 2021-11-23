package com.example.easypeasy.screens.common;

import android.app.SearchableInfo;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.easypeasy.screens.categoriesList.CategoryListViewMvc;
import com.example.easypeasy.screens.categoriesList.CategoryListViewMvcImpl;
import com.example.easypeasy.screens.categoriesList.categoryListItem.CategoryListViewItemMvc;
import com.example.easypeasy.screens.categoriesList.categoryListItem.CategoryListViewItemMvcImpl;
import com.example.easypeasy.screens.common.dialogs.promptdialog.PromptViewMvc;
import com.example.easypeasy.screens.common.dialogs.promptdialog.PromptViewMvcImpl;
import com.example.easypeasy.screens.common.toolbar.ToolbarViewMvc;
import com.example.easypeasy.screens.navDrawer.NavDrawerHelper;
import com.example.easypeasy.screens.navDrawer.NavDrawerViewMvc;
import com.example.easypeasy.screens.navDrawer.NavDrawerViewMvcImpl;
import com.example.easypeasy.screens.recipeDetails.RecipeDetailsUsedIngredientsItemViewMvc;
import com.example.easypeasy.screens.recipeDetails.RecipeDetailsViewMvc;
import com.example.easypeasy.screens.recipeDetails.RecipeDetailsViewMvcImpl;
import com.example.easypeasy.screens.recipesList.common.RecipeListViewItemMvc;
import com.example.easypeasy.screens.recipesList.common.RecipeListViewItemMvcImpl;
import com.example.easypeasy.screens.recipesList.recipesByIngredientsList.SearchByIngredientsViewMvc;
import com.example.easypeasy.screens.recipesList.recipesByIngredientsList.SearchByIngredientsViewMvcImpl;
import com.example.easypeasy.screens.recipesList.recipesByIngredientsList.recipeByIngredientsListItem.SearchByIngredientsViewItemMvc;
import com.example.easypeasy.screens.recipesList.recipesByIngredientsList.recipeByIngredientsListItem.SearchByIngredientsViewItemMvcImpl;
import com.example.easypeasy.screens.recipesList.recipesByNutrientsList.SearchByNutrientsViewMvc;
import com.example.easypeasy.screens.recipesList.recipesByNutrientsList.SearchByNutrientsViewMvcImpl;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;
    private final NavDrawerHelper mNavDrawerHelper;

    public ViewMvcFactory(LayoutInflater layoutInflater, NavDrawerHelper navDrawerHelper) {
        mLayoutInflater = layoutInflater;
        mNavDrawerHelper = navDrawerHelper;
    }

    public SearchByNutrientsViewMvc getSearchByNutrientsViewMvc(@Nullable ViewGroup parent) {
        return new SearchByNutrientsViewMvcImpl(mLayoutInflater, parent, this);
    }

    public SearchByIngredientsViewMvc getSearchByIngredientsViewMvc(@Nullable ViewGroup parent, SearchableInfo searchableInfo) {
        return new SearchByIngredientsViewMvcImpl(mLayoutInflater, parent, searchableInfo, this);
    }

    public RecipeDetailsViewMvc getRecipeInformationViewMvc(@Nullable ViewGroup parent) {
        return new RecipeDetailsViewMvcImpl(mLayoutInflater, parent, this);
    }

    public SearchByIngredientsViewItemMvc getSearchByIngredientsViewItemMvc(@Nullable ViewGroup parent, SearchableInfo searchableInfo) {
        return new SearchByIngredientsViewItemMvcImpl(mLayoutInflater, parent, searchableInfo);
    }

    public CategoryListViewMvc getCategoryViewMvc(@Nullable ViewGroup parent) {
        return new CategoryListViewMvcImpl(mLayoutInflater, parent, this, mNavDrawerHelper);
    }

    public CategoryListViewItemMvc getCategoryListViewItemMvc(@Nullable ViewGroup parent) {
        return new CategoryListViewItemMvcImpl(mLayoutInflater, parent);
    }

    public RecipeDetailsUsedIngredientsItemViewMvc getRecipeInformationUsedIngredientsItemViewMvc(@Nullable ViewGroup parent) {
        return new RecipeDetailsUsedIngredientsItemViewMvc(mLayoutInflater, parent);
    }

    public ToolbarViewMvc getToolbarViewMvc(@Nullable ViewGroup parent) {
        return new ToolbarViewMvc(mLayoutInflater, parent);
    }

    public RecipeListViewItemMvc getRecipeListViewItemMvc(@Nullable ViewGroup parent) {
        return new RecipeListViewItemMvcImpl(mLayoutInflater, parent);
    }

    public NavDrawerViewMvc getNavDrawerViewMvc(@Nullable ViewGroup parent) {
        return new NavDrawerViewMvcImpl(mLayoutInflater, parent);
    }

    public PromptViewMvc getPromptViewMvc(@Nullable  ViewGroup parent) {
        return new PromptViewMvcImpl(mLayoutInflater, parent);
    }
}
