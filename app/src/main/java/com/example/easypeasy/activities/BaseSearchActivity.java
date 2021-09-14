package com.example.easypeasy.activities;

import android.app.Activity;

import com.example.easypeasy.RecipesInteractorInput;
import com.example.easypeasy.adapters.RecipesAdapter;

public class BaseSearchActivity extends Activity implements SearchInput {
    public RecipesInteractorInput output;

    @Override
    public void displayRecipesMetaData(RecipesAdapter object) {
        // ignoring in Base Activity
    }
}
