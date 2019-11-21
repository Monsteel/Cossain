package com.project.swhackaton.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.project.swhackaton.R;
import com.project.swhackaton.databinding.ActivityLoginBinding;
import com.project.swhackaton.databinding.ActivitySignUpAcitivtyBinding;
import com.project.swhackaton.viewmodel.LoginViewModel;
import com.project.swhackaton.viewmodel.MainViewModel;
import com.project.swhackaton.viewmodel.SignUpViewModel;

public class SignUpAcitivty extends AppCompatActivity {
    ActivitySignUpAcitivtyBinding binding;
    SignUpViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acitivty);

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_acitivty);

        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        observer();
    }

    private void observer(){
        viewModel.isvalid.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("SignUp", "입력에 오류가 있습니다..");
                Toast.makeText(SignUpAcitivty.this, "입력에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.isSuccessLogin.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("SignUp", "회원가입 완료");
                Toast.makeText(SignUpAcitivty.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        viewModel.isFailedLogin.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("SignUp", "회원가입에 실패했습니다.");
                Toast.makeText(SignUpAcitivty.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
