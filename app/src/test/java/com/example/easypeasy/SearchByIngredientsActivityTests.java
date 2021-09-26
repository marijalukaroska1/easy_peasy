package com.example.easypeasy;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.test.core.app.ApplicationProvider;

import com.example.easypeasy.activities.SearchByIngredientsActivity;
import com.example.easypeasy.activities.SearchInput;
import com.example.easypeasy.adapters.RecipesAdapter;
import com.example.easypeasy.models.ConvertAmountsResponse;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Nutrient;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.models.RecipeInformationResponse;
import com.example.easypeasy.models.SearchIngredientsResponse;
import com.example.easypeasy.spoonacular.ConvertAmountsRequest;
import com.example.easypeasy.spoonacular.IngredientRequest;
import com.example.easypeasy.spoonacular.RecipesRequest;
import com.example.easypeasy.spoonacular.SearchIngredientsRequest;
import com.example.easypeasy.spoonacular.SpoonacularRecipesApi;
import com.example.easypeasy.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
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
        SearchByIngredientsActivity searchByIngredientsActivity = Robolectric.buildActivity(SearchByIngredientsActivity.class).get();
        SearchActivityOutputSpy outputSpy = new SearchActivityOutputSpy();
        searchByIngredientsActivity.output = outputSpy;
        searchByIngredientsActivity.fetchMetaData();

        assertTrue(outputSpy.recipesRequestCopy.isSearchByIngredients);
    }

    @Test
    public void test_fetchRecipesMetaData_shouldCall_queryRecipesByIngredients() {
        RecipesInteractor recipesInteractor = new RecipesInteractor();
        RecipesRequest recipesRequest = new RecipesRequest(ApplicationProvider.getApplicationContext());
        recipesRequest.isSearchByIngredients = true;
        recipesInteractor.output = new RecipesPresenterSpy();
        SpoonacularAPISpy spoonacularAPISpy = new SpoonacularAPISpy();
        recipesRequest.spoonacularApi = spoonacularAPISpy;
        recipesInteractor.fetchRecipesData(recipesRequest, new ArrayList<>(), null);

        assertTrue(spoonacularAPISpy.isQueryRecipesByIngredientsCalled);
    }

    @Test
    public void test_presentRecipesMetaData_with_validInput_shouldCall_displayRecipesMetaData() {
        RecipesPresenter recipesPresenter = new RecipesPresenter();
        List<Recipe> recipesResponse = new ArrayList<>();

        SearchActivityInputSpy searchActivityInputSpy = new SearchActivityInputSpy();
        recipesPresenter.output = new WeakReference<>(searchActivityInputSpy);

        //when
        recipesPresenter.presentRecipesData(recipesResponse, ApplicationProvider.getApplicationContext());

        //then
        assertTrue(searchActivityInputSpy.isDisplayRecipesMetaDataCalled);
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
            searchByIngredientsActivity.ingredientList.add(new Ingredient());
        }

        Button searchButton = searchByIngredientsActivity.findViewById(R.id.searchButtonId);
        assertNotNull(searchButton);
        searchButton.performClick();

        searchByIngredientsActivity.insertItemFieldAndNotify(new Ingredient());

        assertEquals(ApplicationProvider.getApplicationContext().getResources().getString(R.string.message_maximum_ingredients), ShadowToast.getTextOfLatestToast().toString());
    }

    // When search button is clicked if the number of ingredient fields is between the range of 3 and 10
    // then fetchRecipesData() is called
    @Test
    public void test_WhenSearchButtonIsClickedIfTheNumberOfIngredientsIsInTheRange_fetchRecipesDataIsCalled() {
        SearchByIngredientsActivity searchByIngredientsActivity = Robolectric.buildActivity(SearchByIngredientsActivity.class).create().get();

        SearchActivityOutputSpy outputSpy = new SearchActivityOutputSpy();
        searchByIngredientsActivity.output = outputSpy;

        for (int i = 0; i <= 4; i++) {
            searchByIngredientsActivity.ingredientList.add(new Ingredient());
        }

        Button searchButton = searchByIngredientsActivity.findViewById(R.id.searchButtonId);
        searchButton.performClick();

        assertTrue(outputSpy.fetchRecipesMetaDataIsCalled);
    }

    //getIngredientsUserInput() returns a string containing all ingredients names inserted by the user
    @Test
    public void test_getIngredientsUserInput_returnsStringContainingAllIngredientsSeparatedWithComma() {
        SearchByIngredientsActivity searchByIngredientsActivity = Robolectric.buildActivity(SearchByIngredientsActivity.class).create().get();
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("apple", 3));
        ingredients.add(new Ingredient("milk", 100));
        ingredients.add(new Ingredient("eggs", 5));

        searchByIngredientsActivity.ingredientList = ingredients;

        String stringIngredients = Utils.getIngredientsUserInput(ingredients);
        assertTrue(stringIngredients.contains(ingredients.get(0).getName()));
    }

    //when fetchIngredientData() is called, queryIngredientMetaData() is also called
    @Test
    public void test_whenFetchIngredientDataIsCalled_queryIngredientMetaDataIsCalled() {
        RecipesInteractor recipesInteractor = new RecipesInteractor();
        IngredientRequest ingredientRequest = new IngredientRequest();
        recipesInteractor.output = new RecipesPresenterSpy();
        SpoonacularAPISpy spoonacularAPISpy = new SpoonacularAPISpy();
        ingredientRequest.spoonacularApi = spoonacularAPISpy;
        recipesInteractor.fetchIngredientData(ingredientRequest, 0);

        assertTrue(spoonacularAPISpy.isQueryIngredientMetaDataCalled);
    }

    private static class SearchActivityOutputSpy implements RecipesInteractorInput {

        boolean fetchRecipesMetaDataIsCalled = false;
        boolean fetchIngredientMetaDataIsCalled = false;
        RecipesRequest recipesRequestCopy;
        IngredientRequest ingredientRequestCopy;
        SearchIngredientsRequest searchIngredientsRequestCopy;
        ConvertAmountsRequest convertAmountsRequestCopy;

        @Override
        public void fetchRecipesData(RecipesRequest request, List<Ingredient> inputIngredientsList, List<Nutrient> nutrientList) {
            fetchRecipesMetaDataIsCalled = true;
            recipesRequestCopy = request;
        }

        @Override
        public void fetchIngredientData(IngredientRequest request, long ingredientId) {
            fetchIngredientMetaDataIsCalled = true;
            ingredientRequestCopy = request;
        }

        @Override
        public void fetchIngredientsSearchData(SearchIngredientsRequest request, String ingredientName) {
            searchIngredientsRequestCopy = request;
        }

        @Override
        public void convertAmountsAndUnitsRequest(ConvertAmountsRequest request, String ingredientName, Float sourceAmount, String sourceUnit, Map<String, String> responseIngredientData, List<Recipe> recipes, Recipe currentRecipe) {
            convertAmountsRequestCopy = request;
        }
    }


    public static class RecipesPresenterSpy implements RecipesPresenterInput {

        boolean isPresentRecipesDataCalled = false;
        boolean isPresentIngredientDataCalled = false;
        List<Recipe> recipesResponse;
        Ingredient ingredientResponse;

        @Override
        public void presentRecipesData(List<Recipe> recipesResponse, Context context) {
            isPresentRecipesDataCalled = true;
            this.recipesResponse = recipesResponse;
        }

        @Override
        public void presentIngredientData(Ingredient ingredient) {
            isPresentIngredientDataCalled = true;
            ingredientResponse = ingredient;
        }

        @Override
        public void presentIngredients(List<Ingredient> ingredientList) {

        }

        @Override
        public void convertAmountResponse() {

        }
    }

    public static class SearchActivityInputSpy implements SearchInput {

        boolean isDisplayRecipesMetaDataCalled = false;
        RecipesAdapter recipesViewModelCopy;


        @Override
        public void displayRecipesMetaData(RecipesAdapter object) {
            isDisplayRecipesMetaDataCalled = true;
            recipesViewModelCopy = object;
        }

        @Override
        public void displayIngredientUnits(List<String> possibleUnits) {

        }
    }

    public static class SpoonacularAPISpy implements SpoonacularRecipesApi {

        boolean isQueryRecipesByIngredientsCalled = false;
        boolean isQueryRecipesByNutrientsCalled = false;
        boolean isQueryIngredientMetaDataCalled = false;
        boolean isConvertAmountCalled = false;
        boolean isQueryRecipeInformationCalled = false;
        boolean isSearchIngredientsCalled = false;

        @Override
        public Call<List<Recipe>> queryRecipesByIngredients(Map<String, String> options) {
            isQueryRecipesByIngredientsCalled = true;
            return new Call<List<Recipe>>() {
                @Override
                public Response<List<Recipe>> execute() {
                    return null;
                }

                @Override
                public void enqueue(Callback<List<Recipe>> callback) {

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
                public Call<List<Recipe>> clone() {
                    return null;
                }

                @Override
                public Request request() {
                    return null;
                }
            };
        }

        @Override
        public Call<List<Recipe>> queryRecipesByNutrients(Map<String, String> options) {
            isQueryRecipesByNutrientsCalled = true;
            return new Call<List<Recipe>>() {
                @Override
                public Response<List<Recipe>> execute() throws IOException {
                    return null;
                }

                @Override
                public void enqueue(Callback<List<Recipe>> callback) {

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
                public Call<List<Recipe>> clone() {
                    return null;
                }

                @Override
                public Request request() {
                    return null;
                }
            };
        }

        @Override
        public Call<Ingredient> queryIngredientData(long id, Map<String, String> options) {
            isQueryIngredientMetaDataCalled = true;
            return new Call<Ingredient>() {
                @Override
                public Response<Ingredient> execute() throws IOException {
                    return null;
                }

                @Override
                public void enqueue(Callback<Ingredient> callback) {

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
                public Call<Ingredient> clone() {
                    return null;
                }

                @Override
                public Request request() {
                    return null;
                }
            };
        }


        @Override
        public Call<SearchIngredientsResponse> searchIngredients(Map<String, String> options) {
            isSearchIngredientsCalled = true;
            return new Call<SearchIngredientsResponse>() {
                @Override
                public Response<SearchIngredientsResponse> execute() throws IOException {
                    return null;
                }

                @Override
                public void enqueue(Callback<SearchIngredientsResponse> callback) {

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
                public Call<SearchIngredientsResponse> clone() {
                    return null;
                }

                @Override
                public Request request() {
                    return null;
                }
            };
        }

        @Override
        public Call<ConvertAmountsResponse> convertAmountAndUnit(Map<String, String> options) {
            isConvertAmountCalled = true;
            return new Call<ConvertAmountsResponse>() {
                @Override
                public Response<ConvertAmountsResponse> execute() throws IOException {
                    return null;
                }

                @Override
                public void enqueue(Callback<ConvertAmountsResponse> callback) {

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
                public Call<ConvertAmountsResponse> clone() {
                    return null;
                }

                @Override
                public Request request() {
                    return null;
                }
            };
        }

        @Override
        public Call<RecipeInformationResponse> queryRecipeInformation(long id, Map<String, String> options) {
            isQueryRecipeInformationCalled = true;
            return new Call<RecipeInformationResponse>() {
                @Override
                public Response<RecipeInformationResponse> execute() throws IOException {
                    return null;
                }

                @Override
                public void enqueue(Callback<RecipeInformationResponse> callback) {

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
                public Call<RecipeInformationResponse> clone() {
                    return null;
                }

                @Override
                public Request request() {
                    return null;
                }
            };
        }
    }
}
