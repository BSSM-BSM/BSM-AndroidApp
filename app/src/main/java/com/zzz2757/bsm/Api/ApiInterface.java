package com.zzz2757.bsm.Api;

import com.zzz2757.bsm.GetterSetter.GetterSetter;
import com.zzz2757.bsm.GetterSetter.PostData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiInterface
{
    @GET
    @Streaming
    Call<ResponseBody> downloadFile(@Url String url);
    @GET("version/app/android")
    Call<String> version();
    @FormUrlEncoded
    @POST("account/login")
    Call<GetterSetter> login(
            @Field("member_id") String member_id,
            @Field("member_pw") String member_pw
    );
    @GET("board/{boardType}")
    Call<String> board(
            @Path("boardType") String boardType
    );
    @GET("post/{boardType}/{postNo}")
    Call<PostData> post(
            @Path("boardType") String boardType,
            @Path("postNo") int postNo
    );
    @GET("comment/{boardType}/{postNo}")
    Call<String> comment(
            @Path("boardType") String boardType,
            @Path("postNo") int postNo
    );
    @FormUrlEncoded
    @POST("comment/{boardType}/{postNo}")
    Call<GetterSetter> commentWrtie(
            @Path("boardType") String boardType,
            @Path("postNo") int postNo,
            @Field("comment") String post_comment
    );
    @FormUrlEncoded
    @POST("like/{boardType}/{postNo}")
    Call<String> likeSend(
            @Path("boardType") String boardType,
            @Path("postNo") int postNo,
            @Field("like") int like
    );
}