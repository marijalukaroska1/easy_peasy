package com.example.easypeasy.screens.common;

import android.app.SearchableInfo;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.easypeasy.screens.categoriesList.CategoryListViewItemMvc;
import com.example.easypeasy.screens.categoriesList.CategoryListViewItemMvcImpl;
import com.example.easypeasy.screens.categoriesList.CategoryListViewMvc;
import com.example.easypeasy.screens.categoriesList.CategoryListViewMvcImpl;
import com.example.easypeasy.screens.recipeInformation.RecipeInformationUsedIngredientsItemViewMvc;
import com.example.easypeasy.screens.recipeInformation.RecipeInformationViewMvc;
import com.example.easypeasy.screens.recipeInformation.RecipeInformationViewMvcImpl;
import com.example.easypeasy.screens.searchByIngredientsList.SearchByIngredientsViewItemMvc;
import com.example.easypeasy.screens.searchByIngredientsList.SearchByIngredientsViewItemMvcImpl;
import com.example.easypeasy.screens.searchByIngredientsList.SearchByIngredientsViewMvc;
import com.example.easypeasy.screens.searchByIngredientsList.SearchByIngredientsViewMvcImpl;
import com.example.easypeasy.screens.searchByNutrientsList.SearchByNutrientsViewMvc;
import com.example.easypeasy.screens.searchByNutrientsList.SearchByNutrientsViewMvcImpl;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;

    public ViewMvcFactory(LayoutInflater mLayoutInflater) {
        this.mLayoutInflater = mLayoutInflater;
    }

    public SearchByNutrientsViewMvc getSearchByNutrientsViewMvc(@Nullable ViewGroup parent) {
        return new SearchByNutrientsViewMvcImpl(mLayoutInflater, parent);
    }

    public SearchByIngredientsViewMvc getSearchByIngredientsViewMvc(@Nullable ViewGroup parent, SearchableInfo searchableInfo) {
        return new SearchByIngredientsViewMvcImpl(mLayoutInflater, parent, searchableInfo, this);
    }

    public RecipeInformationViewMvc getRecipeInformationViewMvc(@Nullable ViewGroup parent) {
        return new RecipeInformationViewMvcImpl(mLayoutInflater, parent, this);
    }

    public SearchByIngredientsViewItemMvc getSearchByIngredientsViewItemMvc(@Nullable ViewGroup parent, SearchableInfo searchableInfo) {
        return new SearchByIngredientsViewItemMvcImpl(mLayoutInflater, parent, searchableInfo);
    }

    public CategoryListViewMvc getCategoryViewMvc(@Nullable ViewGroup parent) {
        return new CategoryListViewMvcImpl(mLayoutInflater, parent, this);
    }

    public CategoryListViewItemMvc getCategoryListViewItemMvc(@Nullable ViewGroup parent) {
        return new CategoryListViewItemMvcImpl(mLayoutInflater, parent);
    }

    public RecipeInformationUsedIngredientsItemViewMvc getRecipeInformationUsedIngredientsItemViewMvc(@Nullable ViewGroup parent) {
        return new RecipeInformationUsedIngredientsItemViewMvc(mLayoutInflater, parent);
    }
}
