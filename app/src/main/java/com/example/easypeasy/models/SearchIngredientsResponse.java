package com.example.easypeasy.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchIngredientsResponse {

    @SerializedName("results")
    List<Ingredient> ingredientList;
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

    public void setNumberOfIngredients(int numberOfIngredients) {
        this.numberOfIngredients = numberOfIngredients;
    }

    public int getTotalNumberOfIngredients() {
        return totalNumberOfIngredients;
    }

    public void setTotalNumberOfIngredients(int totalNumberOfIngredients) {
        this.totalNumberOfIngredients = totalNumberOfIngredients;
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
