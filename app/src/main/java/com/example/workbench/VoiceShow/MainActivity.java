package com.example.workbench.VoiceShow;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.workbench.VoiceShow.Permissions.cPermissionManager;
import com.example.workbench.VoiceShow.STTModule.cSTTModule;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
    {
        private String          mPhoneNumber;                           // 핸드폰 번호 문자열

        private void initialize()
        {
            mPhoneNumber        = "";
        }

        // 어플리케이션에서 사용 될 퍼미션 관련 코드
        private void setPermission()
        {
            // OS 버전이 마시멜로우 이상인지 체크
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                // 단말기의 권한 중 전화걸기 권한이 허용되어 있는지 확인
                int             permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE);

                // 현재 어플리케이션이 CALL_PHONE 에 대해 거부되어있는지 확인한다.
                if (permissionResult == PackageManager.PERMISSION_DENIED)
                {
                    // 사용자가 CALL_PHONE 권한을 거부한 적이 있는지 확인한다.
                    // 거부한적이 있으면 True 리턴
                    // 거부한적이 없으면 False 리턴
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE))
                    {
                        // 거부한 적이 있는 경우
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("권한이 필요합니다.").setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속 하시겠습니까?")
                                .setPositiveButton("네", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        // 새로운 인스턴스(onClickListener)를 생성했기 때문에 버전체크를 다시 해준다.
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                        {
                                            // CALL_PHONE 권한을 OS에 요청
                                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                        }
                                    }
                                })
                                .setNegativeButton("아니요", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        Toast.makeText(MainActivity.this, "기능을 취소했습니다", Toast.LENGTH_SHORT).show();

                                        moveTaskToBack(true);
                                        finish();
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                    }
                                })
                                .create().show();
                    }
                    else
                    {
                        // 거부한 적이 없는 경우
                        // CALL_PHONE 권한을 OS에 요청
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                    }
                }
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(com.example.workbench.VoiceShow.R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tl_tabs);// 텝 레이아웃 을 찾아준다.
        ViewPager viewPager = findViewById(R.id.vp_pager); //뷰 페이져

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        initialize();
        setPermission();

       // startActivity(new Intent("android.intent.action.DIAL"));
    }


    @Override
    // activiy_main 에서 발생되는 버튼 이벤트 처리.
    public void onClick(View v)
    {
        // 핸드폰 번호를 보여 줄 텍스트뷰 아이디
        TextView        tv_PhoneNum = (TextView)findViewById(R.id.TEXT_PHONE_NUM);

        switch(v.getId())
        {
            case R.id.BACK_SPACE:
                // 핸드폰번호 뒤에서 하나씩 지움.
                //int         len = mPhoneNumber.length();
                //mPhoneNumber.substring()
                break;

            case R.id.KEYPAD_0:
                mPhoneNumber    += "0";
                break;
            case R.id.KEYPAD_1:
                mPhoneNumber    += "1";
                break;
            case R.id.KEYPAD_2:
                mPhoneNumber    += "2";
                break;
            case R.id.KEYPAD_3:
                mPhoneNumber    += "3";
                break;
            case R.id.KEYPAD_4:
                mPhoneNumber    += "4";
                break;
            case R.id.KEYPAD_5:
                mPhoneNumber    += "5";
                break;
            case R.id.KEYPAD_6:
                mPhoneNumber    += "6";
                break;
            case R.id.KEYPAD_7:
                mPhoneNumber    += "7";
                break;
            case R.id.KEYPAD_8:
                mPhoneNumber    += "8";
                break;
            case R.id.KEYPAD_9:
                mPhoneNumber    += "9";
                break;

            case R.id.KEYPAD_STAR:
                mPhoneNumber    += "*";
                break;
            case R.id.KEYPAD_SHAP:
                mPhoneNumber    += "#";
                break;

            case R.id.KEYPAD_VCALL:
                // 영상 통화
                break;
            case R.id.KEYPAD_CALL:
                // 음성 통화
                String      tel = "tel:" + mPhoneNumber;
                startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));

                // 통화 버튼을 누르면 전화번호와 텍스트뷰 초기화
                mPhoneNumber    = "";
                tv_PhoneNum.setText(mPhoneNumber);
                break;
            case R.id.KEYPAD_HIDE:
                // 키 패드 활성/비활성
                break;
        }

        // 영상/음성 통화, 키패드 활성/비활성 버튼이 아닌 경우 텍스트뷰에 핸드폰 번호 갱신.
        if (v.getId() != R.id.KEYPAD_CALL && v.getId() != R.id.KEYPAD_VCALL && v.getId() != R.id.KEYPAD_HIDE)
            tv_PhoneNum.setText(mPhoneNumber);
    }
}
