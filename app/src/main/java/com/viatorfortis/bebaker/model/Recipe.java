package com.viatorfortis.bebaker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe
        implements Parcelable {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("ingredients")
    private List<Ingredient> mIngredientList;

    @SerializedName("steps")
    private ArrayList<Step> mStepList;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }


    public List<Ingredient> getIngredientList() {
        return mIngredientList;
    }

    public ArrayList<Step> getStepList() {
        return mStepList;
    }


    public Recipe(int id, String name, List<Ingredient> ingredientList, ArrayList<Step> stepList) {
        mId = id;
        mName = name;
        mIngredientList = ingredientList;
        mStepList = stepList;
    }

    private Recipe(Parcel parcel) {
        mId = parcel.readInt();
        mName = parcel.readString();

        mIngredientList = new ArrayList<Ingredient>();
        parcel.readList(mIngredientList, Ingredient.class.getClassLoader() );
        mStepList = new ArrayList<Step>();
        parcel.readList(mStepList, Step.class.getClassLoader() );
    }


    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe> () {
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeList(mIngredientList);
        parcel.writeList(mStepList);
    }
}
