package com.example.easypeasy.screens.recipesList.recipesByIngredientsList;

import android.app.SearchableInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.screens.common.ToolbarViewMvc;
import com.example.easypeasy.screens.common.ViewMvcFactory;
import com.example.easypeasy.screens.navDrawer.BaseObservableNavViewMvc;
import com.example.easypeasy.screens.navDrawer.DrawerItem;
import com.example.easypeasy.screens.recipesList.common.RecipesListAdapter;

import java.util.List;

public class SearchByIngredientsViewMvcImpl extends BaseObservableNavViewMvc<SearchByIngredientsViewMvc.Listener> implements SearchByIngredientsViewMvc, IngredientsAdapter.Listener {

    private static final String TAG = SearchByIngredientsViewMvcImpl.class.getSimpleName();
    private final Button mSearchButton;
    private final RecyclerView mRecyclerViewIngredients, mRecyclerViewRecipes;
    private final IngredientsAdapter mIngredientsAdapter;
    private final ProgressBar progressIndicator;
    private final Toolbar mToolbar;
    private final ToolbarViewMvc mToolbarViewMvc;


    public SearchByIngredientsViewMvcImpl(LayoutInflater inflater, ViewGroup parent, SearchableInfo searchableInfo, ViewMvcFactory viewMvcFactory) {
        super(inflater, parent);
        setRootView(inflater.inflate(R.layout.activity_search_by_ingredients, parent, false));
        mSearchButton = findViewById(R.id.searchButtonId);
        mRecyclerViewIngredients = findViewById(R.id.recyclerViewIngredients);
        mRecyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        progressIndicator = findViewById(R.id.progressIndicatorId);
        mRecyclerViewIngredients.setVisibility(View.VISIBLE);
        mRecyclerViewRecipes.setVisibility(View.GONE);

        mSearchButton.setOnClickListener(v -> {
            for (Listener listeners : getListeners()) {
                listeners.searchRecipesButtonClicked();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewIngredients.setLayoutManager(linearLayoutManager);
        mIngredientsAdapter = new IngredientsAdapter(this, searchableInfo, viewMvcFactory);
        mRecyclerViewIngredients.setAdapter(mIngredientsAdapter);

        mToolbar = findViewById(R.id.toolbar);
        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);

        initToolbar();
    }

    private void initToolbar() {
        mToolbarViewMvc.setTitle(getContext().getResources().getString(R.string.search_by_ingredients));
        mToolbar.addView(mToolbarViewMvc.getRootView());

        mToolbarViewMvc.enableNavigationBackButtonAndListen(() -> {
            for (Listener listener : getListeners()) {
                listener.onNavigationUpClicked();
            }
        });
    }


    @Override
    public List<IngredientSchema> getIngredientList() {
        return mIngredientsAdapter.getIngredientList();
    }

    @Override
    public void bindRecipes(List<RecipeDetailsSchema> recipeData) {
        Log.d(TAG, "bindRecipes:");
        findViewById(R.id.bottomLayoutId).setVisibility(View.GONE);
        mRecyclerViewIngredients.setVisibility(View.GONE);
        RecipesListAdapter mRecipesListAdapter = new RecipesListAdapter(recipeData, getContext());
        if (mRecipesListAdapter.getItemCount() == 0) {
            findViewById(R.id.noRecipesFoundId).setVisibility(View.VISIBLE);
            mRecyclerViewRecipes.setVisibility(View.GONE);
        } else {
            mRecyclerViewRecipes.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            linearLayoutManager.setItemPrefetchEnabled(false);
            mRecyclerViewRecipes.setLayoutManager(linearLayoutManager);
            mRecyclerViewRecipes.setAdapter(mRecipesListAdapter);
        }
    }

    @Override
    public void insertNewIngredientField(IngredientSchema ingredient) {
        if (mIngredientsAdapter.getIngredientList().size() >= 10) {
            Toast.makeText(getContext(), R.string.message_maximum_ingredients, Toast.LENGTH_LONG).show();
        } else if (ingredient.getName().isEmpty()) {
            Toast.makeText(getContext(), R.string.insert_ingredient_name, Toast.LENGTH_SHORT).show();
        } else if (ingredient.getAmount() == 0) {
            Toast.makeText(getContext(), R.string.insert_quantity, Toast.LENGTH_SHORT).show();
        } else if (ingredient.getUnit().isEmpty()) {
            Toast.makeText(getContext(), R.string.insert_ingredient_unit, Toast.LENGTH_SHORT).show();
        } else {
            mIngredientsAdapter.bindIngredient(new IngredientSchema());
            //this is to remove the insert image field from the previous item
            mIngredientsAdapter.notifyItemChanged(mIngredientsAdapter.getIngredientList().size() - 2, Constants.PAYLOAD_INSERT_INGREDIENT_FIELD);
            mIngredientsAdapter.notifyItemInserted(mIngredientsAdapter.getIngredientList().size() - 1);
            mRecyclerViewIngredients.scrollToPosition(mIngredientsAdapter.getIngredientList().size() - 1);
        }
    }

    @Override
    public void bindIngredient(IngredientSchema ingredient) {
        mIngredientsAdapter.bindIngredient(ingredient);
    }

    @Override
    public void bindIngredientPossibleUnits(String[] unitAmounts, int ingredientFetchDataPosition) {
        mIngredientsAdapter.setIngredientPossibleUnits(unitAmounts);
        Log.d(TAG, "ingredientFetchDataPosition: " + ingredientFetchDataPosition);
        mIngredientsAdapter.notifyItemChanged(ingredientFetchDataPosition, Constants.PAYLOAD_INSERT_INGREDIENT_FIELD_UNITS);
    }

    @Override
    public void showProgressIndication() {
        progressIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndication() {
        progressIndicator.setVisibility(View.GONE);
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
