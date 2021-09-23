package com.example.easypeasy.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeInformationResponse {

    long id;
    String title;
    @SerializedName("image")
    String imageUrl;
    String servings;
    int readyInMinutes;
    String summary;
    @SerializedName("extendedIngredients")
    List<Ingredient> usedIngredients;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Ingredient> getUsedIngredients() {
        return usedIngredients;
    }

    public void setUsedIngredients(List<Ingredient> usedIngredients) {
        this.usedIngredients = usedIngredients;
    }

    @Override
    public String toString() {
        return "RecipeInformationResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", servings='" + servings + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", summary='" + summary + '\'' +
                ", usedIngredients=" + usedIngredients +
                '}';
    }
}
