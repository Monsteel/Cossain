package com.project.swhackaton.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.project.swhackaton.R;
import com.project.swhackaton.network.Data;
import com.project.swhackaton.network.NetRetrofit;
import com.project.swhackaton.network.Response;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Objects;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SignActivity extends AppCompatActivity {
    SharedPreferences loginData;
    SignaturePad mSignaturePad;
    LinearLayout btn_finish;
    ImageView signImageView;
    ImageView signImageView2;
    ImageView contractImageView;
    String id;
    int cnt;


    // Upload Album
    private String fileExt;
    private String fileType;
    private String uploadName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        loginData =  getSharedPreferences("Login", MODE_PRIVATE);
        id = getIntent().getStringExtra("id01");

        mSignaturePad = findViewById(R.id.signature_pad);
        btn_finish = findViewById(R.id.bnt_finish);
        signImageView = findViewById(R.id.imageView2);
        contractImageView = findViewById(R.id.imageView1);
        signImageView2 = findViewById(R.id.imageView4);

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                Log.e("+",mSignaturePad.getSignatureBitmap()+"");
                cnt++;
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });

        Call<Response<Data>> getUserInfo = NetRetrofit.getInstance().getContractService().getContractInfo(loginData.getString("token",""),id);
        Log.e("",getUserInfo.toString());
        getUserInfo.enqueue(new Callback<Response<Data>>() {
            @Override
            public void onResponse(Call<Response<Data>> call, retrofit2.Response<Response<Data>> response) {
                Glide.with(SignActivity.this).load(response.body().getData().getUrl()).into(contractImageView);

                if(!response.body().getData().getA()){
                    mSignaturePad.setVisibility(View.VISIBLE);
                    signImageView = findViewById(R.id.imageView4);
                    signImageView2 = findViewById(R.id.imageView2);
                    if(response.body().getData().getData().getA_resolve()){

                    }
                    if(response.body().getData().getData().getB_resolve()){
                        mSignaturePad.setVisibility(View.GONE);
                    }
                }

                if(response.body().getData().getData().getB_resolve()){
                    Glide.with(SignActivity.this).load(response.body().getData().getB_url()).into(signImageView);
                }

                if(response.body().getData().getData().getA_resolve()){
                    signImageView2.setVisibility(View.VISIBLE);
                    Glide.with(SignActivity.this).load(response.body().getData().getA_url()).into(signImageView2);
                }else{
                    signImageView2.setVisibility(View.GONE);
                    mSignaturePad.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Response<Data>> call, Throwable t) {
                Log.e("", "네트워크 오류");
            }
        });
    }
    public void AllowRequest(View ivew){
        uploadProfile(bitmapToByteArray(mSignaturePad.getSignatureBitmap()),"jpeg");
    }

    public byte[] bitmapToByteArray( Bitmap $bitmap ) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        $bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        return byteArray;
    }

    public void uploadProfile(byte[] imageBytes, String originalName){
        if(cnt > 0) {
            String[] filenameArray = originalName.split("\\.");
            String extension = filenameArray[filenameArray.length - 1];

            fileType = "JPEG";
            fileExt = "." + extension;

            uploadName = Integer.toString(new Random().nextInt(999999999));

            RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(fileType)), imageBytes);

            MultipartBody.Part body = MultipartBody.Part.createFormData("picture", uploadName + fileExt, requestFile);

            RequestBody fileNameBody = RequestBody.create(MediaType.parse("text/plain"), uploadName);

            Call<Response<Data>> res = NetRetrofit.getInstance().getContractService().AllowContract(loginData.getString("token", ""), body, fileNameBody, id);
            res.enqueue(new Callback<Response<Data>>() {
                @Override
                public void onResponse(Call<Response<Data>> call, retrofit2.Response<Response<Data>> response) {
                    if (response.code() == 200) {
                        Toast.makeText(SignActivity.this, "서명을 완료했습니다.", Toast.LENGTH_SHORT).show();
                        SignActivity.super.onBackPressed();
                    } else if (response.code() == 401) {
                        Toast.makeText(SignActivity.this, "-", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response<Data>> call, Throwable t) {
                    Toast.makeText(SignActivity.this, "서명에 실패했습니다..", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(SignActivity.this, "서명 란이 비어있거나, 이미 서명한 계약입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
    }
}
