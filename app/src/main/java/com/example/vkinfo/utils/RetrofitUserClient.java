package com.example.vkinfo.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUserClient {

    private static final String APP_URL = "http://10.0.2.2:8080";

    public static UserClient getRetrofitUserClient() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(APP_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit.create(UserClient.class);
    }
}
