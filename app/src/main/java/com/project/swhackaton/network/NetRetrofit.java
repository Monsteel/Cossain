package com.project.swhackaton.network;

import com.project.swhackaton.network.service.ContractService;
import com.project.swhackaton.network.service.LoginService;
import com.project.swhackaton.network.service.SignUpService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetRetrofit {
    private static NetRetrofit ourInstance = new NetRetrofit();
    public static NetRetrofit getInstance() {
        return ourInstance;
    }

    private NetRetrofit() {

    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://172.26.0.167:3000")
            .addConverterFactory(GsonConverterFactory.create()) // 파싱등록
            .build();

    LoginService login = retrofit.create(LoginService.class);
    public LoginService getLogin() {
        return login;
    }

    SignUpService signUpService = retrofit.create(SignUpService.class);
    public SignUpService getSignUp() {
        return signUpService;
    }

    ContractService contractService = retrofit.create(ContractService.class);
    public ContractService getContractService(){
        return contractService;
    }
}

