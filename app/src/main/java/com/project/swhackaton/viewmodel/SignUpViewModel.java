package com.project.swhackaton.viewmodel;

import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.project.swhackaton.network.Data;
import com.project.swhackaton.network.NetRetrofit;
import com.project.swhackaton.network.Response;
import com.project.swhackaton.network.requeset.SignUpRequest;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpViewModel extends ViewModel {
    public LiveData<String> id;
    public LiveData<String> pw;
    public LiveData<String> check_pw;
    public LiveData<String> phone;
    public LiveData<String> email;

    public void SignUpRequest() {
        if (isValid()) {
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setId(id.getValue());
            signUpRequest.setId(pw.getValue());
            signUpRequest.setId(phone.getValue());
            signUpRequest.setId(email.getValue());


            final Call<Response<Data>> res = NetRetrofit.getInstance().getSignUp().SignUpRequest(signUpRequest);
            res.enqueue(new Callback<Response<Data>>() {
                @Override
                public void onResponse(Call<Response<Data>> call, retrofit2.Response<Response<Data>> response) {

                }

                @Override
                public void onFailure(Call<Response<Data>> call, Throwable t) {
                    Log.e("", "네트워크 오류");
                }
            });
        }
    }

    public boolean isValid() {
        if (!isValidId()) {
            return false;
        } else if (!isPhone()) {
            return false;
        } else if (!isValidEmail()) {
            return false;
        }
        return true;
    }

    private boolean isValidId() {
        if (id.getValue().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isPhone() {
        if (phone.getValue().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isValidEmail() {
        if (email.getValue().isEmpty()) {
            return false;
        }
        return true;
    }
}
