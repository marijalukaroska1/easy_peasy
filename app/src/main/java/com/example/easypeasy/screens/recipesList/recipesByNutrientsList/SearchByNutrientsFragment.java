package com.example.easypeasy.screens.recipesList.recipesByNutrientsList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easypeasy.networking.nutrients.NutrientSchema;
import com.example.easypeasy.screens.common.controllers.BaseFragment;

public class SearchByNutrientsFragment extends BaseFragment {

    public static SearchByNutrientsFragment newInstance() {
        return new SearchByNutrientsFragment();
    }

    private static final String SAVED_STATE_CONTROLLER = "SAVED_STATE_CONTROLLER";
    private static final String TAG = SearchByNutrientsFragment.class.getSimpleName();
    private SearchByNutrientsController mSearchByNutrientsController;
    private SearchByNutrientsViewMvc mViewMvc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "container: " + container);
        mSearchByNutrientsController = getCompositionRoot().getSearchByNutrientsController();
        mViewMvc = getCompositionRoot().getViewMvcFactory().getSearchByNutrientsViewMvc(container);

        if (savedInstanceState != null) {
            Log.d(TAG, "restoreState");
            restoreControllerState(savedInstanceState);
        }

        mSearchByNutrientsController.bindView(mViewMvc);
        mViewMvc.bindNutrient(new NutrientSchema("", 0.0f));
        return mViewMvc.getRootView();
    }

    private void restoreControllerState(@NonNull Bundle savedInstanceState) {
        mSearchByNutrientsController.restoreSavedState(
                (SearchByNutrientsController.SavedState)
                        savedInstanceState.getSerializable(SAVED_STATE_CONTROLLER));
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "outState: " + outState);
        Log.d(TAG, "mSearchByNutrientsController: " + mSearchByNutrientsController);
        outState.putSerializable(SAVED_STATE_CONTROLLER, mSearchByNutrientsController.getSavedState());
    }
}
