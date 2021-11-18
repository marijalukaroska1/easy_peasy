package com.example.easypeasy.screens.recipesList.recipesByNutrientsList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.networking.nutrients.NutrientSchema;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;
import com.example.easypeasy.screens.common.ToolbarViewMvc;
import com.example.easypeasy.screens.common.ViewMvcFactory;
import com.example.easypeasy.screens.navDrawer.NavDrawerViewMvcImpl;
import com.example.easypeasy.screens.navDrawer.DrawerItem;
import com.example.easypeasy.screens.recipesList.common.RecipeClickListener;
import com.example.easypeasy.screens.recipesList.common.RecipesListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchByNutrientsViewMvcImpl extends BaseObservableViewMvc<SearchByNutrientsViewMvc.Listener>
        implements SearchByNutrientsViewMvc, NutrientsAdapter.Listener {

    private static final String TAG = SearchByNutrientsViewMvcImpl.class.getSimpleName();
    private final RecyclerView recyclerViewNutrients, recyclerViewRecipes;
    private final Button searchButton;
    private final NutrientsAdapter nutrientsAdapter;
    private final List<NutrientSchema> mNutrientList = new ArrayList<>();
    private final Toolbar mToolbar;
    private final ToolbarViewMvc mToolbarViewMvc;
    private final ViewMvcFactory mViewMvcFactory;

    public SearchByNutrientsViewMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.layout_search_by_nutrients, parent, false));
        recyclerViewNutrients = findViewById(R.id.recyclerViewNutrients);
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        recyclerViewNutrients.setVisibility(View.VISIBLE);
        recyclerViewRecipes.setVisibility(View.GONE);
        searchButton = findViewById(R.id.searchButtonId);
        searchButton.setVisibility(View.VISIBLE);
        searchButton.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.searchRecipes();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewNutrients.setLayoutManager(linearLayoutManager);
        nutrientsAdapter = new NutrientsAdapter(mNutrientList, getContext(), this);
        recyclerViewNutrients.setAdapter(nutrientsAdapter);

        mToolbar = findViewById(R.id.toolbar);
        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);

        mViewMvcFactory = viewMvcFactory;

        initToolbar();

    }

    private void initToolbar() {
        mToolbarViewMvc.setTitle(getContext().getResources().getString(R.string.search_by_nutrients));
        mToolbar.addView(mToolbarViewMvc.getRootView());

        mToolbarViewMvc.enableNavigationBackButtonAndListen(() -> {
            for (Listener listener : getListeners()) {
                listener.onNavigationUpClicked();
            }
        });
    }

    @Override
    public List<NutrientSchema> getNutrients() {
        return mNutrientList;
    }

    @Override
    public void bindNutrient(NutrientSchema nutrient) {
        mNutrientList.add(nutrient);
        //this is to remove the insert image field from the previous item
        nutrientsAdapter.notifyItemChanged(mNutrientList.size() - 2, Constants.PAYLOAD_INSERT_NUTRIENT_FIELD);
        nutrientsAdapter.notifyItemInserted(mNutrientList.size() - 1);
        recyclerViewNutrients.scrollToPosition(mNutrientList.size() - 1);
    }

    @Override
    public void removeNutrient(NutrientSchema nutrient) {
        int position = mNutrientList.indexOf(nutrient);
        Log.d(TAG, "removeItemFieldAndNotify: " + position);
        mNutrientList.remove(nutrient);
        nutrientsAdapter.notifyItemRemoved(position);
    }

    @Override
    public void bindRecipes(List<RecipeDetailsSchema> recipeData, RecipeClickListener recipeClickListener) {
        RecipesListAdapter recipesListAdapter = new RecipesListAdapter(recipeData, mViewMvcFactory, recipeClickListener);
        Log.d(TAG, "recipesAdapter: " + recipesListAdapter.getItemCount());
        findViewById(R.id.bottomLayoutId).setVisibility(View.GONE);
        recyclerViewNutrients.setVisibility(View.GONE);
        if (recipesListAdapter.getItemCount() == 0) {
            findViewById(R.id.noRecipesFoundId).setVisibility(View.VISIBLE);
            recyclerViewRecipes.setVisibility(View.GONE);
        } else {
            recyclerViewRecipes.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            linearLayoutManager.setItemPrefetchEnabled(false);
            recyclerViewRecipes.setLayoutManager(linearLayoutManager);
            recyclerViewRecipes.setAdapter(recipesListAdapter);
        }
    }

    @Override
    public void insertItemFieldAndNotify() {
        Log.d(TAG, "insertItemFieldAndNotify");
        if (checkIfOneOrMoreNutrientsAreInserted()) {
            Toast.makeText(getContext(), R.string.please_insert_nutrient, Toast.LENGTH_LONG).show();
        } else {
            bindNutrient(new NutrientSchema("", 0.0f));
        }
    }

    @Override
    public void removeItemFieldAndNotify(NutrientSchema nutrient) {
        removeNutrient(nutrient);
    }

    boolean checkIfOneOrMoreNutrientsAreInserted() {
        return getNutrients().size() > 0 && !getNutrients().get(0).getName().isEmpty() && getNutrients().get(0).getAmount() != 0.0;
    }
}
