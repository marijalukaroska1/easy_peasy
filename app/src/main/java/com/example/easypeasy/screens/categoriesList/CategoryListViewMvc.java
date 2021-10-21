package com.example.easypeasy.screens.categoriesList;

import com.example.easypeasy.models.Category;
import com.example.easypeasy.screens.common.ObservableListViewMvc;

import java.util.List;

public interface CategoryListViewMvc extends ObservableListViewMvc<CategoryListViewMvc.Listener> {

    interface Listener {
        void onContinueButtonClicked();
    }

    void bindCategories(List<Category> categories);
}
