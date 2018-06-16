package com.viatorfortis.bebaker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

    @SerializedName("ingredient")
    private final String mIngredient;

    @SerializedName("measure")
    private final String mMeasure;

    @SerializedName("quantity")
    private final float mQuantity;

    public String getIngredient() {
        return mIngredient;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public float getQuantity() {
        return mQuantity;
    }

    public Ingredient(String ingredient, String measure, float quantity) {
        mIngredient = ingredient;
        mMeasure = measure;
        mQuantity = quantity;
    }

    private Ingredient(Parcel parcel) {
        mIngredient = parcel.readString();
        mMeasure = parcel.readString();
        mQuantity = parcel.readFloat();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel parcel) {
            return new Ingredient(parcel);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mIngredient);
        parcel.writeString(mMeasure);
        parcel.writeFloat(mQuantity);
    }
}
