package com.project.swhackaton.view;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.project.swhackaton.R;
import com.project.swhackaton.databinding.ActivityLoginBinding;
import com.project.swhackaton.databinding.ActivitySignUpAcitivtyBinding;
import com.project.swhackaton.viewmodel.LoginViewModel;
import com.project.swhackaton.viewmodel.MainViewModel;
import com.project.swhackaton.viewmodel.SignUpViewModel;

import java.util.ArrayList;
import java.util.Locale;

public class SignUpAcitivty extends AppCompatActivity {
    private Boolean isPermission = true;
    ActivitySignUpAcitivtyBinding binding;
    SignUpViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acitivty);

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_acitivty);

        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        usimCheck();
        observer();
    }

    private void observer() {
        viewModel.isvalid.observe(this, s -> {
            Log.e("SignUp", "입력에 오류가 있습니다..");
            Toast.makeText(SignUpAcitivty.this, "입력에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
        });

        viewModel.isSuccessLogin.observe(this, s -> {
            Log.e("SignUp", "회원가입 완료");
            Toast.makeText(SignUpAcitivty.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
            onBackPressed();
        });

        viewModel.isFailedLogin.observe(this, s -> {
            Log.e("SignUp", "회원가입에 실패했습니다.");
            Toast.makeText(SignUpAcitivty.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
        });

    }

    public void tedPermission() {

        // PermissionListener 클래스 참조
        PermissionListener permissionListener = new PermissionListener() {

            // 권한 요청 성공
            @Override
            public void onPermissionGranted() {

                // 사용자가 승락을 한 경우
                if (isPermission) {
                }
            }

            // 권한 요청 실패
            // 사용자가 거절한 경우
            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };

        // 권한 요청 메세지 내용
        TedPermission.with(SignUpAcitivty.this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("설정] > [권한] 에서 권한을 허용할 수 있습니다.") // 안내 문자 2
                .setRationaleMessage("사용자의 연락처 정보를 가져오기 위하여 접근 권한이 필요합니다") // 안내 문자 1
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void usimCheck() {
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephonyManager == null) {
            Toast.makeText(this, "전화번호를 가져올 수 없습니다.", Toast.LENGTH_LONG).show();
        } else {
            try {
                int simState = mTelephonyManager.getSimState();
                switch (simState) { // 유심 상태를 알 수 없는 경우 case TelephonyManager.SIM_STATE_UNKNOWN: // 유심이 없는 경우
                    case TelephonyManager.SIM_STATE_ABSENT: // 유심 오류, 영구적인 사용 중지 상태
                        break;
                    case TelephonyManager.SIM_STATE_PERM_DISABLED: // 유심이 있지만 오류 상태인 경우
                        break;
                    case TelephonyManager.SIM_STATE_CARD_IO_ERROR: // 유심이 있지만 통신사 제한으로 사용 불가
                        break;
                    case TelephonyManager.SIM_STATE_CARD_RESTRICTED:
                        Toast.makeText(this, "단말기의 유심이 존재하지 않거나 오류가 있는 경우, 앱을 사용할 수 없습니다.", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        if (checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            tedPermission();
                            return;
                        }
                        String telNumber = mTelephonyManager.getLine1Number();
                        telNumber = telNumber.replace("+82", "0");
                        binding.getViewModel().phone = PhoneNumberUtils.formatNumber(telNumber, Locale.getDefault().getCountry());

                        Log.e("",mTelephonyManager.getLine1Number());
                        break;
                }
            } catch (Exception e) {
                Toast.makeText(this, "단말기의 정보를 가져오는 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                Log.e("simCheck", "Exception: " + e.toString());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
