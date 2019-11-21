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

public interface ContractService {


//    @Multipart
//    @POST("/contract")
//    Call<Response<Data>> uploadProfile(@Header("x-access-token") String token,
//                                       @Part MultipartBody.Part profile_pic,
//                                       @Part("profile_pic") RequestBody name,
//                                       @Body ContractRequest contractRequest);

    @GET("/contract")
    Call<Response<Data>> getContractList(@Header("x-access-token")String token);
}