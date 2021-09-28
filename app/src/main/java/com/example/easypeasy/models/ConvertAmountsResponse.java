package com.example.easypeasy.models;

public class ConvertAmountsResponse {

    float sourceAmount;
    String sourceUnit;
    float targetAmount;
    String targetUnit;
    Recipe recipe;


    public ConvertAmountsResponse(Recipe recipe) {
        this.recipe = recipe;
    }

    public ConvertAmountsResponse() {
    }

    public float getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(float sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public String getSourceUnit() {
        return sourceUnit;
    }

    public void setSourceUnit(String sourceUnit) {
        this.sourceUnit = sourceUnit;
    }

    public float getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(float targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(String targetUnit) {
        this.targetUnit = targetUnit;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "ConvertAmountsResponse{" +
                "sourceAmount=" + sourceAmount +
                ", sourceUnit='" + sourceUnit + '\'' +
                ", targetAmount=" + targetAmount +
                ", targetUnit='" + targetUnit + '\'' +
                ", recipe='" + recipe + '\'' +
                '}';
    }
}
