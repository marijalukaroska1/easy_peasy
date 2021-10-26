package com.example.easypeasy.networking.ingredients;

//Schemas are part of networking logic
//and should not be exposed to controllers as well
public class ConvertIngredientAmountResponseSchema {

    float sourceAmount;
    String sourceUnit;
    float targetAmount;
    String targetUnit;
    String answer;
    String ingredientName;

    public ConvertIngredientAmountResponseSchema() {
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

    public void setTargetUnit(String targetUnit) {
        this.targetUnit = targetUnit;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    @Override
    public String toString() {
        return "ConvertAmountSchema{" +
                "sourceAmount=" + sourceAmount +
                ", sourceUnit='" + sourceUnit + '\'' +
                ", targetAmount=" + targetAmount +
                ", targetUnit='" + targetUnit + '\'' +
                ", answer='" + answer + '\'' +
                ", ingredientName='" + ingredientName + '\'' +
                '}';
    }
}