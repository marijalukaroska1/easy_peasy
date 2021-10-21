package com.example.easypeasy.screens.categoriesList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.example.easypeasy.SearchCategoryRouter;
import com.example.easypeasy.configurators.Configurator;
import com.example.easypeasy.models.Category;
import com.example.easypeasy.screens.categoriesList.CategoryListViewMvcImpl;
import com.example.easypeasy.screens.categoriesList.CategoryListViewMvc;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends Activity implements CategoryListViewMvc.Listener {
    private final List<Category> categories = new ArrayList<>();
    public SearchCategoryRouter router;
    CategoryListViewMvc mViewMvc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configurator.INSTANCE.configure(this);
        categories.add(new Category("ingredients", false));
        categories.add(new Category("nutrients", false));
        mViewMvc = new CategoryListViewMvcImpl(LayoutInflater.from(this), null);
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
