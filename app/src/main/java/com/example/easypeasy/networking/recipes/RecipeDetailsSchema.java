package com.example.easypeasy.networking.recipes;

import com.example.easypeasy.networking.ingredients.IngredientSchema;

import java.util.List;

public class RecipeDetailsSchema {

    long id;
    String title;
    int usedIngredientCount;
    int missedIngredientCount;
    List<IngredientSchema> missedIngredients;
    List<IngredientSchema> usedIngredients;
    List<IngredientSchema> unusedIngredients;

    String imageUrl;
    String servings;
    int readyInMinutes;
    String summary;
    String sourceUrl;

    public RecipeDetailsSchema() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsedIngredientCount(int usedIngredientCount) {
        this.usedIngredientCount = usedIngredientCount;
    }

    public void setMissedIngredientCount(int missedIngredientCount) {
        this.missedIngredientCount = missedIngredientCount;
    }

    public void setMissedIngredients(List<IngredientSchema> missedIngredients) {
        this.missedIngredients = missedIngredients;
    }

    public void setUsedIngredients(List<IngredientSchema> usedIngredients) {
        this.usedIngredients = usedIngredients;
    }

    public void setUnusedIngredients(List<IngredientSchema> unusedIngredients) {
        this.unusedIngredients = unusedIngredients;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
