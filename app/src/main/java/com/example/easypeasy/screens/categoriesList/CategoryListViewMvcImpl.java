package com.example.easypeasy.screens.categoriesList;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.models.Category;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;
import com.example.easypeasy.screens.common.ViewMvcFactory;

import java.util.List;

public class CategoryListViewMvcImpl extends BaseObservableViewMvc<CategoryListViewMvc.Listener> implements CategoryListViewMvc, CategoriesAdapter.Listener {

    Button continueButton;
    private RecyclerView mRecyclerCategories;
    private CategoriesAdapter mAdapter;

    public CategoryListViewMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.activity_category, parent, false));
        continueButton = findViewById(R.id.continueButtonId);
        mRecyclerCategories = findViewById(R.id.recyclerCategoriesId);

        mRecyclerCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CategoriesAdapter(inflater, this, viewMvcFactory);
        mRecyclerCategories.setAdapter(mAdapter);
        continueButton.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onContinueButtonClicked();
            }
        });
    }

    @Override
    public void bindCategories(List<Category> categories) {
        mAdapter.bindCategories(categories);
    }

    @Override
    public void onCategoryClicked(Category category) {
        mAdapter.updateUi(category);
        mAdapter.notifyDataSetChanged();
    }
}
