package com.testcode.gameofthrones.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Fabian on 07/12/2016.
 */

public class GoTCharacter {
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
}
