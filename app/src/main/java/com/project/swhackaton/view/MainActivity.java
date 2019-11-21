package com.project.swhackaton.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.project.swhackaton.R;
import com.project.swhackaton.network.Data;
import com.project.swhackaton.network.NetRetrofit;
import com.project.swhackaton.network.Response;
import com.project.swhackaton.network.requeset.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        [Example Code]
//
//
//        final Call<Response<Data>> res = NetRetrofit.getInstance().getLogin().loginPost(login);
//        res.enqueue(new Callback<Response<Data>>() {
//            @Override
//            public void onResponse(Call<Response<Data>> call, retrofit2.Response<Response<Data>> response) {
//                Log.e("","성공");
//                System.out.println(response.body().getData().getAccessToken());
//            }
//
//            @Override
//            public void onFailure(Call<Response<Data>> call, Throwable t) {
//                Log.e("","네트워크 오류");
//            }
//        });

    }
}
