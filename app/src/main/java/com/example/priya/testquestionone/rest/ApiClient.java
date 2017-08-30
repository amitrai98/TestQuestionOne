package com.example.priya.testquestionone.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by priya on 25/7/17.
 */
public class ApiClient {

    public static final String ROOT_URL = "http://54.86.93.44/postallhere/apis/";

    private static Retrofit getRetrofitInstance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();    }

    public static SendDataInterface getApiService() {
        return getRetrofitInstance().create(SendDataInterface.class);
    }
    public static SignUpInterface getApiServiceSinUp() {
        return getRetrofitInstance().create(SignUpInterface.class);
    }
}


