package com.example.easypeasy.screens.recipeInformation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.BaseViewMvc;

public class RecipeInformationUsedIngredientsItemViewMvc extends BaseViewMvc {

    private static final String TAG = RecipeInformationUsedIngredientsItemViewMvc.class.getSimpleName();
    private final TextView textView;

    public RecipeInformationUsedIngredientsItemViewMvc(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_recipe_information_used_ingredient_item, parent, false));
        textView = findViewById(R.id.recipeTitleId);
    }

    public void bindUsedIngredientToView(String usedIngredient) {
        Log.d(TAG, "bindUsedIngredientToView: " + usedIngredient);
        textView.setText(usedIngredient);
    }
}
