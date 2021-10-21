package com.example.easypeasy.screens.searchByNutrientsList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.models.Nutrient;
import com.example.easypeasy.models.Recipe;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;
import com.example.easypeasy.screens.common.RecipesAdapter;
import com.example.easypeasy.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SearchByNutrientsViewMvcImpl extends BaseObservableViewMvc<SearchByNutrientsViewMvc.Listener> implements SearchByNutrientsViewMvc, NutrientsAdapter.Listener {

    private static final String TAG = SearchByNutrientsViewMvcImpl.class.getSimpleName();
    private RecyclerView recyclerViewNutrients, recyclerViewRecipes;
    private Button searchButton;
    private NutrientsAdapter nutrientsAdapter;
    private List<Nutrient> mNutrientList = new ArrayList<>();
    private List<Listener> mListeners = new ArrayList<>(1);

    public SearchByNutrientsViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.activity_search_by_nutrients, parent, false));
        recyclerViewNutrients = findViewById(R.id.recyclerViewNutrients);
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        recyclerViewNutrients.setVisibility(View.VISIBLE);
        recyclerViewRecipes.setVisibility(View.GONE);
        searchButton = findViewById(R.id.searchButtonId);
        searchButton.setVisibility(View.VISIBLE);
        searchButton.setOnClickListener(v -> {
            for (Listener listener : mListeners) {
                listener.searchRecipes();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewNutrients.setLayoutManager(linearLayoutManager);
        nutrientsAdapter = new NutrientsAdapter(mNutrientList, getContext(), this);
        recyclerViewNutrients.setAdapter(nutrientsAdapter);
    }

    @Override
    public List<Nutrient> getNutrients() {
        return null;
    }

    @Override
    public void bindNutrient(Nutrient nutrient) {
        mNutrientList.add(nutrient);
        //this is to remove the insert image field from the previous item
        nutrientsAdapter.notifyItemChanged(mNutrientList.size() - 2, Constants.PAYLOAD_INSERT_NUTRIENT_FIELD);
        nutrientsAdapter.notifyItemInserted(mNutrientList.size() - 1);
        recyclerViewNutrients.scrollToPosition(mNutrientList.size() - 1);
    }

    @Override
    public void removeNutrient(Nutrient nutrient) {
        int position = mNutrientList.indexOf(nutrient);
        Log.d(TAG, "removeItemFieldAndNotify: " + position);
        mNutrientList.remove(nutrient);
        nutrientsAdapter.notifyItemRemoved(position);
    }

    @Override
    public void bindRecipes(List<Recipe> recipes) {
        RecipesAdapter recipesAdapter = new RecipesAdapter(recipes, getContext());
        Log.d(TAG, "recipesAdapter: " + recipesAdapter.getItemCount());
        searchButton.setVisibility(View.GONE);
        recyclerViewNutrients.setVisibility(View.GONE);
        findViewById(R.id.bottomLayoutId).setVisibility(View.GONE);
        if (recipesAdapter.getItemCount() == 0) {
            findViewById(R.id.noRecipesFoundId).setVisibility(View.VISIBLE);
            recyclerViewRecipes.setVisibility(View.GONE);
        } else {
            recyclerViewRecipes.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            linearLayoutManager.setItemPrefetchEnabled(false);
            recyclerViewRecipes.setLayoutManager(linearLayoutManager);
            recyclerViewRecipes.setAdapter(recipesAdapter);
        }
    }

    @Override
    public void insertItemFieldAndNotify() {
        Log.d(TAG, "insertItemFieldAndNotify");
        if (checkIfOneOrMoreNutrientsAreInserted()) {
            Toast.makeText(getContext(), R.string.please_insert_nutrient, Toast.LENGTH_LONG).show();
        } else {
            bindNutrient(new Nutrient("", 0.0f));
        }
    }

    @Override
    public void removeItemFieldAndNotify(Nutrient nutrient) {
        removeNutrient(nutrient);
    }

    boolean checkIfOneOrMoreNutrientsAreInserted() {
        return getNutrients().size() > 0 && !getNutrients().get(0).getName().isEmpty() && getNutrients().get(0).getAmount() != 0.0;
    }
}
