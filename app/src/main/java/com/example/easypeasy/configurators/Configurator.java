package com.example.easypeasy.configurators;

import android.util.Log;

import com.example.easypeasy.RecipeInformationInteractor;
import com.example.easypeasy.RecipeInformationPresenter;
import com.example.easypeasy.RecipesInteractor;
import com.example.easypeasy.RecipesPresenter;
import com.example.easypeasy.SearchCategoryRouter;
import com.example.easypeasy.activities.BaseSearchActivity;
import com.example.easypeasy.activities.CategoryActivity;
import com.example.easypeasy.activities.RecipeInformationActivity;
import com.example.easypeasy.activities.RecipeInformationInput;
import com.example.easypeasy.activities.SearchInput;

import java.lang.ref.WeakReference;

public enum Configurator {
    INSTANCE;

    private static final String TAG = Configurator.class.getSimpleName();

    public void configure(BaseSearchActivity activity) {
        Log.d(TAG, "configure BaseSearchActivity");
        RecipesPresenter presenter = new RecipesPresenter();
        presenter.output = new WeakReference<SearchInput>(activity);

        RecipesInteractor interactor = new RecipesInteractor();
        interactor.output = presenter;

        if (activity.output == null) {
            activity.output = interactor;
        }
    }


    public void configure(CategoryActivity activity) {
        Log.d(TAG, "configure CategoryActivity");
        SearchCategoryRouter router = new SearchCategoryRouter();
        router.activity = new WeakReference<>(activity);

        if (activity.router == null) {
            activity.router = router;
        }
    }

    public void configure(RecipeInformationActivity activity) {
        Log.d(TAG, "configure RecipeInformationActivity");
        RecipeInformationPresenter presenter = new RecipeInformationPresenter();
        presenter.output = new WeakReference<>(activity);

        RecipeInformationInteractor interactor = new RecipeInformationInteractor();
        interactor.output = presenter;

        if (activity.output == null) {
            activity.output = interactor;
        }
    }
}