package com.project.swhackaton.viewmodel;

import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.project.swhackaton.widget.SingleLiveEvent;

public class LoginViewModel extends ViewModel {

    // View
    public String id;
    public String password;

    // View Event
    public SingleLiveEvent idNotInputEvent;
    public SingleLiveEvent passwordNotInputEvent;

    public SingleLiveEvent loginSuccessEvent;
    public SingleLiveEvent loginErrorEvent;

    public void login(){
        if(isValidId() && isValidPassword()){
            loginUser();
        }
    }

    public boolean isValidId(){

        if(id == null || id.isEmpty()){
            idNotInputEvent.call();
            return false;
        }
        return true;
    }

    public boolean isValidPassword(){

        if(password == null || password.isEmpty()){
            passwordNotInputEvent.call();
            return false;
        }
        return true;
    }

    public void loginUser(){
        // Retrofit 추가
    }
}
