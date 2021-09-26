package com.example.easypeasy.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.adapters.NutrientsAdapter;
import com.example.easypeasy.adapters.RecipesAdapter;
import com.example.easypeasy.configurators.Configurator;
import com.example.easypeasy.events.FieldChangeListener;
import com.example.easypeasy.models.Nutrient;
import com.example.easypeasy.spoonacular.RecipesRequest;
import com.example.easypeasy.utils.Constants;
import com.example.easypeasy.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SearchByNutrientsActivity extends BaseSearchActivity implements FieldChangeListener {
    private static final String TAG = SearchByNutrientsActivity.class.getSimpleName();
    RecyclerView recyclerViewNutrients, recyclerViewRecipes;
    Button searchButton;
    public List<Nutrient> nutrientList;
    NutrientsAdapter nutrientsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configurator.INSTANCE.configure(this);
        setUpUserInterface();
    }

    private void setUpUserInterface() {
        setContentView(R.layout.activity_search_by_nutrients);
        recyclerViewNutrients = findViewById(R.id.recyclerViewNutrients);
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        recyclerViewNutrients.setVisibility(View.VISIBLE);
        recyclerViewRecipes.setVisibility(View.GONE);
        searchButton = findViewById(R.id.searchButtonId);
        searchButton.setVisibility(View.VISIBLE);
        searchButton.setOnClickListener(new SearchButtonClickListener());
        nutrientList = new ArrayList<>();
        nutrientList.add(new Nutrient("", 0.0f));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewNutrients.setLayoutManager(linearLayoutManager);
        nutrientsAdapter = new NutrientsAdapter(nutrientList, SearchByNutrientsActivity.this, this);
        recyclerViewNutrients.setAdapter(nutrientsAdapter);
    }

    public void fetchMetaData() {
        RecipesRequest recipesRequest = new RecipesRequest(this);
        recipesRequest.isSearchByIngredients = false;
        output.fetchRecipesData(recipesRequest, null, nutrientList);
    }


    boolean checkIfOneOrMoreNutrientsAreInserted() {
        return nutrientList.size() > 0 && !nutrientList.get(0).getName().isEmpty() && nutrientList.get(0).getAmount() != 0.0;
    }

    @Override
    public void insertItemFieldAndNotify(Object newField) {
        Log.d(TAG, "insertItemFieldAndNotify");
        if (checkIfOneOrMoreNutrientsAreInserted()) {
            Toast.makeText(SearchByNutrientsActivity.this, R.string.please_insert_nutrient, Toast.LENGTH_LONG).show();
        } else {
            nutrientList.add(new Nutrient("", 0.0f));
            //this is to remove the insert image field from the previous item
            nutrientsAdapter.notifyItemChanged(nutrientList.size() - 2, Constants.PAYLOAD_INSERT_NUTRIENT_FIELD);
            nutrientsAdapter.notifyItemInserted(nutrientList.size() - 1);
            recyclerViewNutrients.scrollToPosition(nutrientList.size() - 1);
        }
    }

    @Override
    public void removeItemFieldAndNotify(Object field) {
        Nutrient nutrient = (Nutrient) field;
        int position = nutrientList.indexOf(nutrient);
        Log.d(TAG, "removeItemFieldAndNotify: " + position);
        nutrientList.remove(nutrient);
        nutrientsAdapter.notifyItemRemoved(position);
    }

    class SearchButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "fetchMetaData is called");
            fetchMetaData();
        }
    }

    @Override
    public void displayRecipesMetaData(RecipesAdapter recipesAdapter) {
        Log.d(TAG, "recipesAdapter: " + recipesAdapter.getItemCount());
        Utils.hideKeyboard(this);
        searchButton.setVisibility(View.GONE);
        recyclerViewNutrients.setVisibility(View.GONE);
        findViewById(R.id.bottomLayoutId).setVisibility(View.GONE);
        if (recipesAdapter.getItemCount() == 0) {
            findViewById(R.id.noRecipesFoundId).setVisibility(View.VISIBLE);
            recyclerViewRecipes.setVisibility(View.GONE);
        } else {
            recyclerViewRecipes.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            linearLayoutManager.setItemPrefetchEnabled(false);
            recyclerViewRecipes.setLayoutManager(linearLayoutManager);
            recyclerViewRecipes.setAdapter(recipesAdapter);
        }
    }
}
