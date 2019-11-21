package com.project.swhackaton.network.service;

import com.project.swhackaton.network.Data;
import com.project.swhackaton.network.Response;
import com.project.swhackaton.network.requeset.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {


//    [example code]


    @POST("/auth")
    Call<Response<Data>> loginPost(@Body LoginRequest loginRequest);

//    @Multipart
//    @POST("/image/upload/thumbnail")
//    Call<Response> uploadThumbnail(@Header("x-access-token") String token,
//                                   @Part MultipartBody.Part thumbnail,
//                                   @Part("thumbnail") RequestBody name,
//                                   @Query("channel_id") String channel_id);
//
//    Call<Response<Data>> AllowUser(@Header("x-access-token") String token,
//                                   @Query("channel_id") String channel_id,
//                                   @Query("user_id") String user_id);
//
//    @DELETE("/channel-admin/reject")
//    Call<Response<Data>> RejectUser(@Header("x-access-token") String token,
//                                    @Query("channel_id") String channel_id,
//                                    @Query("user_id") String user_id);

}