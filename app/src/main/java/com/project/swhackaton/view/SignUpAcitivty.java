package com.project.swhackaton.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

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

    }
}
