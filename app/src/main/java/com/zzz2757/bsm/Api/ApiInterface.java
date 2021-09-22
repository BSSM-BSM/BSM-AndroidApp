package com.zzz2757.bsm.Api;

import com.zzz2757.bsm.GetterSetter.GetterSetter;
import com.zzz2757.bsm.GetterSetter.PostData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface
{
    @FormUrlEncoded
    @POST("database")
    Call<String> version(
            @Field("command_type") String command_type,
            @Field("os") String os,
            @Field("app") String app
    );
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
    @FormUrlEncoded
    @POST("database")
    Call<PostData> post(
            @Field("command_type") String command_type,
            @Field("boardType") String boardType,
            @Field("post_no") int post_no
    );
    @FormUrlEncoded
    @POST("database")
    Call<String> comment(
            @Field("command_type") String command_type,
            @Field("boardType") String boardType,
            @Field("post_no") int post_no
    );
    @FormUrlEncoded
    @POST("database")
    Call<GetterSetter> commentWrtie(
            @Field("command_type") String command_type,
            @Field("boardType") String boardType,
            @Field("post_no") int post_no,
            @Field("post_comment") String post_comment
    );
}