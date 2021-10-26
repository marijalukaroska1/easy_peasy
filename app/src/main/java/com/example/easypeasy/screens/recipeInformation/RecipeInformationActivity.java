package com.example.easypeasy.screens.recipeInformation;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.easypeasy.models.RecipeDetails;
import com.example.easypeasy.networking.FetchRecipeInformationUseCase;
import com.example.easypeasy.screens.common.BaseActivity;

public class RecipeInformationActivity extends BaseActivity implements FetchRecipeInformationUseCase.Listener {

    private static final String TAG = RecipeInformationActivity.class.getSimpleName();
    long recipeId = 0L;
    RecipeInformationViewMvc mViewMvc;
    FetchRecipeInformationUseCase mFetchRecipeInformationUseCase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getCompositionRoot().getViewMvcFactory().getRecipeInformationViewMvc(null);
        mFetchRecipeInformationUseCase = getCompositionRoot().getFetchRecipeInformationUseCase();
        setContentView(mViewMvc.getRootView());
        handleIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFetchRecipeInformationUseCase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFetchRecipeInformationUseCase.unregisterListener(this);
    }

    private void handleIntent() {
        recipeId = getIntent().getLongExtra("recipeId", 0L);
        Log.d(TAG, "fetchRecipeInformationMetaData is called with recipeId: " + recipeId);
        mViewMvc.showProgressIndication();
        mFetchRecipeInformationUseCase.fetchRecipeDetailsAndNotify(recipeId);
    }

    @Override
    public void onFetchRecipeDetailsSuccess(RecipeDetails response) {
        mViewMvc.hideProgressIndication();
        mViewMvc.bindRecipe(response);
    }

    @Override
    public void onFetchRecipeDetailsFailure() {
        Toast.makeText(this, "Error fetching recipe information", Toast.LENGTH_LONG).show();
    }
}
