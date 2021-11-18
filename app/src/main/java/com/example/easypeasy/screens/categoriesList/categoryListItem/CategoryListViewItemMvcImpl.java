package com.example.easypeasy.screens.categoriesList.categoryListItem;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.easypeasy.R;
import com.example.easypeasy.networking.categories.CategorySchema;
import com.example.easypeasy.screens.common.views.BaseObservableViewMvc;
import com.google.android.material.button.MaterialButton;

public class CategoryListViewItemMvcImpl extends BaseObservableViewMvc<CategoryListViewItemMvc.Listener> implements CategoryListViewItemMvc {

    private static final String TAG = CategoryListViewItemMvcImpl.class.getSimpleName();

    private CategorySchema mCategory;
    private MaterialButton mButton;

    public CategoryListViewItemMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_categories_item, parent, false));
        mButton = findViewById(R.id.buttonCategory);
        mButton.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onCategoryClicked(mCategory);
            }
        });
    }

    @Override
    public void bindCategory(CategorySchema category) {
        mCategory = category;
        mButton.setText(mCategory.getName());
        mButton.setChecked(mCategory.isSelected());
        Log.d(TAG, "in bindCategory()");
    }
}
