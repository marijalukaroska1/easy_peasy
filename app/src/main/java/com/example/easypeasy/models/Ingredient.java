package com.example.easypeasy.models;

import java.util.List;

public class Ingredient {
    long id;
    float amount;
    String unit = "";
    String unitShort = "";
    String name = "";
    List<String> possibleUnits;

    public Ingredient(String name, float amount) {
        this.amount = amount;
        this.name = name;
    }

    public Ingredient() {
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

    public void setId(long id) {
        this.id = id;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setUnitShort(String unitShort) {
        this.unitShort = unitShort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPossibleUnits() {
        return possibleUnits;
    }

    public void setPossibleUnits(List<String> possibleUnits) {
        this.possibleUnits = possibleUnits;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                ", unitShort='" + unitShort + '\'' +
                ", name='" + name + '\'' +
                ", possibleUnits=" + possibleUnits +
                '}';
    }
}
