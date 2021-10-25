package com.example.easypeasy;

import android.app.Application;

import com.example.easypeasy.common.dependancyInjection.CompositionRoot;

public class RecipesApplication extends Application {

    private CompositionRoot mCompositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        mCompositionRoot = new CompositionRoot();
    }

    public CompositionRoot getCompositionRoot() {
        return mCompositionRoot;
    }
}
