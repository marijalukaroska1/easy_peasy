package com.example.easypeasy.screens.recipesList.recipesByIngredientsList;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.screens.common.controllers.BaseFragment;

import java.util.ArrayList;

public class SearchByIngredientsFragment extends BaseFragment {

    public static SearchByIngredientsFragment newInstance(ComponentName componentName) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_COMPONENT_NAME, componentName);
        SearchByIngredientsFragment fragment = new SearchByIngredientsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String SAVED_STATE_CONTROLLER = "SAVED_STATE_CONTROLLER";
    private static final String ARGS_COMPONENT_NAME = "ARGS_COMPONENT_NAME";
    private static final String LIST_INGREDIENTS = "LIST_INGREDIENTS";
    private static final String TAG = SearchByIngredientsFragment.class.getSimpleName();
    private SearchByIngredientsController mSearchByIngredientsController;
    private SearchByIngredientsViewMvc mViewMvc;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "container: " + container);
        mSearchByIngredientsController = getCompositionRoot().getSearchByIngredientsController();
        mViewMvc = getCompositionRoot().getViewMvcFactory()
                .getSearchByIngredientsViewMvc(container,
                        getCompositionRoot().getSearchManager().getSearchableInfo(getComponentName()));

        if (savedInstanceState != null) {
            Log.d(TAG, "restoreState");
            restoreControllerState(savedInstanceState);
        }

        mSearchByIngredientsController.bindView(mViewMvc);
        return mViewMvc.getRootView();
    }

    private void restoreControllerState(@NonNull Bundle savedInstanceState) {
        Log.d(TAG, "in restoreControllerState");
        mSearchByIngredientsController.restoreSavedState(
                (SearchByIngredientsController.SavedState)
                        savedInstanceState.getSerializable(SAVED_STATE_CONTROLLER),
                savedInstanceState.getParcelableArrayList(LIST_INGREDIENTS));
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "in onStart");
        mSearchByIngredientsController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "in onStop");
        mSearchByIngredientsController.onStop();
    }

    private ComponentName getComponentName() {
        return getArguments().getParcelable(ARGS_COMPONENT_NAME);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "outState: " + outState);
        Log.d(TAG, "mSearchByNutrientsController: " + mSearchByIngredientsController.getSavedState());
        Log.d(TAG, "mSearchByNutrientsController: " + mSearchByIngredientsController.getIngredientList());
        outState.putSerializable(SAVED_STATE_CONTROLLER, mSearchByIngredientsController.getSavedState());
        outState.putParcelableArrayList(LIST_INGREDIENTS, (ArrayList<? extends Parcelable>) mSearchByIngredientsController.getIngredientList());
    }
}
