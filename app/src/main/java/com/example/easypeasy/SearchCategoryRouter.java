package com.example.easypeasy;

import android.content.Intent;
import android.util.Log;

import com.example.easypeasy.models.Category;
import com.example.easypeasy.screens.categoriesList.CategoryActivity;
import com.example.easypeasy.screens.searchByIngredientsList.SearchByIngredientsActivity;
import com.example.easypeasy.screens.searchByNutrientsList.SearchByNutrientsActivity;

import java.lang.ref.WeakReference;
import java.util.List;

interface SearchCategoryRouterInput {
    Intent determineNextScreen(List<Category> categoryList);

    void passDataToNextScreen(int position, Intent intent);
}

public class SearchCategoryRouter implements SearchCategoryRouterInput {

    private static final String TAG = SearchCategoryRouter.class.getSimpleName();
    public WeakReference<CategoryActivity> activity;


    @Override
    public Intent determineNextScreen(List<Category> categoryList) {
        //Based on the position or some other data decide what is the next scene
        Log.d(TAG, "marija categoryList: " + categoryList);
        Category chosenCategory = findChosenCategory(categoryList);
        if (chosenCategory.getName().equals("ingredients")) {
            return new Intent(activity.get(), SearchByIngredientsActivity.class);
        } else {
            return new Intent(activity.get(), SearchByNutrientsActivity.class);
        }
    }

    private Category findChosenCategory(List<Category> categoryList) {
        Category chosenCategory = null;
        for (Category category : categoryList) {
            if (category.isSelected()) {
                chosenCategory = category;
            }
        }
        return chosenCategory;
    }

    @Override
    public void passDataToNextScreen(int position, Intent intent) {

    }
}
