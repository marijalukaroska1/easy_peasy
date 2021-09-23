package com.example.easypeasy.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.Constants;
import com.example.easypeasy.R;
import com.example.easypeasy.Utils;
import com.example.easypeasy.adapters.IngredientsAdapter;
import com.example.easypeasy.adapters.RecipesAdapter;
import com.example.easypeasy.configurators.Configurator;
import com.example.easypeasy.events.InsertIngredientFieldListener;
import com.example.easypeasy.events.UnitsSpinnerClickListener;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.spoonacular.RecipesRequest;
import com.example.easypeasy.spoonacular.SearchIngredientsRequest;

import java.util.ArrayList;
import java.util.List;

import static com.example.easypeasy.Utils.getIngredientsUserInput;

public class SearchByIngredientsActivity extends BaseSearchActivity implements InsertIngredientFieldListener, UnitsSpinnerClickListener, IngredientFetchDataListener {

    private static final String TAG = SearchByIngredientsActivity.class.getSimpleName();
    RecyclerView recyclerViewIngredients, recyclerViewRecipes;
    Button searchButton;
    public List<Ingredient> ingredientList;
    IngredientsAdapter ingredientsAdapter;
    int ingredientFetchDataPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(): " + getIntent());
        if (getIntent() != null && Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            handleIntent(getIntent());
        } else {
            Configurator.INSTANCE.configure(this);
        }

        setUpUserInterface();
    }

    private void doSearchIngredients(String ingredientName) {
        SearchIngredientsRequest ingredientsSearchRequest = new SearchIngredientsRequest();
        output.fetchIngredientsSearchData(ingredientsSearchRequest, ingredientName);
    }

    private void setUpUserInterface() {
        setContentView(R.layout.activity_search_by_ingredients);
        searchButton = findViewById(R.id.searchButtonId);
        searchButton.setVisibility(View.VISIBLE);
        recyclerViewIngredients = findViewById(R.id.recyclerViewIngredients);
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        recyclerViewIngredients.setVisibility(View.VISIBLE);
        recyclerViewRecipes.setVisibility(View.GONE);

        searchButton.setOnClickListener(new SearchButtonClickListener());
        ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewIngredients.setLayoutManager(linearLayoutManager);
        ingredientsAdapter = new IngredientsAdapter(ingredientList, this, this, SearchByIngredientsActivity.this);
        recyclerViewIngredients.setAdapter(ingredientsAdapter);

    }

    public void fetchMetaData() {
        RecipesRequest recipesRequest = new RecipesRequest(this);
        recipesRequest.isSearchByIngredients = true;
        output.fetchRecipesData(recipesRequest, ingredientList);
    }


    @Override
    public void displayRecipesMetaData(RecipesAdapter recipesAdapter) {
        Utils.hideKeyboard(this);
        searchButton.setVisibility(View.GONE);
        recyclerViewIngredients.setVisibility(View.GONE);
        recyclerViewRecipes.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setItemPrefetchEnabled(false);
        recyclerViewRecipes.setLayoutManager(linearLayoutManager);
        recyclerViewRecipes.setAdapter(recipesAdapter);
    }

    @Override
    public void insertItemFieldAndNotify(Ingredient ingredient) {
        if (getNumberOfInsertedIngredients() >= 10) {
            Toast.makeText(SearchByIngredientsActivity.this, R.string.message_maximum_ingredients, Toast.LENGTH_LONG).show();
        } else if (ingredient.getName().isEmpty()) {
            Toast.makeText(this, R.string.insert_ingredient_name, Toast.LENGTH_SHORT).show();
        } else if (ingredient.getAmount() == 0) {
            Toast.makeText(this, R.string.insert_quantity, Toast.LENGTH_SHORT).show();
        } else {
            ingredientList.add(new Ingredient());
            //this is to remove the insert image field from the previous item
            ingredientsAdapter.notifyItemChanged(ingredientList.size() - 2, Constants.PAYLOAD_INSERT_INGREDIENT_FIELD);
            ingredientsAdapter.notifyItemInserted(ingredientList.size() - 1);
            recyclerViewIngredients.scrollToPosition(ingredientList.size() - 1);
        }
    }

    @Override
    public void unitsSpinnerClick(Ingredient ingredient) {
    }

    @Override
    public void getFetchedIngredientData() {

    }

    int getNumberOfInsertedIngredients() {
        return ingredientList.size();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            ingredientFetchDataPosition = intent.getIntExtra("ingredientPositionInAdapter", 0);
            Log.d(TAG, "doSearchIngredients() is called: " + query);
            doSearchIngredients(query);
        }
    }

    @Override
    public void displayIngredientUnits(List<String> possibleUnits) {
        String[] unitAmounts = new String[possibleUnits.size()];
        for (int i = 0; i < possibleUnits.size(); i++) {
            unitAmounts[i] = possibleUnits.get(i);
        }
        ingredientsAdapter.setIngredientPossibleUnits(unitAmounts);
        Log.d(TAG, "ingredientFetchDataPosition: " + ingredientFetchDataPosition);
        ingredientsAdapter.notifyItemChanged(ingredientFetchDataPosition, Constants.PAYLOAD_INSERT_INGREDIENT_FIELD_UNITS);
    }


    class SearchButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (getNumberOfInsertedIngredients() < 3) {
                Toast.makeText(SearchByIngredientsActivity.this, R.string.message_minimum_ingredients, Toast.LENGTH_LONG).show();
            } else {
                String userInput = getIngredientsUserInput(ingredientList);
                Log.d(TAG, "fetchMetaData is called: " + userInput);
                fetchMetaData();
            }
        }
    }
}