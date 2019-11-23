package com.project.swhackaton.network.service;

import com.project.swhackaton.network.Data;
import com.project.swhackaton.network.Response;
import com.project.swhackaton.network.requeset.ContractRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ContractService {


//    @Multipart
//    @POST("/contract")
//    Call<Response<Data>> uploadProfile(@Header("x-access-token") String token,
//                                       @Part MultipartBody.Part profile_pic,
//                                       @Part("profile_pic") RequestBody name,
//                                       @Body ContractRequest contractRequest);

    @GET("/contract")
    Call<Response<Data>> getContractList(@Header("x-access-token")String token);

    @GET("/info")
    Call<Response<Data>> getUserInfo(@Header("x-access-token")String token);

    @GET("/contract/{id}")
    Call<Response<Data>> getContractInfo(@Header("x-access-token")String token,
                                         @Path("id") String id);

    @Multipart
    @POST("/contract/accept")
    Call<Response<Data>> AllowContract(@Header("x-access-token") String token,
                                       @Part MultipartBody.Part picture,
                                       @Part("picture") RequestBody name,
                                       @Query("_id") String _id);

    @Multipart
    @POST("/contract")
    Call<Response<Data>> uploadProfile(@Header("x-access-token") String token,
                                       @Part MultipartBody.Part picture,
                                       @Part("picture") RequestBody name,
                                       @Query("title") String title,
                                       @Query("b_id") String b_id);
}