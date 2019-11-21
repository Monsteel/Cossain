package com.project.swhackaton.network.service;

import com.project.swhackaton.network.Data;
import com.project.swhackaton.network.Response;
import com.project.swhackaton.network.requeset.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/auth")
    Call<Response<Data>> loginPost(@Body LoginRequest loginRequest);
}