package com.testcode.gameofthrones.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Fabian on 07/12/2016.
 */

public class GoTHouse implements Parcelable {
    @SerializedName("houseImageUrl")
    public
    String u;
    @SerializedName("houseName")
    public
    String n;
    @SerializedName("houseId")
    public
    String i;

    public GoTHouse(){}

    protected GoTHouse(Parcel in) {
        u = in.readString();
        n = in.readString();
        i = in.readString();
    }

    public static final Creator<GoTHouse> CREATOR = new Creator<GoTHouse>() {
        @Override
        public GoTHouse createFromParcel(Parcel in) {
            return new GoTHouse(in);
        }

        @Override
        public GoTHouse[] newArray(int size) {
            return new GoTHouse[size];
        }
    };

    public String getU() {
        return u;
    }

    public void setU(final String houseImageUrl) {
        this.u = houseImageUrl;
    }

    public String getN() {
        return n;
    }

    public void setN(final String houseName) {
        this.n = houseName;
    }

    public String getI() {
        return i;
    }

    public void setI(final String houseId) {
        this.i = houseId;
    }

    public GoTHouse(String houseimgurl, String housename, String houseid){
        this.u = houseimgurl;
        this.n = housename;
        this.i = houseid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(u);
        dest.writeString(n);
        dest.writeString(i);
    }
}
