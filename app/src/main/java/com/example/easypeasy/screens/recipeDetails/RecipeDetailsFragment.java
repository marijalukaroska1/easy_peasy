package com.example.easypeasy.screens.recipeDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easypeasy.screens.common.BaseFragment;

public class RecipeDetailsFragment extends BaseFragment {

    private static final String RECIPE_ID = "RECIPE_ID";

    public static RecipeDetailsFragment newInstance(long recipeId) {
        Bundle args = new Bundle();
        args.putLong(RECIPE_ID, recipeId);
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String TAG = RecipeDetailsFragment.class.getSimpleName();
    RecipeDetailsViewMvc mViewMvc;
    private RecipeDetailsController mRecipeDetailsController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView");

        mViewMvc = getCompositionRoot().getViewMvcFactory().getRecipeInformationViewMvc(null);
        mRecipeDetailsController = getCompositionRoot().getRecipeDetailsController();
        Log.d(TAG, "getRecipeId(): " + getRecipeId());
        mRecipeDetailsController.setRecipeId(getRecipeId());
        mRecipeDetailsController.bindView(mViewMvc);
        return mViewMvc.getRootView();
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "in onStart");
        mRecipeDetailsController.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "in onStop");
        mRecipeDetailsController.onStop();
    }

    private long getRecipeId() {
        return getArguments().getLong(RECIPE_ID);
    }
}
