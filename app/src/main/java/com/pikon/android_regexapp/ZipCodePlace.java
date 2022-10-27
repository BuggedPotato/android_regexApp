package com.pikon.android_regexapp;

import com.google.gson.annotations.SerializedName;

public class ZipCodePlace {
    @SerializedName( "kod" )
    public String zipCode;
    @SerializedName( "gmina" )
    public String place;

    public ZipCodePlace(String zipCode, String place) {
        this.zipCode = zipCode;
        this.place = place;
    }

    @Override
    public String toString() {
        return  zipCode +
                ", gmina: " + place;
    }
}
