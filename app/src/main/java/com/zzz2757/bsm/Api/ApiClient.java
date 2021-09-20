package com.zzz2757.bsm.Api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.CookieJar;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://bssm.kro.kr/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient(Context context){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        CookieJar cookieJar = new JavaNetCookieJar(new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL));
        builder.cookieJar(cookieJar);
        OkHttpClient client = builder.build();

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
