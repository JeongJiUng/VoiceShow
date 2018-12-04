package com.example.workbench.VoiceShow.Settings;

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
import com.example.workbench.VoiceShow.cSystemManager;

import com.example.workbench.VoiceShow.R;

public class PasswordActivity extends AppCompatActivity {

    private int password_num1;
    private int password_num2;
    private int password_num3;
    private int password_num4;
    private String password_star1;
    private String password_star2;
    private String password_star3;
    private String password_star4;
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
        TextView    password_check1 = (TextView)findViewById(R.id.Password_Num1);
        TextView    password_check2 = (TextView)findViewById(R.id.Password_Num2);
        TextView    password_check3 = (TextView)findViewById(R.id.Password_Num3);
        TextView    password_check4 = (TextView)findViewById(R.id.Password_Num4);

        switch (v.getId()) {
            case R.id.Password_BACK_SPACE:
                if(i==0){
                    break;
                }
                else if(i == 1) {
                    password_star1 = "";
                    password_num1 = -1;
                    password_check1.setText(password_star1);
                }
                else if(i == 2) {
                    password_star2 = "";
                    password_num2 = -1;
                    password_check2.setText(password_star2);
                }
                else if(i == 3){
                    password_star3 = "";
                    password_num3 = -1;
                    password_check3.setText(password_star3);
                }
                i--;
                break;
            case R.id.Password_KEYPAD_0:
                ButtonFunc(0);
                break;
            case R.id.Password_KEYPAD_1:
                ButtonFunc(1);
                break;
            case R.id.Password_KEYPAD_2:
                ButtonFunc(2);
                break;
            case R.id.Password_KEYPAD_3:
                ButtonFunc(3);
                break;
            case R.id.Password_KEYPAD_4:
                ButtonFunc(4);
                break;
            case R.id.Password_KEYPAD_5:
                ButtonFunc(5);
                break;
            case R.id.Password_KEYPAD_6:
                ButtonFunc(6);
                break;
            case R.id.Password_KEYPAD_7:
                ButtonFunc(7);
                break;
            case R.id.Password_KEYPAD_8:
                ButtonFunc(8);
                break;
            case R.id.Password_KEYPAD_9:
                ButtonFunc(9);
                break;
        }
    }
    public void ButtonFunc(int _i){
        TextView    password_check1 = (TextView)findViewById(R.id.Password_Num1);
        TextView    password_check2 = (TextView)findViewById(R.id.Password_Num2);
        TextView    password_check3 = (TextView)findViewById(R.id.Password_Num3);
        TextView    password_check4 = (TextView)findViewById(R.id.Password_Num4);

        if(i == 0) {
            password_star1 = "*";
            password_num1 = _i;
            password_check1.setText(password_star1);
            i++;
        }
        else if(i == 1) {
            password_star2 = "*";
            password_num2 = _i;
            password_check2.setText(password_star2);
            i++;
        }
        else if(i == 2){
            password_star3 = "*";
            password_num3 = _i;
            password_check3.setText(password_star3);
            i++;
        }
        else if(i == 3){
            password_star4 = "*";
            password_num4 = _i;
            password_check4.setText(password_star4);
            i = 0;
            int pInt = password_num1 * 1000 + password_num2 * 100 + password_num3 *10 + password_num4;
            String pString = String.valueOf(pInt);
            cSystemManager.getInstance().GetSettings().SetPassword(pString);
            cSystemManager.getInstance().GetSettings().SetmbSecure(true);
            finish();
        }
    }
}
