package com.example.easypeasy.screens.categoriesList;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.networking.categories.CategorySchema;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;
import com.example.easypeasy.screens.common.ToolbarViewMvc;
import com.example.easypeasy.screens.common.ViewMvcFactory;
import com.example.easypeasy.screens.navDrawer.NavDrawerHelper;

import java.util.List;

public class CategoryListViewMvcImpl extends BaseObservableViewMvc<CategoryListViewMvc.Listener> implements CategoryListViewMvc, CategoriesAdapter.Listener {

    private static final String TAG = CategoryListViewMvcImpl.class.getSimpleName();
    Button continueButton;
    private final RecyclerView mRecyclerCategories;
    private final CategoriesAdapter mAdapter;
    private final Toolbar mToolbar;
    private final ToolbarViewMvc mToolbarViewMvc;
    private final NavDrawerHelper mNavDrawerHelper;

    public CategoryListViewMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory, NavDrawerHelper navDrawerHelper) {
        setRootView(inflater.inflate(R.layout.layout_category_list, parent, false));
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

        mToolbar = findViewById(R.id.toolbar);
        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);
        mNavDrawerHelper = navDrawerHelper;

        initToolbar();
    }

    private void initToolbar() {
        mToolbarViewMvc.setTitle(getContext().getString(R.string.choose_category_screen_title));
        mToolbar.addView(mToolbarViewMvc.getRootView());

        mToolbarViewMvc.enableHamburgerButtonAndListen(mNavDrawerHelper::openDrawer);
    }

    @Override
    public void bindCategories(List<CategorySchema> categories) {
        mAdapter.bindCategories(categories);
    }


    @Override
    public void onCategoryClicked(CategorySchema category) {
        mAdapter.updateUi(category);
        mAdapter.notifyDataSetChanged();
    }
}
