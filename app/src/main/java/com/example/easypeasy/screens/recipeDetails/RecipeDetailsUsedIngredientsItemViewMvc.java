package com.example.easypeasy.screens.recipeDetails;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.BaseViewMvc;

public class RecipeDetailsUsedIngredientsItemViewMvc extends BaseViewMvc {

    private static final String TAG = RecipeDetailsUsedIngredientsItemViewMvc.class.getSimpleName();
    private final TextView textView;

    public RecipeDetailsUsedIngredientsItemViewMvc(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_recipe_information_used_ingredient_item, parent, false));
        textView = findViewById(R.id.recipeTitleId);
    }

    public void bindUsedIngredientToView(String usedIngredient) {
        Log.d(TAG, "bindUsedIngredientToView: " + usedIngredient);
        textView.setText(usedIngredient);
    }
}
