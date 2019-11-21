package com.project.swhackaton.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.project.swhackaton.network.Data;
import com.project.swhackaton.network.NetRetrofit;
import com.project.swhackaton.network.Response;
import com.project.swhackaton.network.requeset.SignUpRequest;
import com.project.swhackaton.widget.SingleLiveEvent;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpViewModel extends ViewModel {

    //EditText Value
    public String id;
    public String pw;
    public MutableLiveData<String> check_pw;
    public String phone;
    public String email;
    public String name;

    //Event
    public SingleLiveEvent<String> isvalid = new SingleLiveEvent<>();
    public SingleLiveEvent<String> isSuccessLogin = new SingleLiveEvent<>();
    public SingleLiveEvent<String> isFailedLogin = new SingleLiveEvent<>();

    public void SignUpRequest() {
        if (isValid()) {

            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setId(id);
            signUpRequest.setPhoneNumber(pw);
            signUpRequest.setPassword(phone);
            signUpRequest.setEmail(email);
            signUpRequest.setName(name);

            final Call<Response<Data>> signup = NetRetrofit.getInstance().getSignUp().SignUpRequest(signUpRequest);
            signup.enqueue(new Callback<Response<Data>>() {
                @Override
                public void onResponse(Call<Response<Data>> call, retrofit2.Response<Response<Data>> response) {
                    Log.e("", "성공");
                    System.out.println(response.code()+"");
                    isSuccessLogin.call();
                }

                @Override
                public void onFailure(Call<Response<Data>> call, Throwable t) {
                    Log.e("", "네트워크 오류");
                    isFailedLogin.call();
                }
            });


        }
        else{
            isvalid.call();
        }
    }

    public boolean isValid() {
        if (!isValidId()) {
            return false;
        } else if (!isValidPhone()) {
            return false;

        } else if (!isValidPw()) {
            return false;

        } else if (!isValidEmail()) {
            return false;
        }
        return true;
    }

    private boolean isValidId() {
        if (id == null||id.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isValidPw() {
        if (pw == null||pw.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isValidPhone() {
        if (phone == null||phone.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isValidEmail() {
        if (email == null||email.isEmpty()) {
            return false;
        }
        return true;
    }
}
