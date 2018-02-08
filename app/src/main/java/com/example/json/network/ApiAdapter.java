package com.example.json.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mamorky on 8/02/18.
 */

public class ApiAdapter {
    private static GitHubClient API_SERVICE;
    public static final String BASE_URL = "https://api.github.com/";

    public static GitHubClient getApiService(){

        if(API_SERVICE == null){
            Gson gson = new GsonBuilder()
                    .setDateFormat("dd-MM-yyyy'T'HH:mm:ssZ")
                    .create();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();

            API_SERVICE = retrofit.create(GitHubClient.class);
        }
        return API_SERVICE;
    }
}
