package com.example.easypeasy.models;

import java.util.List;

public class Recipe {

    int id;
    String title;
    String image;
    int usedIngredientCount;
    int missedIngredientCount;

    List<Ingredient> missedIngredients;
    List<Ingredient> usedIngredients;
    List<Ingredient> unusedIngredients;


    public Recipe() {
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", usedIngredientCount=" + usedIngredientCount +
                ", missedIngredientCount=" + missedIngredientCount +
                ", missedIngredients=" + missedIngredients +
                ", usedIngredients=" + usedIngredients +
                ", unusedIngredients=" + unusedIngredients +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public int getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public List<Ingredient> getMissedIngredients() {
        return missedIngredients;
    }

    public List<Ingredient> getUsedIngredients() {
        return usedIngredients;
    }

    public List<Ingredient> getUnusedIngredients() {
        return unusedIngredients;
    }
}
