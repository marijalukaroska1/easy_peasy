package com.example.easypeasy.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.easypeasy.configurators.Configurator;
import com.example.easypeasy.R;
import com.example.easypeasy.adapters.RecipesAdapter;
import com.example.easypeasy.spoonacular.RecipesRequest;

public class SearchByIngredientsActivity extends BaseSearchActivity {

    private static final String TAG = SearchByIngredientsActivity.class.getSimpleName();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_ingredients);

        Configurator.INSTANCE.configure(this);

        fetchMetaData();
    }

    public void fetchMetaData() {
        RecipesRequest recipesRequest = new RecipesRequest();
        recipesRequest.isSearchByIngredients = true;
        String userInput = "apples, sugar, floor, eggs";
        Log.d(TAG, "fetchMetaData is called");
        output.fetchRecipesData(recipesRequest, userInput);
    }

    @Override
    public void displayRecipesMetaData(RecipesAdapter recipesAdapter) {
        recyclerView = findViewById(R.id.recyclerViewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recipesAdapter);
    }
}