package com.example.easypeasy.models;

public class Nutrient {
    String name;
    float amount;

    public Nutrient() {
    }

    public Nutrient(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Nutrient{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
