package com.example.workbench.VoiceShow.Settings;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.graphics.Color;
import com.example.workbench.VoiceShow.cSystemManager;


import com.example.workbench.VoiceShow.R;

public class PasswordCheckActivity extends AppCompatActivity {

    private String password;
    public int i;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_check);
        password = "";
        i = 0;
    }
    public void ButtonClick(View v) {
        // activity_main 에서 발생되는 버튼 이벤트 처리.
        // 암호를 입력하고 비밀번호가 일치하는 지 확인하는 이벤트
        String pString = cSystemManager.getInstance().GetSettings().GetPassword();
        TextView    password_check1 = (TextView)findViewById(R.id.Password_Check1);
        TextView    password_check2 = (TextView)findViewById(R.id.Password_Check2);
        TextView    password_check3 = (TextView)findViewById(R.id.Password_Check3);
        TextView    password_check4 = (TextView)findViewById(R.id.Password_Check4);

        switch (v.getId()) {
            case R.id.Password_Check_BACK_SPACE:
                if (i == 1) {
                    password = "";
                    password_check1.setText("");
                }
                if (i == 2) {
                    password.substring(0, 1);
                    password_check2.setText("");
                }
                if (i == 3) {
                    password.substring(0, 2);
                    password_check3.setText("");
                }
                if(i > 0 && i < 4)
                    i--;
                break;
            case R.id.Password_Check_KEYPAD_0:
                password += "0";
                i++;
                break;
            case R.id.Password_Check_KEYPAD_1:
                password += "1";
                i++;
                break;
            case R.id.Password_Check_KEYPAD_2:
                password += "2";
                i++;
                break;
            case R.id.Password_Check_KEYPAD_3:
                password += "3";
                i++;
                break;
            case R.id.Password_Check_KEYPAD_4:
                password += "4";
                i++;
                break;
            case R.id.Password_Check_KEYPAD_5:
                password += "5";
                i++;
                break;
            case R.id.Password_Check_KEYPAD_6:
                password += "6";
                i++;
                break;
            case R.id.Password_Check_KEYPAD_7:
                password += "7";
                i++;
                break;
            case R.id.Password_Check_KEYPAD_8:
                password += "8";
                i++;
                break;
            case R.id.Password_Check_KEYPAD_9:
                password += "9";
                i++;
                break;
        }
        if (i == 1)
            password_check1.setText("*");
        else if (i == 2)
            password_check2.setText("*");
        else if (i == 3)
            password_check3.setText("*");
        else if (i == 4) {
            int value1 = 0;
            int value2 = 0;
            password_check4.setText("*");
            if(pString.indexOf(value1) == password.indexOf(value2)){
                finish();
            }
            else{
                i = 0;
                password_check1.setText("");
                password_check2.setText("");
                password_check3.setText("");
                password_check4.setText("");
                password = "";
                Toast.makeText(getApplicationContext(),"비밀번호가 틀립니다.",Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onBackPressed(){
        finishAffinity();
        System.runFinalization();
        System.exit(0);
    }
}
