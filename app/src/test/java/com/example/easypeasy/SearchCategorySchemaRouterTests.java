package com.example.easypeasy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class SearchCategorySchemaRouterTests {

    @Test
    public void test_WhenInputIsIngredients_nextScreenIsSearchByIngredientsActivity() {
//        SearchCategoryRouter screensRouter = new SearchCategoryRouter();
//        CategoryActivity categoryActivityMock = Robolectric.buildActivity(CategoryActivity.class).create().get();
//        screensRouter.activity = new WeakReference<CategoryActivity>(categoryActivityMock);
//
//
//        List<CategorySchema> categories = new ArrayList<>();
//        categories.add(new CategorySchema("ingredients", false));
//        categories.add(new CategorySchema("nutrients", false));
//        categories.add(new CategorySchema("cuisine", false));
//        categoryActivityMock.getViewMvc().bindCategories(categories);
//
//        //When - Ingredients are input
//        Intent intent = screensRouter.determineNextScreen(categories);
//
//        //Then
//        String targetActivityName = intent.getComponent().getClassName();
//        assertEquals(targetActivityName, SearchByIngredientsActivity.class.getName());
    }



    @Test
    public void test_WhenInputIsNutrients_nextScreenIsSearchByNutrientsActivity() {
//        SearchCategoryRouter screensRouter = new SearchCategoryRouter();
//        CategoryActivity categoryActivityMock = Robolectric.buildActivity(CategoryActivity.class).create().get();
//        screensRouter.activity = new WeakReference<CategoryActivity>(categoryActivityMock);
//
//
//        List<CategorySchema> categories = new ArrayList<>();
//        categories.add(new CategorySchema("ingredients", false));
//        categories.add(new CategorySchema("nutrients", false));
//        categories.add(new CategorySchema("cuisine", false));
//        categoryActivityMock.getViewMvc().bindCategories(categories);
//
//        //When - Nutrients are input
//        Intent intent = screensRouter.determineNextScreen(categories);
//
//        //Then
//        String targetActivityName = intent.getComponent().getClassName();
//        assertEquals(targetActivityName, SearchByNutrientsActivity.class.getName());
    }
}
