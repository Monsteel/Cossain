package com.project.swhackaton.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.karrel.signviewlib.SignView;
import com.karrel.signviewlib.SignViewUtil;
import com.project.swhackaton.R;

public class SignActivity extends AppCompatActivity {

    SignView signView;
    ImageView imageView;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        signView = findViewById(R.id.signView);
        imageView = findViewById(R.id.imageView);
        frameLayout = findViewById(R.id.FrameLayout);

    }
    private View.OnClickListener onMakeBitmapListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Bitmap bitmap1 = signView.getBitmap();
            Bitmap bitmap2 = SignViewUtil.loadBitmapFromView(frameLayout);

            Bitmap overlay = SignViewUtil.overlay(bitmap1, bitmap2);
            imageView.setImageBitmap(overlay);
        }
    };
}
