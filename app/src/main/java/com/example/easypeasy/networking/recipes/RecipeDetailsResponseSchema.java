package com.example.easypeasy.networking.recipes;

import com.example.easypeasy.networking.ingredients.IngredientResponseSchema;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeDetailsResponseSchema {
    long id;
    String title;
    @SerializedName("image")
    String imageUrl;
    String servings;
    int readyInMinutes;
    String summary;
    @SerializedName("extendedIngredients")
    List<IngredientResponseSchema> usedIngredients;
    String sourceUrl;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getServings() {
        return servings;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public String getSummary() {
        return summary;
    }

    public List<IngredientResponseSchema> getUsedIngredients() {
        return usedIngredients;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    @Override
    public String toString() {
        return "RecipeDataSchema{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", servings='" + servings + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", summary='" + summary + '\'' +
                ", usedIngredients=" + usedIngredients +
                ", sourceUrl='" + sourceUrl + '\'' +
                '}';
    }
}
