package com.example.easypeasy.screens.common;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.easypeasy.common.utils.Utils;
import com.example.easypeasy.networking.categories.CategorySchema;
import com.example.easypeasy.screens.categoriesList.CategoryFragment;
import com.example.easypeasy.screens.common.fragmentframehelper.FragmentHelper;
import com.example.easypeasy.screens.recipeDetails.RecipeDetailsFragment;
import com.example.easypeasy.screens.recipesList.recipesByIngredientsList.SearchByIngredientsFragment;
import com.example.easypeasy.screens.recipesList.recipesByNutrientsList.SearchByNutrientsFragment;

import java.util.List;


public class ScreenNavigator {

    private static final String TAG = ScreenNavigator.class.getSimpleName();
    private final FragmentHelper mFragmentFrameHelper;
    private final FragmentActivity mActivity;

    public ScreenNavigator(FragmentHelper fragmentFrameHelper, FragmentActivity activity) {
        mFragmentFrameHelper = fragmentFrameHelper;
        mActivity = activity;
    }

    private CategorySchema findChosenCategory(List<CategorySchema> categoryList) {
        CategorySchema chosenCategory = null;
        for (CategorySchema category : categoryList) {
            if (category.isSelected()) {
                chosenCategory = category;
            }
        }
        return chosenCategory;
    }

    public void determineNextScreen(List<CategorySchema> categoryList) {
        //Based on the position or some other data decide what is the next scene
        Log.d(TAG, "marija categoryList: " + categoryList);
        CategorySchema chosenCategory = findChosenCategory(categoryList);
        if (chosenCategory.getName().equals("ingredients")) {
            toSearchByIngredients();
        } else {
            toSearchByNutrients();
        }
    }

    public void toSearchByIngredients() {
        mFragmentFrameHelper.replaceFragment(SearchByIngredientsFragment.newInstance(mActivity.getComponentName()));
    }

    public void toSearchByNutrients() {
        mFragmentFrameHelper.replaceFragment(SearchByNutrientsFragment.newInstance());
    }

    public void toCategoryList() {
        mFragmentFrameHelper.replaceFragmentAndClearHistory(CategoryFragment.newInstance());
    }

    public void toRecipeDetails(long recipeId) {
        mFragmentFrameHelper.replaceFragment(RecipeDetailsFragment.newInstance(recipeId));
    }

    public void navigateUp() {
        mFragmentFrameHelper.navigateUp();
    }

    public void hideKeyboardOnCurrentScreen() {
        Utils.hideKeyboard(mActivity);
    }
}
