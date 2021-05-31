package com.example.easypeasy;

import com.example.easypeasy.activities.SearchByIngredientsActivity;
import com.example.easypeasy.activities.SearchInput;
import com.example.easypeasy.adapters.RecipesAdapter;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.spoonacular.RecipesRequest;
import com.example.easypeasy.spoonacular.SpoonacularApi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class SearchByIngredientsActivityTests {

    @Test
    public void test_SearchActivity_shouldNotBeNull() {
        SearchByIngredientsActivity mainActivity = Robolectric.buildActivity(SearchByIngredientsActivity.class).get();
        assertNotNull(mainActivity);
    }

    @Test
    public void test_OnCreateShouldCall_fetchRecipesMetaData() {
        SearchByIngredientsActivity searchByIngredientsActivity = Robolectric.buildActivity(SearchByIngredientsActivity.class).get();
        SearchActivityOutputSpy outputSpy = new SearchActivityOutputSpy();
        searchByIngredientsActivity.output = outputSpy;
        searchByIngredientsActivity.fetchMetaData();

        assertTrue(outputSpy.fetchRecipesMetaDataIsCalled);
    }

    @Test
    public void test_OnCreate_Calls_fetchRecipesMetaData_withCorrectData() {
        SearchByIngredientsActivity searchByIngredientsActivity = Robolectric.buildActivity(SearchByIngredientsActivity.class).get();
        SearchActivityOutputSpy outputSpy = new SearchActivityOutputSpy();
        searchByIngredientsActivity.output = outputSpy;
        searchByIngredientsActivity.fetchMetaData();

        assertTrue(outputSpy.recipesRequestCopy.isSearchByIngredients);
    }

    @Test
    public void test_fetchRecipesMetaData_shouldCall_queryRecipesByIngredients() {
        RecipesInteractor recipesInteractor = new RecipesInteractor();
        RecipesRequest recipesRequest = new RecipesRequest();
        recipesRequest.isSearchByIngredients = true;
        recipesInteractor.output = new RecipesPresenterSpy();
        SpoonacularAPISpy spoonacularAPISpy = new SpoonacularAPISpy();
        recipesRequest.spoonacularApi = spoonacularAPISpy;
        recipesInteractor.fetchRecipesData(recipesRequest, "");

        assertTrue(spoonacularAPISpy.isQueryRecipesByIngredientsCalled);
    }

    @Test
    public void test_presentRecipesMetaData_with_vaildInput_shouldCall_displayRecipesMetaData() {
        RecipesPresenter recipesPresenter = new RecipesPresenter();
        List<Recipe> recipesResponse = new ArrayList<>();

        SearchActivityInputSpy searchActivityInputSpy = new SearchActivityInputSpy();
        recipesPresenter.output = new WeakReference<SearchInput>(searchActivityInputSpy);

        //when
        recipesPresenter.presentRecipesData(recipesResponse);

        //then
        assertTrue(searchActivityInputSpy.isDisplayRecipesMetaDataCalled);
    }

    private class SearchActivityOutputSpy implements RecipesInteractorInput {

        boolean fetchRecipesMetaDataIsCalled = false;
        RecipesRequest recipesRequestCopy;

        @Override
        public void fetchRecipesData(RecipesRequest request, String userInput) {
            fetchRecipesMetaDataIsCalled = true;
            recipesRequestCopy = request;
        }
    }


    public class RecipesPresenterSpy implements RecipesPresenterInput {

        boolean isPresentRecipesDataCalled = false;
        List<Recipe> recipesResponse;

        @Override
        public void presentRecipesData(List<Recipe> recipesResponse) {
            isPresentRecipesDataCalled = true;
            recipesResponse = recipesResponse;
        }
    }

    public class SearchActivityInputSpy implements SearchInput {

        boolean isDisplayRecipesMetaDataCalled = false;
        RecipesAdapter recipesViewModelCopy;


        @Override
        public void displayRecipesMetaData(RecipesAdapter object) {
            isDisplayRecipesMetaDataCalled = true;
            recipesViewModelCopy = object;
        }
    }

    public class SpoonacularAPISpy implements SpoonacularApi {

        boolean isQueryRecipesByIngredientsCalled = false;
        boolean isQueryRecipesByNutrientsCalled = false;

        @Override
        public Call<List<Recipe>> queryRecipesByIngredients(Map<String, String> options) {
            isQueryRecipesByIngredientsCalled = true;
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
    }
}
