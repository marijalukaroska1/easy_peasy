package com.example.easypeasy.screens.categoriesList;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.networking.categories.CategorySchema;
import com.example.easypeasy.screens.common.ToolbarViewMvc;
import com.example.easypeasy.screens.common.ViewMvcFactory;
import com.example.easypeasy.screens.navDrawer.BaseObservableNavViewMvc;
import com.example.easypeasy.screens.navDrawer.DrawerItem;

import java.util.List;

public class CategoryListViewMvcImpl extends BaseObservableNavViewMvc<CategoryListViewMvc.Listener> implements CategoryListViewMvc, CategoriesAdapter.Listener {

    Button continueButton;
    private final RecyclerView mRecyclerCategories;
    private final CategoriesAdapter mAdapter;
    private final Toolbar mToolbar;
    private final ToolbarViewMvc mToolbarViewMvc;

    public CategoryListViewMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        super(inflater, parent);
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

        mToolbar = findViewById(R.id.toolbar);
        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);

        initToolbar();
    }

    private void initToolbar() {
        mToolbarViewMvc.setTitle(getContext().getString(R.string.choose_category_screen_title));
        mToolbar.addView(mToolbarViewMvc.getRootView());


        mToolbarViewMvc.enableHamburgerButtonAndListen(() -> openDrawer());
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

    @Override
    protected void onDrawerItemClick(DrawerItem item) {
        for (Listener listener : getListeners()) {
            switch (item) {
                case SELECT_SEARCH_BY_INGREDIENTS:
                    listener.selectSearchByIngredientsItemClicked();
                case SELECT_SEARCH_BY_NUTRIENTS:
                    listener.selectSearchByNutrientsItemClicked();
            }
        }
    }
}
