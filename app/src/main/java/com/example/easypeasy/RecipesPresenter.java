package com.example.easypeasy;

import com.example.easypeasy.activities.SearchInput;
import com.example.easypeasy.adapters.RecipesAdapter;
import com.example.easypeasy.models.Recipe;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecipesPresenter implements RecipesPresenterInput {

    public WeakReference<SearchInput> output;

    @Override
    public void presentRecipesData(List<Recipe> recipesResponse) {
        RecipesAdapter recipesAdapter = new RecipesAdapter(recipesResponse);
        output.get().displayRecipesMetaData(recipesAdapter);
    }
}
