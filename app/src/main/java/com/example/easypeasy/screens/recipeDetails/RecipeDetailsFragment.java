package com.example.easypeasy.screens.recipeDetails;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easypeasy.screens.common.BackPressListener;
import com.example.easypeasy.screens.common.BaseFragment;

public class RecipeDetailsFragment extends BaseFragment implements BackPressListener, HandleIntentListener {

    private static final String TAG = RecipeDetailsFragment.class.getSimpleName();
    RecipeDetailsViewMvc mViewMvc;
    private RecipeDetailsController mRecipeDetailsController;
    private Intent mIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView");

        mViewMvc = getCompositionRoot().getViewMvcFactory().getRecipeInformationViewMvc(null);
        mRecipeDetailsController = getCompositionRoot().getRecipeDetailsController();

        mRecipeDetailsController.bindView(mViewMvc);

        return mViewMvc.getRootView();
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "in onStart");
        mRecipeDetailsController.onStart();
        if (mIntent != null) {
            mRecipeDetailsController.handleIntent(mIntent);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "in onStop");
        mRecipeDetailsController.onStop();
    }

    @Override
    public boolean onBackPress() {
        return mRecipeDetailsController.onBackPressed();
    }

    @Override
    public void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        mIntent = intent;
    }
}
