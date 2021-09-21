package com.example.easypeasy;

import android.util.Log;

import com.example.easypeasy.models.Ingredient;

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

    public static Map<String, Map<String, Float>> mapIngredientsNamesAndAmounts(List<Ingredient> ingredients) {
        Map<String, Map<String, Float>> ingredientsMap = new HashMap<>();
        for (Ingredient ingredient : ingredients) {
            Map<String, Float> quantityMap = new HashMap<>();
            quantityMap.put(ingredient.getUnit(), ingredient.getAmount());
            ingredientsMap.put(ingredient.getName(), quantityMap);
        }

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
}
