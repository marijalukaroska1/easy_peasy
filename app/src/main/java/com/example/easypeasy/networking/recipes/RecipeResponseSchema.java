package com.example.easypeasy.networking.recipes;

import com.example.easypeasy.networking.ingredients.IngredientSchema;

import java.util.List;

public class RecipeResponseSchema {
    long id;
    String title;
    String image;
    int usedIngredientCount;
    int missedIngredientCount;
    List<IngredientSchema> missedIngredients;
    List<IngredientSchema> usedIngredients;
    List<IngredientSchema> unusedIngredients;

    public RecipeResponseSchema() {
    }

    public long getId() {
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

    public List<IngredientSchema> getMissedIngredients() {
        return missedIngredients;
    }

    public List<IngredientSchema> getUsedIngredients() {
        return usedIngredients;
    }

    public List<IngredientSchema> getUnusedIngredients() {
        return unusedIngredients;
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
}
