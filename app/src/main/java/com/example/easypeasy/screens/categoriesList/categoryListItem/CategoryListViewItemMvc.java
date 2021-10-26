package com.example.easypeasy.screens.categoriesList.categoryListItem;

import com.example.easypeasy.networking.categories.CategorySchema;
import com.example.easypeasy.screens.common.ObservableListViewMvc;

public interface CategoryListViewItemMvc extends ObservableListViewMvc<CategoryListViewItemMvc.Listener> {

    interface Listener {
        void onCategoryClicked(CategorySchema category);
    }

    void bindCategory(CategorySchema category);
}
