package com.testcode.gameofthrones.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Fabian on 07/12/2016.
 */

public class GoTCharacter implements Parcelable {
    @SerializedName("name")
    public
    String n;
    @SerializedName("imageUrl")
    public
    String iu;
    @SerializedName("description")
    public
    String d;
    @SerializedName("houseImageUrl")
    public
    String hu;
    @SerializedName("houseName")
    public
    String hn;
    @SerializedName("houseId")
    public
    String hi;

    protected GoTCharacter(Parcel in) {
        n = in.readString();
        iu = in.readString();
        d = in.readString();
        hu = in.readString();
        hn = in.readString();
        hi = in.readString();
    }

    public static final Creator<GoTCharacter> CREATOR = new Creator<GoTCharacter>() {
        @Override
        public GoTCharacter createFromParcel(Parcel in) {
            return new GoTCharacter(in);
        }

        @Override
        public GoTCharacter[] newArray(int size) {
            return new GoTCharacter[size];
        }
    };

    public String getHu() {
        return hu;
    }

    public void setHu(final String s) {
        this.hu = s;
    }

    public String getHn() {
        return hn;
    }

    public void setHn(final String s) {
        this.hn = s;
    }

    public String getHi() {
        return hi;
    }

    public void setHi(final String s) {
        this.hi = s;
    }

    public String getN() {
        return n;
    }

    public void setN(final String s) {
        this.n = s;
    }

    public String getIu() {
        return iu;
    }

    public void setIu(final String s) {
        this.iu = s;
    }

    public String getD() {
        return d;
    }

    public void setD(final String s) {
        this.d = s;
    }

    public GoTCharacter(String n,String iu,String d,String hu,String hn,String hi){
        this.n = n;
        this.iu = iu;
        this.d = d;
        this.hu = hu;
        this.hn = hn;
        this.hi = hi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(n);
        dest.writeString(iu);
        dest.writeString(d);
        dest.writeString(hu);
        dest.writeString(hn);
        dest.writeString(hi);
    }
}
