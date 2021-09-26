package com.example.easypeasy.spoonacular;

import android.content.Context;
import android.util.Log;

import com.example.easypeasy.utils.Constants;
import com.example.easypeasy.RecipeInformationPresenterInput;
import com.example.easypeasy.models.RecipeInformationResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeInformationRequest extends BaseRequest implements Callback<RecipeInformationResponse> {

    private static final String TAG = RecipeInformationRequest.class.getSimpleName();
    public SpoonacularRecipesApi spoonacularApi;
    RecipeInformationPresenterInput output;
    Map<String, String> map = new HashMap<>();
    Context context;
    long recipeId;

    public RecipeInformationRequest(Context context, long recipeId) {
        spoonacularApi = buildRecipesUrl();
        this.context = context;
        this.recipeId = recipeId;
    }

    public void getRecipeInformationMetaData(RecipeInformationPresenterInput output) {
        Log.d(TAG, "getRecipeInformationMetaData is called");
        this.output = output;
        map.put("apiKey", Constants.API_KEY);

        Call<RecipeInformationResponse> call = spoonacularApi.queryRecipeInformation(recipeId, map);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<RecipeInformationResponse> call, Response<RecipeInformationResponse> response) {
        Log.d(TAG, "onResponse: " + response.body());
        if (response.isSuccessful()) {
            output.presentRecipeInformation(response.body());
        }

    }

    @Override
    public void onFailure(Call<RecipeInformationResponse> call, Throwable t) {

    }
}
