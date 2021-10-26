package com.example.easypeasy.screens.recipeDetails;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.recipes.FetchRecipeDetailsUseCase;
import com.example.easypeasy.screens.common.BaseActivity;

public class RecipeDetailsActivity extends BaseActivity implements FetchRecipeDetailsUseCase.Listener {

    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    long recipeId = 0L;
    RecipeDetailsViewMvc mViewMvc;
    FetchRecipeDetailsUseCase mFetchRecipeDetailsUseCase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getCompositionRoot().getViewMvcFactory().getRecipeInformationViewMvc(null);
        mFetchRecipeDetailsUseCase = getCompositionRoot().getFetchRecipeInformationUseCase();
        setContentView(mViewMvc.getRootView());
        handleIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFetchRecipeDetailsUseCase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFetchRecipeDetailsUseCase.unregisterListener(this);
    }

    private void handleIntent() {
        recipeId = getIntent().getLongExtra("recipeId", 0L);
        Log.d(TAG, "fetchRecipeInformationMetaData is called with recipeId: " + recipeId);
        mViewMvc.showProgressIndication();
        mFetchRecipeDetailsUseCase.fetchRecipeDetailsAndNotify(recipeId);
    }

    @Override
    public void onFetchRecipeDetailsSuccess(RecipeDetailsSchema response) {
        mViewMvc.hideProgressIndication();
        mViewMvc.bindRecipe(response);
    }

    @Override
    public void onFetchRecipeDetailsFailure() {
        Toast.makeText(this, "Error fetching recipe information", Toast.LENGTH_LONG).show();
    }
}
