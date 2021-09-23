package com.example.easypeasy.spoonacular;

import android.content.Context;
import android.util.Log;

import com.example.easypeasy.Constants;
import com.example.easypeasy.RecipesInteractorInput;
import com.example.easypeasy.RecipesPresenterInput;
import com.example.easypeasy.Utils;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesRequest extends BaseRequest implements Callback<List<Recipe>> {

    private static final String TAG = RecipesRequest.class.getSimpleName();
    private static int numberOfConvertAmountRequestsMade = 0;
    public boolean isSearchByIngredients = false;
    public SpoonacularRecipesApi spoonacularApi;
    List<Ingredient> ingredientList;

    RecipesPresenterInput output;
    RecipesInteractorInput interactor;
    Map<String, String> map = new HashMap<>();
    Context context;

    public RecipesRequest(Context context) {
        spoonacularApi = buildRecipesUrl();
        this.context = context;
    }

    public void getRecipesByIngredients(List<Ingredient> ingredientsList, RecipesPresenterInput output, RecipesInteractorInput interactor) {
        this.interactor = interactor;
        this.output = output;
        ingredientList = ingredientsList;
        String ingredients = Utils.getIngredientsUserInput(ingredientsList);
        Log.d(TAG, "getRecipesByIngredients is called: " + ingredients);

        map.put("apiKey", Constants.API_KEY);
        map.put("ingredients", ingredients);

        Call<List<Recipe>> call = spoonacularApi.queryRecipesByIngredients(map);
        call.enqueue(this);
    }

    public void getRecipesByNutrients(String nutrients) {
        map.put("nutrients", nutrients);
        map.put("apiKey", Constants.API_KEY);

        Call<List<Recipe>> call = spoonacularApi.queryRecipesByNutrients(map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        Log.d(TAG, "onResponse: " + response.isSuccessful());
        if (response.isSuccessful()) {
            Log.d(TAG, "onResponse successful: " + response.message());
            List<Recipe> recipes = response.body();
            recipes.forEach(recipe -> Log.d(TAG, "onResponse successful: " + recipe));
            filterRecipesByIngredientsAmount(recipes);
            //output.presentRecipesData(filteredRecipes, context);
        } else {
            Log.d(TAG, "onResponse error: " + response.code());
        }
    }

    private void filterRecipesByIngredientsAmount(List<Recipe> recipes) {
        List<Recipe> filteredRecipes = new ArrayList(recipes);
        Map<String, Map<String, String>> inputIngredientsMap = Utils.mapIngredientNameWithAmountAndUnit(ingredientList);
        numberOfConvertAmountRequestsMade = 0;
        for (Recipe recipe : recipes) {
            Log.d(TAG, "recipe name: " + recipe.getTitle());
            Log.d(TAG, "=================================================");
            for (Ingredient responseIngredient : recipe.getUsedIngredients()) {
                if (inputIngredientsMap.containsKey(responseIngredient.getName())) {
                    Log.d(TAG, "responseIngredient name: " + responseIngredient.getName());
                    Log.d(TAG, "input ingredient data: " + inputIngredientsMap.get(responseIngredient.getName()));
                    Log.d(TAG, "response ingredient data: " + responseIngredient.getAmount() + " unit: " + responseIngredient.getUnit());
                    if (!responseIngredient.getUnit().isEmpty() && !responseIngredient.getUnit().equalsIgnoreCase(inputIngredientsMap.get(responseIngredient.getName()).get("unit"))) {
                        Log.d(TAG, "different unit for both ingredients: " + inputIngredientsMap.get(responseIngredient.getName()).get("unit") + " " + responseIngredient.getUnit());
                        ConvertAmountsRequest request = new ConvertAmountsRequest(context);
                        numberOfConvertAmountRequestsMade++;
                        Log.d(TAG, "convertAmountRequestsNumber: " + numberOfConvertAmountRequestsMade);
                        interactor.convertAmountsAndUnitsRequest(request, responseIngredient.getName(), responseIngredient.getAmount(), responseIngredient.getUnit(), inputIngredientsMap.get(responseIngredient.getName()), filteredRecipes, recipe);
                    } else {
                        Log.d(TAG, "same unit for both ingredients: " + inputIngredientsMap.get(responseIngredient.getName()).get("unit") + " " + responseIngredient.getUnit());
                        if (responseIngredient.getAmount() > Float.parseFloat(inputIngredientsMap.get(responseIngredient.getName()).get("amount"))) {
                            filteredRecipes.remove(recipe);
                        }
                    }
                }
            }
            Log.d(TAG, "=================================================");
        }
        output.presentRecipesData(filteredRecipes, context);
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
    }

    public static synchronized int getConvertAmountRequestsNumber() {
        return numberOfConvertAmountRequestsMade;
    }
}
