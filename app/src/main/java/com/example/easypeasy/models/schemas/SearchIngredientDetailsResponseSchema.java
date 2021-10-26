package com.example.easypeasy.models.schemas;

import com.example.easypeasy.models.Ingredient;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchIngredientDetailsResponseSchema {

    @SerializedName("results")
    List<Ingredient> ingredientList = new ArrayList<>();
    int numberOfIngredients;
    int totalNumberOfIngredients;


    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
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
