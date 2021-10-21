package com.example.easypeasy.screens.categoriesList;

import com.example.easypeasy.models.Category;
import com.example.easypeasy.screens.common.ObservableListViewMvc;

public interface CategoryListViewItemMvc extends ObservableListViewMvc<CategoryListViewItemMvc.Listener> {

    interface Listener {
        void onCategoryClicked(Category category);
    }

    void bindCategory(Category category);
}
