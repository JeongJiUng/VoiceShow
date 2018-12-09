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

public class PasswordActivity extends AppCompatActivity {

    private String password;
    public int i = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        ImageButton backbutton = (ImageButton) findViewById((R.id.SETTINGS_BACK_BUTTON4));
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String password = "";
    }

    public void ButtonClick(View v) {
        TextView password_check1 = (TextView) findViewById(R.id.Password_Num1);
        TextView password_check2 = (TextView) findViewById(R.id.Password_Num2);
        TextView password_check3 = (TextView) findViewById(R.id.Password_Num3);
        TextView password_check4 = (TextView) findViewById(R.id.Password_Num4);

        switch (v.getId()) {
            case R.id.Password_BACK_SPACE:
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
            case R.id.Password_KEYPAD_0:
                password += "0";
                i++;
                break;
            case R.id.Password_KEYPAD_1:
                password += "1";
                i++;
                break;
            case R.id.Password_KEYPAD_2:
                password += "2";
                i++;
                break;
            case R.id.Password_KEYPAD_3:
                password += "3";
                i++;
                break;
            case R.id.Password_KEYPAD_4:
                password += "4";
                i++;
                break;
            case R.id.Password_KEYPAD_5:
                password += "5";
                i++;
                break;
            case R.id.Password_KEYPAD_6:
                password += "6";
                i++;
                break;
            case R.id.Password_KEYPAD_7:
                password += "7";
                i++;
                break;
            case R.id.Password_KEYPAD_8:
                password += "8";
                i++;
                break;
            case R.id.Password_KEYPAD_9:
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
            password_check4.setText("*");
            i = 0;
            String pString = password;
            String pString1 = pString.substring(4);
            cSystemManager.getInstance().GetSettings().SetPassword(pString);
            cSystemManager.getInstance().GetSettings().SetmbSecure(true);
            SharedPreferences s = getSharedPreferences("VoshowData", MODE_PRIVATE);
            SharedPreferences.Editor editor = s.edit();
            editor.putString("password", pString1);
            editor.putBoolean("isPasswordCheck",true);
            editor.commit();
            finish();
        }
    }
}

