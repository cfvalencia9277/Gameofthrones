package com.testcode.gameofthrones.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Fabian on 07/12/2016.
 */

public class GoTHouse {
    @SerializedName("houseImageUrl")
    public
    String u;
    @SerializedName("houseName")
    public
    String n;
    @SerializedName("houseId")
    public
    String i;

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
}
