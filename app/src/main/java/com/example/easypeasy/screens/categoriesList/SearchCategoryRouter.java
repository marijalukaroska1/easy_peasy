package com.example.easypeasy.screens.categoriesList;

import android.content.Intent;
import android.util.Log;

import com.example.easypeasy.networking.categories.CategorySchema;
import com.example.easypeasy.screens.recipesList.recipesByIngredientsList.SearchByIngredientsActivity;
import com.example.easypeasy.screens.recipesList.recipesByNutrientsList.SearchByNutrientsActivity;

import java.lang.ref.WeakReference;
import java.util.List;

interface SearchCategoryRouterInput {
    Intent determineNextScreen(List<CategorySchema> categoryList);

    void passDataToNextScreen(int position, Intent intent);
}

public class SearchCategoryRouter implements SearchCategoryRouterInput {

    private static final String TAG = SearchCategoryRouter.class.getSimpleName();
    public WeakReference<CategoryActivity> activity;


    @Override
    public Intent determineNextScreen(List<CategorySchema> categoryList) {
        //Based on the position or some other data decide what is the next scene
        Log.d(TAG, "marija categoryList: " + categoryList);
        CategorySchema chosenCategory = findChosenCategory(categoryList);
        if (chosenCategory.getName().equals("ingredients")) {
            return new Intent(activity.get(), SearchByIngredientsActivity.class);
        } else {
            return new Intent(activity.get(), SearchByNutrientsActivity.class);
        }
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

    @Override
    public void passDataToNextScreen(int position, Intent intent) {

    }
}
