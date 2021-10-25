package com.example.easypeasy.screens.categoriesList;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.easypeasy.common.Configurator;
import com.example.easypeasy.models.Category;
import com.example.easypeasy.screens.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends BaseActivity implements CategoryListViewMvc.Listener {
    private final List<Category> categories = new ArrayList<>();
    public SearchCategoryRouter router;
    CategoryListViewMvc mViewMvc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configurator.INSTANCE.configure(this);
        categories.add(new Category("ingredients", false));
        categories.add(new Category("nutrients", false));
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
