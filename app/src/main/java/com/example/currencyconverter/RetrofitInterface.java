package com.example.currencyconverter;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @GET("v6/ea10a3231ce0ae12de5f2b6a/latest/{currency}")
    Call<JsonObject> getExchangeChange(@Path("currency") String currency);
}
