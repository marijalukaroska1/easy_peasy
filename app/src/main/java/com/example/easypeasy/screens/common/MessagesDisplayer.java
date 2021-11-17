package com.example.easypeasy.screens.common;

import android.content.Context;
import android.widget.Toast;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.recipesList.recipesByIngredientsList.SearchByIngredientsActivity;

public class MessagesDisplayer {

    private Context mContext;

    public MessagesDisplayer(Context context) {
        mContext = context;
    }

    public void showFetchRecipeDetailsFailureMsg() {
        Toast.makeText(mContext, "Error fetching recipe information", Toast.LENGTH_LONG).show();
    }

    public void showFetchRecipesFailureMsg() {
        Toast.makeText(mContext, "Fetch Recipes Failure", Toast.LENGTH_LONG).show();
    }

    public void showFetchIngredientMetaDataFailure() {
        Toast.makeText(mContext, "Fetch Ingredient Names Failure", Toast.LENGTH_LONG).show();
    }

    public void showFetchIngredientSearchMetaDataFailure() {
        Toast.makeText(mContext, "Fetch Ingredient Names Failure", Toast.LENGTH_LONG).show();
    }

    public void showMinimumIngredientsMesssage() {
        Toast.makeText(mContext, R.string.message_minimum_ingredients, Toast.LENGTH_LONG).show();
    }
}
