package com.example.easypeasy.models;

import java.util.List;

public class RecipeData {

    long id;
    String title;
    int usedIngredientCount;
    int missedIngredientCount;
    List<Ingredient> missedIngredients;
    List<Ingredient> usedIngredients;
    List<Ingredient> unusedIngredients;

    String imageUrl;
    String servings;
    int readyInMinutes;
    String summary;
    String sourceUrl;

    public RecipeData() {
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

    public List<Ingredient> getMissedIngredients() {
        return missedIngredients;
    }

    public List<Ingredient> getUsedIngredients() {
        return usedIngredients;
    }

    public List<Ingredient> getUnusedIngredients() {
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

    public void setMissedIngredients(List<Ingredient> missedIngredients) {
        this.missedIngredients = missedIngredients;
    }

    public void setUsedIngredients(List<Ingredient> usedIngredients) {
        this.usedIngredients = usedIngredients;
    }

    public void setUnusedIngredients(List<Ingredient> unusedIngredients) {
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
