package com.example.easypeasy.networking.ingredients;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class IngredientSchema implements Parcelable {
    long id;
    float amount;
    String unit = "";
    String unitShort = "";
    String name = "";
    List<String> possibleUnits = new ArrayList<>();
    String nameWithAmount = "";

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

    public String getNameWithAmount() {
        return nameWithAmount;
    }

    public void setNameWithAmount(String nameWithAmount) {
        this.nameWithAmount = nameWithAmount;
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
                ", nameWithAmount='" + nameWithAmount + '\'' +
                '}';
    }

    /**
     * Creates an ingredient object based on the provided parcel.
     *
     * @param in The parcel.
     */
    public IngredientSchema(Parcel in) {
        this.id = in.readLong();
        this.amount = in.readFloat();
        this.unit = in.readString();
        this.unitShort = in.readString();
        this.name = in.readString();
        in.readList(this.possibleUnits, IngredientSchema.class.getClassLoader());
        this.nameWithAmount = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeFloat(this.amount);
        dest.writeString(this.unit);
        dest.writeString(this.unitShort);
        dest.writeString(this.name);
        dest.writeList(this.possibleUnits);
        dest.writeString(this.nameWithAmount);
    }

    public static final Parcelable.Creator<IngredientSchema> CREATOR = new Parcelable.Creator<IngredientSchema>() {
        public IngredientSchema createFromParcel(Parcel in) {
            return new IngredientSchema(in);
        }

        public IngredientSchema[] newArray(int size) {
            return new IngredientSchema[size];
        }
    };
}
