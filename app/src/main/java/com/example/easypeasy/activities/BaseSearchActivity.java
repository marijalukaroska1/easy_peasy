package com.example.easypeasy.activities;

import android.app.Activity;

import com.example.easypeasy.RecipesInteractorInput;
import com.example.easypeasy.adapters.RecipesAdapter;

import java.util.List;

public class BaseSearchActivity extends Activity implements SearchInput {
    public RecipesInteractorInput output;

    @Override
    public void displayRecipesMetaData(RecipesAdapter object) {
        // ignoring in Base Activity
    }

    @Override
    public void displayIngredientUnits(List<String> possibleUnits) {

    }
}
