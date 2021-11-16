package com.example.easypeasy.screens.categoriesList;

import android.util.Log;

import com.example.easypeasy.networking.categories.CategorySchema;
import com.example.easypeasy.screens.common.ScreenNavigator;

import java.util.ArrayList;
import java.util.List;

public class CategoryListController implements CategoryListViewMvc.Listener {

    private static final String TAG = CategoryListController.class.getSimpleName();
    private CategoryListViewMvc mCategoryListViewMvc;
    private final ScreenNavigator mSearchCategoryRouter;
    private List<CategorySchema> mCategories;


    public CategoryListController(ScreenNavigator searchCategoryRouter) {
        mSearchCategoryRouter = searchCategoryRouter;
    }

    public void bindView(CategoryListViewMvc categoryListViewMvc) {
        mCategoryListViewMvc = categoryListViewMvc;
        mCategories = new ArrayList<>();
        mCategories.add(new CategorySchema("ingredients", false));
        mCategories.add(new CategorySchema("nutrients", false));
        mCategoryListViewMvc.bindCategories(mCategories);
    }


    public void onStart() {
        mCategoryListViewMvc.registerListener(this);
    }

    public void onStop() {
        mCategoryListViewMvc.unregisterListener(this);
    }


    @Override
    public void onContinueButtonClicked() {
        mSearchCategoryRouter.determineNextScreen(mCategories);
    }

    @Override
    public void onNavigateUpAndClicked() {

    }

    @Override
    public void selectSearchByIngredientsItemClicked() {
        Log.d(TAG, "selectSearchByIngredientsItemClicked");
        mSearchCategoryRouter.toSearchByIngredients();
    }

    @Override
    public void selectSearchByNutrientsItemClicked() {
        Log.d(TAG, "selectSearchByNutrientsItemClicked");
        mSearchCategoryRouter.toSearchByNutrients();

    }

    public boolean onBackPressed() {
        if (mCategoryListViewMvc.isDrawerOpen()) {
            mCategoryListViewMvc.closeDrawer();
            return true;
        } else {
            return false;
        }
    }
}
