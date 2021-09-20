package com.zzz2757.bsm.Api;

import com.zzz2757.bsm.GetterSetter.BoardData;
import com.zzz2757.bsm.GetterSetter.GetterSetter;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface
{
    @FormUrlEncoded
    @POST("database")
    Call<GetterSetter> login(
            @Field("command_type") String command_type,
            @Field("member_id") String member_id,
            @Field("member_pw") String member_pw
    );
    @FormUrlEncoded
    @POST("database")
    Call<String> board(
            @Field("command_type") String command_type,
            @Field("boardType") String boardType
    );
}