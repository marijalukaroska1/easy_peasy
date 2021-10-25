package com.example.easypeasy.models.schemas;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientSchema {

    long id;
    float amount;
    String unit = "";
    String unitShort = "";
    String name = "";
    List<String> possibleUnits;
    @SerializedName("original")
    String nameWithAmount;

    public IngredientSchema(String name, float amount) {
        this.amount = amount;
        this.name = name;
    }

    public IngredientSchema() {
    }

    public long getId() {
        return id;
    }

    public float getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public String getUnitShort() {
        return unitShort;
    }

    public String getName() {
        return name;
    }

    public List<String> getPossibleUnits() {
        return possibleUnits;
    }

    public String getNameWithAmount() {
        return nameWithAmount;
    }

    @Override
    public String toString() {
        return "IngredientSchema{" +
                "id=" + id +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                ", unitShort='" + unitShort + '\'' +
                ", name='" + name + '\'' +
                ", possibleUnits=" + possibleUnits +
                ", nameWithAmount='" + nameWithAmount + '\'' +
                '}';
    }
}
