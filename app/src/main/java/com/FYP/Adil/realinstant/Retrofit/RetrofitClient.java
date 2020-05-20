package com.FYP.Adil.realinstant.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.FYP.Adil.realinstant.Retrofit.ApiUtils.BASE_URL;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClient() {
        if (retrofit==null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

            retrofit = builder.build();
        }
        return retrofit;
    }
}