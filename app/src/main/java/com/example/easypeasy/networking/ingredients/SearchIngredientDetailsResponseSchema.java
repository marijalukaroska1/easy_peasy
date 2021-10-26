package com.example.easypeasy.networking.ingredients;

import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchIngredientDetailsResponseSchema {

    @SerializedName("results")
    List<IngredientSchema> ingredientList = new ArrayList<>();
    int numberOfIngredients;
    int totalNumberOfIngredients;


    public List<IngredientSchema> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<IngredientSchema> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public int getNumberOfIngredients() {
        return numberOfIngredients;
    }


    public int getTotalNumberOfIngredients() {
        return totalNumberOfIngredients;
    }


    @Override
    public String toString() {
        return "SearchIngredientsResponse{" +
                "ingredientList=" + ingredientList +
                ", numberOfIngredients=" + numberOfIngredients +
                ", totalNumberOfIngredients=" + totalNumberOfIngredients +
                '}';
    }
}
