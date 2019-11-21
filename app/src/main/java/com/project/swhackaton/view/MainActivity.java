package com.project.swhackaton.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.project.swhackaton.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLogin();
    }

    public void checkLogin(){
        SharedPreferences loginData = getSharedPreferences("Login", MODE_PRIVATE);

        if(loginData.getString("id", null) == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}
