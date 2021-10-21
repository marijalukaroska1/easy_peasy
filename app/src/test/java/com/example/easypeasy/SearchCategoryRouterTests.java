package com.example.easypeasy;

import android.content.Intent;

import com.example.easypeasy.screens.categoriesList.CategoryActivity;
import com.example.easypeasy.screens.searchByIngredientsList.SearchByIngredientsActivity;
import com.example.easypeasy.screens.searchByNutrientsList.SearchByNutrientsActivity;
import com.example.easypeasy.models.Category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class SearchCategoryRouterTests {

    @Test
    public void test_WhenInputIsIngredients_nextScreenIsSearchByIngredientsActivity() {
        SearchCategoryRouter screensRouter = new SearchCategoryRouter();
        CategoryActivity categoryActivityMock = Robolectric.buildActivity(CategoryActivity.class).create().get();
        screensRouter.activity = new WeakReference<CategoryActivity>(categoryActivityMock);


        List<Category> categories = new ArrayList<>();
        categories.add(new Category("ingredients", false));
        categories.add(new Category("nutrients", false));
        categories.add(new Category("cuisine", false));
        categoryActivityMock.getViewMvc().bindCategories(categories);

        //When - Ingredients are input
        Intent intent = screensRouter.determineNextScreen(categories);

        //Then
        String targetActivityName = intent.getComponent().getClassName();
        assertEquals(targetActivityName, SearchByIngredientsActivity.class.getName());
    }



    @Test
    public void test_WhenInputIsNutrients_nextScreenIsSearchByNutrientsActivity() {
        SearchCategoryRouter screensRouter = new SearchCategoryRouter();
        CategoryActivity categoryActivityMock = Robolectric.buildActivity(CategoryActivity.class).create().get();
        screensRouter.activity = new WeakReference<CategoryActivity>(categoryActivityMock);


        List<Category> categories = new ArrayList<>();
        categories.add(new Category("ingredients", false));
        categories.add(new Category("nutrients", false));
        categories.add(new Category("cuisine", false));
        categoryActivityMock.getViewMvc().bindCategories(categories);

        //When - Nutrients are input
        Intent intent = screensRouter.determineNextScreen(categories);

        //Then
        String targetActivityName = intent.getComponent().getClassName();
        assertEquals(targetActivityName, SearchByNutrientsActivity.class.getName());
    }
}
