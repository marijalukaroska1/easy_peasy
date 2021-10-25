package com.example.easypeasy;

import android.view.View;
import android.widget.Button;

import androidx.test.core.app.ApplicationProvider;

import com.example.easypeasy.common.utils.Utils;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.RecipeData;
import com.example.easypeasy.models.schemas.ConvertAmountSchema;
import com.example.easypeasy.models.schemas.IngredientSchema;
import com.example.easypeasy.models.schemas.RecipeDataSchema;
import com.example.easypeasy.models.schemas.RecipeSchema;
import com.example.easypeasy.models.schemas.SearchIngredientNameSchema;
import com.example.easypeasy.networking.SpoonacularApi;
import com.example.easypeasy.screens.searchByIngredientsList.SearchByIngredientsActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class SearchByIngredientsActivityTests {

    @Test
    public void test_SearchByIngredientsActivity_shouldNotBeNull() {
        SearchByIngredientsActivity mainActivity = Robolectric.buildActivity(SearchByIngredientsActivity.class).get();
        assertNotNull(mainActivity);
    }

    @Test
    public void test_SearchByIngredientsActivity_Calls_fetchRecipesMetaData_withCorrectData() {
//        SearchByIngredientsActivity searchByIngredientsActivity = Robolectric.buildActivity(SearchByIngredientsActivity.class).get();
//
//        searchByIngredientsActivity.fetchMetaData();
//
//        assertTrue(outputSpy.fetchRecipesUseCaseCopy.isSearchByIngredients);
    }


    @Test
    public void test_SearchByIngredientsActivityShouldDisplaySearchButton() {
        SearchByIngredientsActivity searchByIngredientsActivity = Robolectric.buildActivity(SearchByIngredientsActivity.class).create().get();

        Button searchButton = searchByIngredientsActivity.findViewById(R.id.searchButtonId);
        assertNotNull(searchButton);
        assertEquals(View.VISIBLE, searchButton.getVisibility());
    }

    // When the minimum number of inserted ingredients is not reached, and the search button is clicked
    // toast message is displayed
    @Test
    public void test_WhenTheMinimumNumberInsertedIngredientsIsNotReached_And_SearchButtonIsClicked_ToastMessageIsDisplayed() {
        SearchByIngredientsActivity searchByIngredientsActivity = Robolectric.buildActivity(SearchByIngredientsActivity.class).create().get();

        Button searchButton = searchByIngredientsActivity.findViewById(R.id.searchButtonId);
        assertNotNull(searchButton);
        searchButton.performClick();

        assertEquals(ApplicationProvider.getApplicationContext().getResources().getString(R.string.message_minimum_ingredients), ShadowToast.getTextOfLatestToast());
    }

    // When the maximum number of inserted ingredients is reached,
    // a toast message is displayed
    @Test
    public void test_WhenTheMaximumNumberInsertedIngredientsIsReached_ToastMessageIsDisplayed() {
        SearchByIngredientsActivity searchByIngredientsActivity = Robolectric.buildActivity(SearchByIngredientsActivity.class).create().get();

        for (int i = 0; i <= 10; i++) {
            searchByIngredientsActivity.mViewMvc.getIngredientList().add(new Ingredient());
        }

        Button searchButton = searchByIngredientsActivity.findViewById(R.id.searchButtonId);
        assertNotNull(searchButton);
        searchButton.performClick();

        searchByIngredientsActivity.mViewMvc.bindIngredient(new Ingredient());

        assertEquals(ApplicationProvider.getApplicationContext().getResources().getString(R.string.message_maximum_ingredients), ShadowToast.getTextOfLatestToast().toString());
    }

    // When search button is clicked if the number of ingredient fields is between the range of 3 and 10
    // then fetchRecipesData() is called
    @Test
    public void test_WhenSearchButtonIsClickedIfTheNumberOfIngredientsIsInTheRange_fetchRecipesDataIsCalled() {
//        SearchByIngredientsActivity searchByIngredientsActivity = Robolectric.buildActivity(SearchByIngredientsActivity.class).create().get();
//
//        SearchActivityOutputSpy outputSpy = new SearchActivityOutputSpy();
//        searchByIngredientsActivity.output = outputSpy;
//
//        for (int i = 0; i <= 4; i++) {
//            searchByIngredientsActivity.mViewMvc.getIngredientList().add(new Ingredient());
//        }
//
//        Button searchButton = searchByIngredientsActivity.findViewById(R.id.searchButtonId);
//        searchButton.performClick();
//
//        assertTrue(outputSpy.fetchRecipesMetaDataIsCalled);
    }

    //getIngredientsUserInput() returns a string containing all ingredients names inserted by the user
    @Test
    public void test_getIngredientsUserInput_returnsStringContainingAllIngredientsSeparatedWithComma() {
        SearchByIngredientsActivity searchByIngredientsActivity = Robolectric.buildActivity(SearchByIngredientsActivity.class).create().get();
        List<Ingredient> ingredients = new ArrayList<>();

        searchByIngredientsActivity.mViewMvc.bindIngredient(new Ingredient("apple", 3));
        searchByIngredientsActivity.mViewMvc.bindIngredient(new Ingredient("milk", 100));
        searchByIngredientsActivity.mViewMvc.bindIngredient(new Ingredient("eggs", 5));

        String stringIngredients = Utils.getIngredientsUserInput(ingredients);
        assertTrue(stringIngredients.contains(ingredients.get(0).getName()));
    }

    public static class SpoonacularAPISpy implements SpoonacularApi {

        boolean isQueryRecipesByIngredientsCalled = false;
        boolean isQueryRecipesByNutrientsCalled = false;
        boolean isQueryIngredientMetaDataCalled = false;
        boolean isConvertAmountCalled = false;
        boolean isQueryRecipeInformationCalled = false;
        boolean isSearchIngredientsCalled = false;

        @Override
        public Call<List<RecipeSchema>> queryRecipesByIngredients(Map<String, String> options) {
            isQueryRecipesByIngredientsCalled = true;
            return new Call<List<RecipeSchema>>() {
                @Override
                public Response<List<RecipeSchema>> execute() throws IOException {
                    return null;
                }

                @Override
                public void enqueue(Callback<List<RecipeSchema>> callback) {

                }

                @Override
                public boolean isExecuted() {
                    return false;
                }

                @Override
                public void cancel() {

                }

                @Override
                public boolean isCanceled() {
                    return false;
                }

                @Override
                public Call<List<RecipeSchema>> clone() {
                    return null;
                }

                @Override
                public Request request() {
                    return null;
                }

                @Override
                public Timeout timeout() {
                    return null;
                }
            };
        }

        @Override
        public Call<List<RecipeSchema>> queryRecipesByNutrients(Map<String, String> options) {
            isQueryRecipesByNutrientsCalled = true;
            return new Call<List<RecipeSchema>>() {
                @Override
                public Response<List<RecipeSchema>> execute() throws IOException {
                    return null;
                }

                @Override
                public void enqueue(Callback<List<RecipeSchema>> callback) {

                }

                @Override
                public boolean isExecuted() {
                    return false;
                }

                @Override
                public void cancel() {

                }

                @Override
                public boolean isCanceled() {
                    return false;
                }

                @Override
                public Call<List<RecipeSchema>> clone() {
                    return null;
                }

                @Override
                public Request request() {
                    return null;
                }

                @Override
                public Timeout timeout() {
                    return null;
                }
            };
        }

        @Override
        public Call<IngredientSchema> queryIngredientData(long id, Map<String, String> options) {
            isQueryIngredientMetaDataCalled = true;
            return new Call<IngredientSchema>() {
                @Override
                public Response<IngredientSchema> execute() throws IOException {
                    return null;
                }

                @Override
                public void enqueue(Callback<IngredientSchema> callback) {

                }

                @Override
                public boolean isExecuted() {
                    return false;
                }

                @Override
                public void cancel() {

                }

                @Override
                public boolean isCanceled() {
                    return false;
                }

                @Override
                public Call<IngredientSchema> clone() {
                    return null;
                }

                @Override
                public Request request() {
                    return null;
                }

                @Override
                public Timeout timeout() {
                    return null;
                }
            };
        }


        @Override
        public Call<SearchIngredientNameSchema> searchIngredients(Map<String, String> options) {
            isSearchIngredientsCalled = true;
            return new Call<SearchIngredientNameSchema>() {
                @Override
                public Response<SearchIngredientNameSchema> execute() throws IOException {
                    return null;
                }

                @Override
                public void enqueue(Callback<SearchIngredientNameSchema> callback) {

                }

                @Override
                public boolean isExecuted() {
                    return false;
                }

                @Override
                public void cancel() {

                }

                @Override
                public boolean isCanceled() {
                    return false;
                }

                @Override
                public Call<SearchIngredientNameSchema> clone() {
                    return null;
                }

                @Override
                public Request request() {
                    return null;
                }

                @Override
                public Timeout timeout() {
                    return null;
                }
            };
        }

        @Override
        public Observable<ConvertAmountSchema> convertAmountAndUnit(Map<String, String> options) {
            isConvertAmountCalled = true;
            return new Observable<ConvertAmountSchema>() {
                @Override
                protected void subscribeActual(@NonNull Observer<? super ConvertAmountSchema> observer) {

                }

            };
        }

        @Override
        public Call<RecipeDataSchema> queryRecipeInformation(long id, Map<String, String> options) {
            isQueryRecipeInformationCalled = true;
            return new Call<RecipeDataSchema>() {
                @Override
                public Response<RecipeDataSchema> execute() throws IOException {
                    return null;
                }

                @Override
                public void enqueue(Callback<RecipeDataSchema> callback) {

                }

                @Override
                public boolean isExecuted() {
                    return false;
                }

                @Override
                public void cancel() {

                }

                @Override
                public boolean isCanceled() {
                    return false;
                }

                @Override
                public Call<RecipeDataSchema> clone() {
                    return null;
                }

                @Override
                public Request request() {
                    return null;
                }

                @Override
                public Timeout timeout() {
                    return null;
                }
            };
        }
    }
}
