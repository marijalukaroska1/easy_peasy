package com.example.easypeasy.networking.ingredients;

import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;

public class IngredientWithConvertedAmountSchema {

    float sourceAmount;
    String sourceUnit;
    float targetAmount;
    String targetUnit;
    RecipeDetailsSchema recipeDetailsSchema;
    String ingredientName;

    public IngredientWithConvertedAmountSchema(RecipeDetailsSchema recipeDetailsSchema) {
        this.recipeDetailsSchema = recipeDetailsSchema;
    }

    public IngredientWithConvertedAmountSchema() {
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


    public RecipeDetailsSchema getRecipe() {
        return recipeDetailsSchema;
    }

    public void setRecipe(RecipeDetailsSchema recipeDetailsSchema) {
        this.recipeDetailsSchema = recipeDetailsSchema;
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
                ", recipe=" + recipeDetailsSchema +
                ", ingredientName='" + ingredientName + '\'' +
                '}';
    }
}
