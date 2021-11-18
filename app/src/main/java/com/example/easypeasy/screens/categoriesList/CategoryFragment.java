package com.example.easypeasy.screens.categoriesList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easypeasy.screens.common.controllers.BaseFragment;

public class CategoryFragment extends BaseFragment {

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    private static final String TAG = CategoryFragment.class.getSimpleName();
    private CategoryListViewMvc mViewMvc;
    private CategoryListController mCategoryListController;

    /**
     * This method binds the UI
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "in onCreate");

        mCategoryListController = getCompositionRoot().getCategoryListController();
        mViewMvc = getCompositionRoot().getViewMvcFactory().getCategoryViewMvc(container);
        mCategoryListController.bindView(mViewMvc);
        return mViewMvc.getRootView();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "in onStop");
        mCategoryListController.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "in onStart");
        mCategoryListController.onStart();
    }
}
