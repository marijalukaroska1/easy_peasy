package com.example.easypeasy.screens.searchByIngredientsList;

import android.app.SearchableInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.RecipesAdapter;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;
import com.example.easypeasy.utils.Constants;

import java.util.List;

public class SearchByIngredientsViewMvcImpl extends BaseObservableViewMvc<SearchByIngredientsViewMvc.Listener> implements SearchByIngredientsViewMvc, IngredientsAdapter.Listener {

    private static final String TAG = SearchByIngredientsViewMvcImpl.class.getSimpleName();
    private Button mSearchButton;
    private RecyclerView mRecyclerViewIngredients, mRecyclerViewRecipes;
    private IngredientsAdapter mIngredientsAdapter;
    private RecipesAdapter mRecipesAdapter;
    private SearchableInfo mSearchableInfo;


    public SearchByIngredientsViewMvcImpl(LayoutInflater inflater, ViewGroup parent, SearchableInfo searchableInfo) {
        setRootView(inflater.inflate(R.layout.activity_search_by_ingredients, parent, false));
        mSearchableInfo = searchableInfo;
        mSearchButton = findViewById(R.id.searchButtonId);
        mSearchButton.setVisibility(View.VISIBLE);
        mRecyclerViewIngredients = findViewById(R.id.recyclerViewIngredients);
        mRecyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        mRecyclerViewIngredients.setVisibility(View.VISIBLE);
        mRecyclerViewRecipes.setVisibility(View.GONE);

        mSearchButton.setOnClickListener(v -> {
            for (Listener listeners : getListeners()) {
                listeners.searchRecipes();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewIngredients.setLayoutManager(linearLayoutManager);
        mIngredientsAdapter = new IngredientsAdapter(LayoutInflater.from(getContext()), this, mSearchableInfo);
        mRecyclerViewIngredients.setAdapter(mIngredientsAdapter);
    }


    @Override
    public List<Ingredient> getIngredientList() {
        return mIngredientsAdapter.getIngredientList();
    }

    @Override
    public void bindRecipes(List<Recipe> recipes) {
        Log.d(TAG, "bindRecipes:");
        mSearchButton.setVisibility(View.GONE);
        findViewById(R.id.bottomLayoutId).setVisibility(View.GONE);
        mRecyclerViewIngredients.setVisibility(View.GONE);
        mRecipesAdapter = new RecipesAdapter(recipes, getContext());
        if (mRecipesAdapter.getItemCount() == 0) {
            findViewById(R.id.noRecipesFoundId).setVisibility(View.VISIBLE);
            mRecyclerViewRecipes.setVisibility(View.GONE);
        } else {
            mRecyclerViewRecipes.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            linearLayoutManager.setItemPrefetchEnabled(false);
            mRecyclerViewRecipes.setLayoutManager(linearLayoutManager);
            mRecyclerViewRecipes.setAdapter(mRecipesAdapter);
        }
    }

    @Override
    public void insertNewIngredientButtonClicked(Ingredient ingredient) {
        if (mIngredientsAdapter.getIngredientList().size() >= 10) {
            Toast.makeText(getContext(), R.string.message_maximum_ingredients, Toast.LENGTH_LONG).show();
        } else if (ingredient.getName().isEmpty()) {
            Toast.makeText(getContext(), R.string.insert_ingredient_name, Toast.LENGTH_SHORT).show();
        } else if (ingredient.getAmount() == 0) {
            Toast.makeText(getContext(), R.string.insert_quantity, Toast.LENGTH_SHORT).show();
        } else if (ingredient.getUnit().isEmpty()) {
            Toast.makeText(getContext(), R.string.insert_ingredient_unit, Toast.LENGTH_SHORT).show();
        } else {
            mIngredientsAdapter.getIngredientList().add(new Ingredient());
            //this is to remove the insert image field from the previous item
            mIngredientsAdapter.notifyItemChanged(mIngredientsAdapter.getIngredientList().size() - 2, Constants.PAYLOAD_INSERT_INGREDIENT_FIELD);
            mIngredientsAdapter.notifyItemInserted(mIngredientsAdapter.getIngredientList().size() - 1);
            mRecyclerViewIngredients.scrollToPosition(mIngredientsAdapter.getIngredientList().size() - 1);
        }
    }

    @Override
    public void bindIngredient(Ingredient ingredient) {
        mIngredientsAdapter.bindIngredient(ingredient);
    }

    @Override
    public void bindPossibleUnits(String[] unitAmounts, int ingredientFetchDataPosition) {
        mIngredientsAdapter.setIngredientPossibleUnits(unitAmounts);
        Log.d(TAG, "ingredientFetchDataPosition: " + ingredientFetchDataPosition);
        mIngredientsAdapter.notifyItemChanged(ingredientFetchDataPosition, Constants.PAYLOAD_INSERT_INGREDIENT_FIELD_UNITS);
    }
}
