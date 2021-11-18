package com.example.easypeasy.screens.categoriesList.categoryListItem;

import com.example.easypeasy.networking.categories.CategorySchema;
import com.example.easypeasy.screens.common.views.ObservableViewMvc;

public interface CategoryListViewItemMvc extends ObservableViewMvc<CategoryListViewItemMvc.Listener> {

    interface Listener {
        void onCategoryClicked(CategorySchema category);
    }

    void bindCategory(CategorySchema category);
}
