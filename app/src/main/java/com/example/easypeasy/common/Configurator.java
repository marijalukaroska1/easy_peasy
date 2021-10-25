package com.example.easypeasy.common;

import android.util.Log;

import com.example.easypeasy.screens.categoriesList.SearchCategoryRouter;
import com.example.easypeasy.screens.categoriesList.CategoryActivity;

import java.lang.ref.WeakReference;

public enum Configurator {
    INSTANCE;
    private static final String TAG = Configurator.class.getSimpleName();

    public void configure(CategoryActivity activity) {
        Log.d(TAG, "configure CategoryActivity");
        SearchCategoryRouter router = new SearchCategoryRouter();
        router.activity = new WeakReference<>(activity);

        if (activity.router == null) {
            activity.router = router;
        }
    }

}