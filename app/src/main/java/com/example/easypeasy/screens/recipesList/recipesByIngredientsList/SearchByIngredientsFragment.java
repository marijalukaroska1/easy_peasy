package com.example.easypeasy.screens.recipesList.recipesByIngredientsList;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.screens.common.BackPressListener;
import com.example.easypeasy.screens.common.BaseFragment;
import com.example.easypeasy.screens.recipeDetails.HandleIntentListener;

public class SearchByIngredientsFragment extends BaseFragment implements HandleIntentListener, BackPressListener {

    private static final String TAG = SearchByIngredientsFragment.class.getSimpleName();
    private SearchByIngredientsController mSearchByIngredientsController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchByIngredientsController = getCompositionRoot().getSearchByIngredientsController();
        SearchByIngredientsViewMvc mSearchByIngredientsViewMvc = getCompositionRoot().getViewMvcFactory().getSearchByIngredientsViewMvc(null,
                getCompositionRoot().getSearchManager().getSearchableInfo(requireActivity().getComponentName()));
        mSearchByIngredientsController.bindView(mSearchByIngredientsViewMvc);
        mSearchByIngredientsViewMvc.bindIngredient(new IngredientSchema());
        return mSearchByIngredientsViewMvc.getRootView();
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

    @Override
    public void onHandleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            mSearchByIngredientsController.setIngredientFetchDataPosition(intent.getIntExtra("ingredientPositionInAdapter", 0));
            Log.d(TAG, "doSearchIngredients() is called: " + query);
            mSearchByIngredientsController.doSearchIngredients(query);
        }
    }

    @Override
    public boolean onBackPress() {
        return mSearchByIngredientsController.onBackPress();
    }
}
