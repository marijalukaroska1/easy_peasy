package com.example.easypeasy.models;

public class ExtendedIngredient {

    String aisle;
    float amount;
    long id;

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ExtendedIngredient{" +
                "aisle='" + aisle + '\'' +
                ", amount=" + amount +
                ", id=" + id +
                '}';
    }
}
