package com.example.android.popularmovies;


import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500";
    private String mImage;
    private String mTitle;
    private String mRating;
    private String mPlot;
    private String mReleaseDate;
    private int mId;


    public Movies(String image, String plot, String date, String title, String rating, int id) {
        mImage = IMAGE_URL+image;
        mTitle = title;
        mRating = rating;
        mPlot = plot;
        mReleaseDate = date;
        mId = id;

    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmPlot() {
        return mPlot;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmImage() {
        return mImage;
    }

    public int getmId() {
        return mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mImage);
        dest.writeString(this.mTitle);
        dest.writeString(this.mRating);
        dest.writeString(this.mPlot);
        dest.writeString(this.mReleaseDate);
        dest.writeInt(this.mId);
    }

    protected Movies(Parcel in) {
        this.mImage = in.readString();
        this.mTitle = in.readString();
        this.mRating = in.readString();
        this.mPlot = in.readString();
        this.mReleaseDate = in.readString();
        this.mId = in.readInt();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
