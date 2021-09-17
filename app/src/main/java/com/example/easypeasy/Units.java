package com.example.easypeasy;

public enum Units {

    KG("kg"),
    G("g"),
    MG("mg"),
    TSP("tsp"),
    TBSP("tbsp"),
    CUP("cup"),
    PIECE("piece"),
    SQUARES("squares");

    private final String unit;

    Units(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return this.unit;
    }
}
