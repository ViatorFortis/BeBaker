package com.viatorfortis.bebaker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {

    @SerializedName("id")
    private final int mId;

    @SerializedName("shortDescription")
    private final String mShortDescription;

    @SerializedName("description")
    private final String mDescription;

    @SerializedName("videoURL")
    private final String mVideoUrl;

    @SerializedName("thumbnailURL")
    private final String mThumbnailUrl;

    public int getId() {
        return mId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public Step(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        mId = id;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }

    private Step(Parcel parcel) {
        mId = parcel.readInt();
        mShortDescription = parcel.readString();
        mDescription = parcel.readString();
        mVideoUrl = parcel.readString();
        mThumbnailUrl = parcel.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step> () {
        @Override
        public Step createFromParcel(Parcel parcel) {
            return new Step(parcel);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoUrl);
        parcel.writeString(mThumbnailUrl);
    }
}
