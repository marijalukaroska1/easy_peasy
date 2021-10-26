package com.example.easypeasy.common.utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.networking.nutrients.NutrientSchema;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    private static final String TAG = "Utils";

    public static String getIngredientsUserInput(List<IngredientSchema> ingredients) {
        StringBuilder ingredientsString = new StringBuilder();
        for (IngredientSchema ingredient : ingredients) {
            Log.d(TAG, "ingredient: " + ingredient.toString());
            ingredientsString.append(ingredient.getName()).append(", ");
        }
        return ingredientsString.toString();
    }

    public static Map<String, Map<String, String>> mapIngredientNameWithAmountAndUnit(List<IngredientSchema> ingredients) {
        Map<String, Map<String, String>> ingredientsMap = new HashMap<>();
        for (IngredientSchema ingredient : ingredients) {
            Map<String, String> quantityMap = new HashMap<>();
            quantityMap.put("amount", String.valueOf(ingredient.getAmount()));
            quantityMap.put("unit", ingredient.getUnit());
            ingredientsMap.put(ingredient.getName(), quantityMap);
        }


        Log.d(TAG, "ingredientsMap: " + ingredientsMap.values());
        return ingredientsMap;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String[] getPossibleNutrients(List<NutrientSchema> alreadyAddedNutrients) {
        List<Nutrients> nutrientList = new ArrayList<Nutrients>(EnumSet.allOf(Nutrients.class));
        List<Nutrients> filteredNutrientList = new ArrayList<>(nutrientList);
        for (Nutrients possibleNutrient : nutrientList) {
            for (NutrientSchema addedNutrient : alreadyAddedNutrients) {
                if (possibleNutrient.getNutrient().equalsIgnoreCase(addedNutrient.getName())) {
                    filteredNutrientList.remove(possibleNutrient);
                }
            }
        }
        String[] nutrients = new String[filteredNutrientList.size()];
        for (int i = 0; i < filteredNutrientList.size(); i++) {
            nutrients[i] = filteredNutrientList.get(i).getNutrient();
        }
        return nutrients;
    }
}
