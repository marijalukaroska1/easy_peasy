package com.example.easypeasy.common.utils;

public enum Nutrients {
    minCarbs("minCarbs"),
    maxCarbs("maxCarbs"),
    minProtein("minProtein"),
    maxProtein("maxProtein"),
    minCalories("minCalories"),
    maxCalories("maxCalories"),
    minFat("minFat"),
    minAlcohol("minAlcohol"),
    maxAlcohol("maxAlcohol"),
    minCaffeine("minCaffeine"),
    maxCaffeine("maxCaffeine"),
    minCopper("maxCaffeine"),
    maxCopper("maxCopper"),
    minCalcium("minCalcium"),
    maxCalcium("maxCalcium"),
    minCholine("minCholine"),
    maxCholine("maxCholine"),
    minCholesterol("minCholesterol"),
    maxCholesterol("maxCholesterol"),
    minFluoride("minFluoride"),
    maxFluoride("maxFluoride"),
    minSaturatedFat("minSaturatedFat"),
    maxSaturatedFat("maxSaturatedFat"),
    minVitaminA("minVitaminA"),
    maxVitaminA("maxVitaminA"),
    minVitaminC("minVitaminC"),
    maxVitaminC("maxVitaminC"),
    minVitaminD("minVitaminD"),
    maxVitaminD("maxVitaminD"),
    minVitaminE("minVitaminE"),
    maxVitaminE("maxVitaminE"),
    minVitaminK("minVitaminK"),
    maxVitaminK("maxVitaminK"),
    minVitaminB1("minVitaminB1"),
    maxVitaminB1("maxVitaminB1");

    private final String nutrient;

    Nutrients(String nutrient) {
        this.nutrient = nutrient;
    }

    public String getNutrient() {
        return this.nutrient;
    }
}
