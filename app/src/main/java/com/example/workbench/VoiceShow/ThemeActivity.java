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
import android.widget.Toast;
import android.widget.LinearLayout;
import android.graphics.Color;

import com.example.workbench.VoiceShow.R;

public class ThemeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        ImageView backbutton = (ImageView)findViewById((R.id.SETTINGS_BACK_BUTTON2));

        ImageView theme1 = (ImageView)findViewById((R.id.theme_black));
        ImageView theme2 = (ImageView)findViewById((R.id.theme_blue));
        ImageView theme3 = (ImageView)findViewById((R.id.theme_green));
        ImageView theme4 = (ImageView)findViewById((R.id.theme_yellow));


        theme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"블랙 테마 설정됨",Toast.LENGTH_LONG).show();
            }
        });
        theme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"블루 테마 설정됨",Toast.LENGTH_LONG).show();
            }
        });
        theme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"그린 테마 설정됨",Toast.LENGTH_LONG).show();
            }
        });
        theme4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"옐로 테마 설정됨",Toast.LENGTH_LONG).show();
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
