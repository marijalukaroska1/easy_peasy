package com.example.easypeasy.screens.categoriesList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.easypeasy.common.Configurator;
import com.example.easypeasy.networking.categories.CategorySchema;
import com.example.easypeasy.screens.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends BaseActivity implements CategoryListViewMvc.Listener {
    private static final String TAG = CategoryActivity.class.getSimpleName();
    private final List<CategorySchema> categories = new ArrayList<>();
    public SearchCategoryRouter router;
    CategoryListViewMvc mViewMvc;

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "in onStop");
        mViewMvc.unregisterListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "in onStart");
        mViewMvc.registerListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "in onCreate");
        Configurator.INSTANCE.configure(this);
        categories.add(new CategorySchema("ingredients", false));
        categories.add(new CategorySchema("nutrients", false));
        mViewMvc = getCompositionRoot().getViewMvcFactory().getCategoryViewMvc(null);
        mViewMvc.bindCategories(categories);
        setContentView(mViewMvc.getRootView());
    }


    public CategoryListViewMvc getViewMvc() {
        return mViewMvc;
    }

    @Override
    public void onContinueButtonClicked() {
        Intent intent = router.determineNextScreen(categories);
        startActivity(intent);
    }

    @Override
    public void onNavigateUpAndClicked() {

    }
}
