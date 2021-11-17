package com.example.easypeasy.screens.recipesList.recipesByNutrientsList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easypeasy.networking.nutrients.NutrientSchema;
import com.example.easypeasy.screens.common.BackPressListener;
import com.example.easypeasy.screens.common.BaseFragment;

public class SearchByNutrientsFragment extends BaseFragment implements BackPressListener {

    private static final String TAG = SearchByNutrientsFragment.class.getSimpleName();
    private SearchByNutrientsController mSearchByNutrientsController;
    private SearchByNutrientsViewMvc mSearchByNutrientsViewMvc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mSearchByNutrientsController = getCompositionRoot().getSearchByNutrientsController();
        mSearchByNutrientsViewMvc = getCompositionRoot().getViewMvcFactory().getSearchByNutrientsViewMvc(null);
        mSearchByNutrientsController.bindView(mSearchByNutrientsViewMvc);
        mSearchByNutrientsViewMvc.bindNutrient(new NutrientSchema("", 0.0f));
        return mSearchByNutrientsViewMvc.getRootView();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "in onStop");
        mSearchByNutrientsController.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "in onStart");
        mSearchByNutrientsController.onStart();
    }

    @Override
    public boolean onBackPress() {
        return mSearchByNutrientsController.onBackPress();
    }
}
