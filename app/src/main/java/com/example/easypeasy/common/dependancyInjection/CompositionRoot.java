package com.example.easypeasy.common.dependancyInjection;

import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.networking.SpoonacularApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompositionRoot {

    private Retrofit mRetrofit;
    private final Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.SPOONACULAR_BASE_URL)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return mRetrofit;
    }

    public SpoonacularApi getSpoonacularRecipesApi() {
        return getRetrofit().create(SpoonacularApi.class);
    }

}
