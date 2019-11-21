package com.project.swhackaton.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.swhackaton.R;
import com.project.swhackaton.adapter.ListViewAdapter;
import com.project.swhackaton.databinding.ActivityMainBinding;
import com.project.swhackaton.model.ListModel;
import com.project.swhackaton.network.Data;
import com.project.swhackaton.network.NetRetrofit;
import com.project.swhackaton.network.Response;
import com.project.swhackaton.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MainViewModel viewModel;
    SharedPreferences loginData;
    List<ListModel> DataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginData =  getSharedPreferences("Login", MODE_PRIVATE);

        if(checkLogin()){
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
            binding.setLifecycleOwner(this);

            binding.List.setLayoutManager(new LinearLayoutManager(this));

            Call<Response<Data>> getList = NetRetrofit.getInstance().getContractService().getContractList(loginData.getString("token",""));

            Log.e("", loginData.getString("token","")+"++");
            getList.enqueue(new Callback<Response<Data>>() {
                @Override
                public void onResponse(Call<Response<Data>> call, retrofit2.Response<Response<Data>> response) {
                    Log.e("", "성공");
                    DataList = response.body().getData().getList();
                    ListViewAdapter listViewAdapter = new ListViewAdapter(DataList);
                    binding.List.setAdapter(listViewAdapter);
                    System.out.println("");
                }

                @Override
                public void onFailure(Call<Response<Data>> call, Throwable t) {
                    Log.e("", "네트워크 오류");
                }
            });

            Call<Response<Data>> getUserInfo = NetRetrofit.getInstance().getContractService().getUserInfo(loginData.getString("token",""));

            Log.e("", loginData.getString("token","")+"++");
            getUserInfo.enqueue(new Callback<Response<Data>>() {
                @Override
                public void onResponse(Call<Response<Data>> call, retrofit2.Response<Response<Data>> response) {
                    binding.helloUser.setText(response.body().getData().getUser().getName()+"님,\n안녕하세요.");
                }

                @Override
                public void onFailure(Call<Response<Data>> call, Throwable t) {
                    Log.e("", "네트워크 오류");
                }
            });
        }
    }

    public boolean checkLogin() {
        if (loginData.getString("token", "1").equals("1")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            return false;
        }
        return true;
    }
}
