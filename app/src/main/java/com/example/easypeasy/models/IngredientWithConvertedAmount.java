package com.example.easypeasy.models;

public class IngredientWithConvertedAmount {

    float sourceAmount;
    String sourceUnit;
    float targetAmount;
    String targetUnit;
    RecipeData recipeData;
    String ingredientName;

    public IngredientWithConvertedAmount(RecipeData recipeData) {
        this.recipeData = recipeData;
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


    public RecipeData getRecipe() {
        return recipeData;
    }

    public void setRecipe(RecipeData recipeData) {
        this.recipeData = recipeData;
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
                ", recipe=" + recipeData +
                ", ingredientName='" + ingredientName + '\'' +
                '}';
    }
}
