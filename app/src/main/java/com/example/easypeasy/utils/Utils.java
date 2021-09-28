package com.example.easypeasy.utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.models.Nutrient;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    private static final String TAG = "Utils";

    public static String getIngredientsUserInput(List<Ingredient> ingredients) {
        StringBuilder ingredientsString = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            Log.d(TAG, "ingredient: " + ingredient.toString());
            ingredientsString.append(ingredient.getName()).append(", ");
        }
        return ingredientsString.toString();
    }

    public static Map<String, Map<String, String>> mapIngredientNameWithAmountAndUnit(List<Ingredient> ingredients) {
        Map<String, Map<String, String>> ingredientsMap = new HashMap<>();
        for (Ingredient ingredient : ingredients) {
            Map<String, String> quantityMap = new HashMap<>();
            quantityMap.put("amount", String.valueOf(ingredient.getAmount()));
            quantityMap.put("unit", ingredient.getUnit());
            ingredientsMap.put(ingredient.getName(), quantityMap);
        }


        Log.d(TAG, "ingredientsMap: " + ingredientsMap.values());
        return ingredientsMap;
    }

    public static String[] getIngredientDefaultPossibleUnits() {
        List<Units> unitList = new ArrayList<Units>(EnumSet.allOf(Units.class));
        String[] unitAmounts = new String[unitList.size()];
        for (int i = 0; i < unitList.size(); i++) {
            unitAmounts[i] = unitList.get(i).getUnit();
        }
        return unitAmounts;
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

    public static String[] getPossibleNutrients(List<Nutrient> alreadyAddedNutrients) {
        List<Nutrients> nutrientList = new ArrayList<Nutrients>(EnumSet.allOf(Nutrients.class));
        List<Nutrients> filteredNutrientList = new ArrayList<>(nutrientList);
        for (Nutrients possibleNutrient : nutrientList) {
            for (Nutrient addedNutrient : alreadyAddedNutrients) {
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
