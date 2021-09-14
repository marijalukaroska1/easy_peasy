package com.example.easypeasy.spoonacular;

import com.example.easypeasy.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRequest {

    Retrofit retrofit;
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.SPOONACULAR_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public SpoonacularRecipesApi buildRecipesUrl() {
        Retrofit retrofit = getRetrofit();
        return retrofit.create(SpoonacularRecipesApi.class);
    }
}
