package com.zzz2757.bsm;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface
{
    @FormUrlEncoded
    @POST("database")
    Call<GetterSetter> login(
            @Field("command_type") String command,
            @Field("member_id") String member_id,
            @Field("member_pw") String member_pw
    );
}