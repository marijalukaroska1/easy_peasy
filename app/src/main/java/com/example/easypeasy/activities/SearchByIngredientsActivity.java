package com.example.easypeasy.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easypeasy.Constants;
import com.example.easypeasy.adapters.IngredientsAdapter;
import com.example.easypeasy.configurators.Configurator;
import com.example.easypeasy.R;
import com.example.easypeasy.adapters.RecipesAdapter;
import com.example.easypeasy.events.InsertIngredientFieldListener;
import com.example.easypeasy.events.UnitsSpinnerClickListener;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.spoonacular.IngredientRequest;
import com.example.easypeasy.spoonacular.RecipesRequest;

import java.util.ArrayList;
import java.util.List;

import static com.example.easypeasy.Utils.getIngredientsUserInput;

public class SearchByIngredientsActivity extends BaseSearchActivity implements InsertIngredientFieldListener, UnitsSpinnerClickListener {

    private static final String TAG = SearchByIngredientsActivity.class.getSimpleName();
    RecyclerView recyclerView;
    Button searchButton;
    public List<Ingredient> ingredientList;

    IngredientsAdapter ingredientsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpUserInterface();

        Configurator.INSTANCE.configure(this);
    }

    private void setUpUserInterface() {
        setContentView(R.layout.activity_search_by_ingredients);
        searchButton = findViewById(R.id.searchButtonId);
        searchButton.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recyclerViewId);

        searchButton.setOnClickListener(new SearchButtonClickListener());
        ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ingredientsAdapter = new IngredientsAdapter(ingredientList, this, this, SearchByIngredientsActivity.this);
        recyclerView.setAdapter(ingredientsAdapter);

    }

    public void fetchMetaData() {
        RecipesRequest recipesRequest = new RecipesRequest(this);
        recipesRequest.isSearchByIngredients = true;
        output.fetchRecipesData(recipesRequest, ingredientList);
    }


    @Override
    public void displayRecipesMetaData(RecipesAdapter recipesAdapter) {
        recyclerView = findViewById(R.id.recyclerViewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recipesAdapter);
    }

    @Override
    public void insertItemFieldAndNotify(Ingredient ingredient) {
        if (getNumberOfInsertedIngredients() >= 10) {
            Toast.makeText(SearchByIngredientsActivity.this, R.string.message_maximum_ingredients, Toast.LENGTH_LONG).show();
        } else if (ingredient.getAmount() == 0) {
            Toast.makeText(this, R.string.insert_quantity, Toast.LENGTH_SHORT).show();
        } else {
            ingredientList.add(new Ingredient());
            //this is to remove the insert image field from the previous item
            ingredientsAdapter.notifyItemChanged(ingredientList.size() - 2, Constants.PAYLOAD_INSERT_INGREDIENT_FIELD);

            ingredientsAdapter.notifyItemInserted(ingredientList.size() - 1);
            recyclerView.scrollToPosition(ingredientList.size() - 1);

        }
    }

    @Override
    public void unitsSpinnerClick(Ingredient ingredient) {
        IngredientRequest ingredientRequest = new IngredientRequest();

        String userInput = getIngredientsUserInput(ingredientList);
        Log.d(TAG, "fetchMetaData is called getIngredientsUserInput: " + userInput);
        output.fetchIngredientData(ingredientRequest, ingredient.getId());
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

    int getNumberOfInsertedIngredients() {
        return ingredientList.size();
    }
}