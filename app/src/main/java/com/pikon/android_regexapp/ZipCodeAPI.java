package com.pikon.android_regexapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ZipCodeAPI {

    @Headers({"Accept: application/json"})

    @GET("{zipCode}")
    Call<JsonArray> getPlaceFromZipCode(@Path("zipCode") String zipCode );
}
