package com.zzz2757.bsm;

import android.content.Context;

import com.zzz2757.bsm.Api.PersistentCookieStore;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.CookieJar;
import okhttp3.JavaNetCookieJar;

public class Common {
    private static final String BASE_URL = "http://bssm.kro.kr/";
    public static CookieJar cookieJar;

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static CookieJar cookie(Context context){
        cookieJar = new JavaNetCookieJar(new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL));
        return cookieJar;
    }
}
