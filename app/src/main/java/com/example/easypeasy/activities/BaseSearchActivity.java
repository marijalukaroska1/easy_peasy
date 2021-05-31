package com.example.easypeasy.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easypeasy.RecipesInteractorInput;
import com.example.easypeasy.adapters.RecipesAdapter;

public class BaseSearchActivity extends AppCompatActivity implements SearchInput {
    public RecipesInteractorInput output;

    @Override
    public void displayRecipesMetaData(RecipesAdapter object) {
        // ignoring in Base Activity
    }
}
