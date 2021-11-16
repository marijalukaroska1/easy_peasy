package com.example.easypeasy.screens.common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.easypeasy.networking.categories.CategorySchema;
import com.example.easypeasy.screens.recipesList.recipesByIngredientsList.SearchByIngredientsActivity;
import com.example.easypeasy.screens.recipesList.recipesByNutrientsList.SearchByNutrientsActivity;

import java.util.List;

interface ScreenNavigatorInput {
    void determineNextScreen(List<CategorySchema> categoryList);

    void toSearchByIngredients();

    void toSearchByNutrients();
}

public class ScreenNavigator implements ScreenNavigatorInput {

    private static final String TAG = ScreenNavigator.class.getSimpleName();
    private final Context mContext;

    public ScreenNavigator(Context context) {
        mContext = context;
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
    public void determineNextScreen(List<CategorySchema> categoryList) {
        //Based on the position or some other data decide what is the next scene
        Log.d(TAG, "marija categoryList: " + categoryList);
        CategorySchema chosenCategory = findChosenCategory(categoryList);
        Intent intent;
        if (chosenCategory.getName().equals("ingredients")) {
            intent = new Intent(mContext, SearchByIngredientsActivity.class);
        } else {
            intent = new Intent(mContext, SearchByNutrientsActivity.class);
        }

        mContext.startActivity(intent);
    }

    @Override
    public void toSearchByIngredients() {
        mContext.startActivity(new Intent(mContext, SearchByIngredientsActivity.class));
    }

    @Override
    public void toSearchByNutrients() {
        mContext.startActivity(new Intent(mContext, SearchByNutrientsActivity.class));
    }
}
