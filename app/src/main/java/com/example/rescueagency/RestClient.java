package com.example.rescueagency;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    public static final String BASE_URL = "https://0dbd-180-235-121-242.ngrok-free.app";
    public static Retrofit getInstance() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client=new OkHttpClient.Builder().addInterceptor(interceptor).build();
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build();
    }
    public static MYAPI makeAPI() {
        return getInstance().create(MYAPI.class);
    }

}
