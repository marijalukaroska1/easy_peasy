package com.example.easypeasy.screens.categoriesList;

import com.example.easypeasy.networking.categories.CategorySchema;
import com.example.easypeasy.screens.common.ObservableListViewMvc;
import com.example.easypeasy.screens.navDrawer.NavDrawerViewMvc;

import java.util.List;

public interface CategoryListViewMvc extends ObservableListViewMvc<CategoryListViewMvc.Listener> {

    interface Listener {
        void onContinueButtonClicked();
        void onNavigateUpAndClicked();
    }

    void bindCategories(List<CategorySchema> categories);

}
