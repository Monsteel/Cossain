package com.project.swhackaton.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.project.swhackaton.R;
import com.project.swhackaton.databinding.ActivityLoginBinding;
import com.project.swhackaton.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        observerViewModel();
    }

    public void observerViewModel(){

        viewModel.idNotInputEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("Login / Id", "ID 를 입력하지 않으셨습니다.");
                Toast.makeText(LoginActivity.this, "ID 를 입력하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.passwordNotInputEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("Login / Password", "비밀번호를 입력하지 않으셨습니다.");
                Toast.makeText(LoginActivity.this, "비밀번호를 입력하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.loginSuccessEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("Login", "로그인을 정상적으로 수행하였습니다.");
                Toast.makeText(LoginActivity.this, "로그인을 정상적으로 수행하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.loginErrorEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("Login", "로그인을 실패하였습니다.");
                Toast.makeText(LoginActivity.this, "로그인을 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.signEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("Login / Sign Up", "회원가입으로 이동합니다.");
                Intent intent = new Intent(LoginActivity.this, SignUpAcitivty.class);
                startActivity(intent);
            }
        });
    }
}
