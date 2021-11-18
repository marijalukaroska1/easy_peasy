package com.example.easypeasy.screens.recipesList.recipesByIngredientsList;

import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.screens.common.BaseFragment;

public class SearchByIngredientsFragment extends BaseFragment {

    public static SearchByIngredientsFragment newInstance(ComponentName componentName) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_COMPONENT_NAME, componentName);
        SearchByIngredientsFragment fragment = new SearchByIngredientsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String ARGS_COMPONENT_NAME = "ARGS_COMPONENT_NAME";
    private static final String TAG = SearchByIngredientsFragment.class.getSimpleName();
    private SearchByIngredientsController mSearchByIngredientsController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "container: " + container);
        mSearchByIngredientsController = getCompositionRoot().getSearchByIngredientsController();
        SearchByIngredientsViewMvc mSearchByIngredientsViewMvc = getCompositionRoot().getViewMvcFactory()
                .getSearchByIngredientsViewMvc(container,
                        getCompositionRoot().getSearchManager().getSearchableInfo(getComponentName()));
        mSearchByIngredientsViewMvc.bindIngredient(new IngredientSchema());
        mSearchByIngredientsController.bindView(mSearchByIngredientsViewMvc);
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

    private ComponentName getComponentName() {
        return getArguments().getParcelable(ARGS_COMPONENT_NAME);
    }
}
