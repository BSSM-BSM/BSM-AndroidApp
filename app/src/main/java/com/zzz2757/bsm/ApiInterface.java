package com.zzz2757.bsm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface
{
    @GET("database")
    Call<GetterSetter> getTest(
            @Query("status") String status
    );
}