package com.example.rxjavaretrofit.networking;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
    private static Retrofit instance;
    private static String url = "https://jsonplaceholder.typicode.com/";

    public static Retrofit getInstance(){
        if(instance == null){
            return new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return instance;
    };

}
