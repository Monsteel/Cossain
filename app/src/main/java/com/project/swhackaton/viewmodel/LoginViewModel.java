package com.project.swhackaton.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.project.swhackaton.network.Data;
import com.project.swhackaton.network.NetRetrofit;
import com.project.swhackaton.network.Response;
import com.project.swhackaton.network.requeset.LoginRequest;
import com.project.swhackaton.widget.SingleLiveEvent;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginViewModel extends ViewModel {

    // View
    public String id;
    public String password;

    // View Event
    public SingleLiveEvent<String> idNotInputEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<String> passwordNotInputEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<String> loginSuccessEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<String> loginErrorEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<String> signEvent = new SingleLiveEvent<>();

    // Request
    LoginRequest login_request = new LoginRequest();

    // Login
    public void login(){
        if(isValidId() && isValidPassword()){

            login_request.setId(id);
            login_request.setPw(password);

            loginUser();
        }
    }

    // Id Check
    public boolean isValidId(){

        if(id == null || id.isEmpty()){
            idNotInputEvent.call();
            return false;
        }
        return true;
    }

    // Password Check
    public boolean isValidPassword(){

        if(password == null || password.isEmpty()){
            passwordNotInputEvent.call();
            return false;
        }
        return true;
    }

    // LoginUser
    public void loginUser(){

        Call<Response<Data>> res = NetRetrofit.getInstance().getLogin().loginPost(login_request);
        res.enqueue(new Callback<Response<Data>>() {
            @Override
            public void onResponse(Call<Response<Data>> call, retrofit2.Response<Response<Data>> response) {
                loginSuccessEvent.call();
            }

            @Override
            public void onFailure(Call<Response<Data>> call, Throwable t) {
                loginErrorEvent.call();
            }
        });
    }

    // SignUp
    public void signup(){
        signEvent.call();
    }
}
