package com.project.swhackaton.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.project.swhackaton.R;
import com.project.swhackaton.databinding.ActivityContractBinding;
import com.project.swhackaton.network.Data;
import com.project.swhackaton.network.NetRetrofit;
import com.project.swhackaton.network.Response;
import com.project.swhackaton.network.requeset.ContractRequest;
import com.project.swhackaton.viewmodel.ContractViewModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.POST;

public class ContractActivity extends AppCompatActivity {

    ActivityContractBinding binding;
    ContractViewModel viewModel;

    // Floating Button
    private Animation fab_open;
    private Animation fab_close;
    public boolean isFabOpen = true;

    // Date
    Date today = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 E요일", Locale.KOREAN);

    // Upload Album
    private static final int PICK_FROM_ALBUM = 1;
    private final int REQUEST_IMAGE_CROP = 2;
    private File tempFile;
    private String fileExt;
    private String fileType;
    private String uploadName;

    // SharedPreferences
    SharedPreferences loginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);

        viewModel = ViewModelProviders.of(this).get(ContractViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contract);

        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        loginData =  getSharedPreferences("Login", MODE_PRIVATE);

        fab_open = AnimationUtils.loadAnimation(ContractActivity.this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(ContractActivity.this, R.anim.fab_close);

        setDate();
        initLayout();
        toggleFab();
    }

    // Date 텍스트의 날짜를 설정한다.
    public void setDate(){
        binding.date.setText(simpleDateFormat.format(today));
    }

    // Toolbar 설정
    public void initLayout(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp);
    }

    // Toolbar Event
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
                break;

            case R.id.register:
                Toast.makeText(this, "등록", Toast.LENGTH_SHORT).show();

                try{
                    uploadProfile(changeToBytes(), tempFile.getName());
                }catch (Exception e){
                    Toast.makeText(this, "올바른 거래 계약서를 작성하여주십시오.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Toolbar Menu 설정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contract_item, menu);
        return true;
    }

    // Floating Button ClickEvent
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.fab_main:
                toggleFab();
                break;

            case R.id.fab_sub1:
                tedPermission();
                toggleFab();
                break;

            case R.id.fab_sub2:
                toggleFab();
                break;
        }
    }

    // Floating Button Setting
    public void toggleFab(){
        if(isFabOpen){
            binding.fabMain.setImageResource(R.drawable.ic_add_black_24dp);
            binding.fabSub1.startAnimation(fab_close);
            binding.fabSub2.startAnimation(fab_close);
            binding.fabSub1.setClickable(false);
            binding.fabSub2.setClickable(false);
            isFabOpen = false;
        }else{
            binding.fabMain.setImageResource(R.drawable.ic_close_black_24dp);
            binding.fabSub1.startAnimation(fab_open);
            binding.fabSub2.startAnimation(fab_open);
            binding.fabSub1.setClickable(true);
            binding.fabSub2.setClickable(true);
            isFabOpen = true;
        }
    }

    // Phone 권한 설정
    public void tedPermission(){

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                goToAlbum();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setDeniedMessage("사진 및 파일을 저장하기 위하여 접근 권한이 필요합니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    // Album Setting
    public void goToAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    // 앨범 호출 함수로 부터 전달 받는 onActivityResult 함수
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            // goToAlbum 매서드에서 startActivityForResult result_code 로 전달하는 값
            case PICK_FROM_ALBUM:

                // Uri 클래스를 사용해서 photoUri 변수에 값을 저장받는다.
                Uri photoUri = null;

                try {
                    photoUri = data.getData();
                } catch (NullPointerException e) {

                    photoUri = null;
                }

                Log.d("TAG", "PICK_FROM_ALBUM photoUri : " + photoUri);

                // cursor 변수
                Cursor cursor = null;

                try {
                    // proj 배열에 갤러리로 부터 받은 값을 저장한다.
                    String[] proj = {MediaStore.Images.Media.DATA};

                    // assert 자료형을 사용합니다.
                    // assert 자료형은 null 이 들어오면 안되는 값에 사용한다.
                    assert photoUri != null;
                    cursor = ContractActivity.this.getContentResolver().query(photoUri, proj, null, null, null);

                    assert cursor != null;
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                    cursor.moveToFirst();

                    tempFile = new File(cursor.getString(column_index));

                    Log.d("TAG", "tempFile Uri : " + Uri.fromFile(tempFile));

                } catch (NullPointerException e) {

                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
                try {

                    binding.file.setImageURI(Uri.fromFile(tempFile));
                } catch (NullPointerException e) {

                }

                break;

            case REQUEST_IMAGE_CROP:
                break;
        }
    }

    // 이미지파일을 비트로 바꿉니다
    private byte[] changeToBytes() {

        int size = (int) tempFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(tempFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // Profile Upload
    public void uploadProfile(byte[] imageBytes, String originalName){

        String[] filenameArray = originalName.split("\\.");
        String extension = filenameArray[filenameArray.length -1];

        fileType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        fileExt = "." + extension;

        uploadName = Integer.toString(new Random().nextInt(999999999));

        RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(fileType)), imageBytes);

        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", uploadName + fileExt, requestFile);

        RequestBody fileNameBody = RequestBody.create(MediaType.parse("text/plain"), uploadName);

        Call<Response<Data>> res  = NetRetrofit.getInstance().getContractService().uploadProfile(loginData.getString("token",""), body, fileNameBody, binding.title.getText().toString(),binding.select.getText().toString());
        res.enqueue(new Callback<Response<Data>>() {
            @Override
            public void onResponse(Call<Response<Data>> call, retrofit2.Response<Response<Data>> response) {

                if(response.code() == 200){
                    Toast.makeText(ContractActivity.this, "거래 계약서 작성을 완료하였습니다.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(ContractActivity.this, MainActivity.class));
                } else if(response.code() == 401){
                    Toast.makeText(ContractActivity.this, "거래자의 ID 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Response<Data>> call, Throwable t) {

                Toast.makeText(ContractActivity.this, "거래 계약서 작성을 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // AlertDialog SelectUser
    public void selectUser(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("입력");
        builder.setMessage("거래를 진행할 대상의 ID 를 입력해주세요.");
        builder.setIcon(R.drawable.ic_message_black_24dp);

        EditText editText = new EditText(this);
        builder.setView(editText);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(ContractActivity.this, "okay", Toast.LENGTH_SHORT).show();
                binding.select.setText(editText.getText().toString());
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ContractActivity.this, "no", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
