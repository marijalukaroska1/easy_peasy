package com.example.easypeasy.models;

public class IngredientWithConvertedAmount {

    float sourceAmount;
    String sourceUnit;
    float targetAmount;
    String targetUnit;
    RecipeDetails recipeDetails;
    String ingredientName;

    public IngredientWithConvertedAmount(RecipeDetails recipeDetails) {
        this.recipeDetails = recipeDetails;
    }

    public IngredientWithConvertedAmount() {
    }

    public float getSourceAmount() {
        return sourceAmount;
    }

    public String getSourceUnit() {
        return sourceUnit;
    }

    public float getTargetAmount() {
        return targetAmount;
    }


    public String getTargetUnit() {
        return targetUnit;
    }


    public RecipeDetails getRecipe() {
        return recipeDetails;
    }

    public void setRecipe(RecipeDetails recipeDetails) {
        this.recipeDetails = recipeDetails;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setSourceAmount(float sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public void setSourceUnit(String sourceUnit) {
        this.sourceUnit = sourceUnit;
    }

    public void setTargetAmount(float targetAmount) {
        this.targetAmount = targetAmount;
    }

    public void setTargetUnit(String targetUnit) {
        this.targetUnit = targetUnit;
    }

    @Override
    public String toString() {
        return "IngredientWithConvertedAmount{" +
                "sourceAmount=" + sourceAmount +
                ", sourceUnit='" + sourceUnit + '\'' +
                ", targetAmount=" + targetAmount +
                ", targetUnit='" + targetUnit + '\'' +
                ", recipe=" + recipeDetails +
                ", ingredientName='" + ingredientName + '\'' +
                '}';
    }
}
