package com.example.easypeasy;

public enum Units {

    KG("kg"),
    G("g"),
    MG("mg"),
    TSP("tsp"),
    TBSP("tbsp"),
    LARGE("large"),
    STICKS("sticks"),
    PIECE("piece"),
    FRUIT("fruit"),
    NA("N/A");

    private final String unit;

    Units(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return this.unit;
    }
}
