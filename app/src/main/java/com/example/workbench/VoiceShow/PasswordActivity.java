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
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.graphics.Color;

import com.example.workbench.VoiceShow.R;

public class PasswordActivity extends AppCompatActivity {

    private String password_num1;
    private String password_num2;
    private String password_num3;
    private String password_num4;
    public int i = 0;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        ImageButton backbutton = (ImageButton)findViewById((R.id.SETTINGS_BACK_BUTTON4));
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void ButtonClick(View v) {
        // activity_main 에서 발생되는 버튼 이벤트 처리.
        // 핸드폰 번호를 보여 줄 텍스트뷰 아이디
        TextView    password_check1 = (TextView)findViewById(R.id.Password_Check1);
        TextView    password_check2 = (TextView)findViewById(R.id.Password_Check2);
        TextView    password_check3 = (TextView)findViewById(R.id.Password_Check3);
        TextView    password_check4 = (TextView)findViewById(R.id.Password_Check4);

        switch (v.getId()) {
            case R.id.Password_BACK_SPACE:
                if(i==0){
                    break;
                }
                else if(i == 1) {
                    password_num1 = "";
                    password_check1.setText(password_num1);
                }
                else if(i == 2) {
                    password_num2 = "";
                    password_check2.setText(password_num2);
                }
                else if(i == 3){
                    password_num3 = "";
                    password_check3.setText(password_num3);
                }
                i--;
                break;
            case R.id.Password_KEYPAD_0:
                if(i<4)
                    i++;
                break;
            case R.id.Password_KEYPAD_1:
                if(i<4)
                    i++;
                break;
            case R.id.Password_KEYPAD_2:
                if(i<4)
                    i++;
                break;
            case R.id.Password_KEYPAD_3:
                if(i<4)
                    i++;
                break;
            case R.id.Password_KEYPAD_4:
                if(i<4)
                    i++;
                break;
            case R.id.Password_KEYPAD_5:
                if(i<4)
                    i++;
                break;
            case R.id.Password_KEYPAD_6:
                if(i<4)
                    i++;
                break;
            case R.id.Password_KEYPAD_7:
                if(i<4)
                    i++;
                break;
            case R.id.Password_KEYPAD_8:
                if(i<4)
                    i++;
                break;
            case R.id.Password_KEYPAD_9:
                if(i<4)
                    i++;
                break;
        }
        if(i == 1) {
            password_num1 = "*";
            password_check1.setText(password_num1);
        }
        else if(i == 2) {
            password_num2 = "*";
            password_check2.setText(password_num2);
        }
        else if(i == 3){
            password_num3 = "*";
            password_check3.setText(password_num3);
        }
        else if(i == 4) {
            password_num4 = "*";
            password_check4.setText(password_num4);
            finish();
        }
    }
}
