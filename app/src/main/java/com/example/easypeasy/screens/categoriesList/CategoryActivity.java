package com.example.easypeasy.screens.categoriesList;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.easypeasy.common.Configurator;
import com.example.easypeasy.networking.categories.CategorySchema;
import com.example.easypeasy.screens.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends BaseActivity implements CategoryListViewMvc.Listener {
    private final List<CategorySchema> categories = new ArrayList<>();
    public SearchCategoryRouter router;
    CategoryListViewMvc mViewMvc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configurator.INSTANCE.configure(this);
        categories.add(new CategorySchema("ingredients", false));
        categories.add(new CategorySchema("nutrients", false));
        mViewMvc = getCompositionRoot().getViewMvcFactory().getCategoryViewMvc(null);
        mViewMvc.bindCategories(categories);
        mViewMvc.registerListener(this);
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
}
