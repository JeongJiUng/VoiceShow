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

public class activity_SETTINGS extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView textView1 = (TextView)findViewById(R.id.SETTINGS_THEME);
        TextView textView2 = (TextView)findViewById(R.id.SETTINGS_CHATLETTER);
        TextView textView3 = (TextView)findViewById(R.id.SETTINGS_DEL_FREQ);
        TextView textView4 = (TextView)findViewById(R.id.SETTINGS_PASSWORD);
        TextView textView5 = (TextView)findViewById(R.id.SETTINGS_VERSION);
        ImageView backbutton = (ImageView)findViewById((R.id.SETTINGS_BACK_BUTTON));

        textView1.setBackgroundColor(Color.RED);
        textView2.setBackgroundColor(Color.YELLOW);
        textView3.setBackgroundColor(Color.GREEN);
        textView4.setBackgroundColor(Color.BLUE);
        textView5.setBackgroundColor(Color.GRAY);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"아직 미완성 기능입니다",Toast.LENGTH_LONG).show();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"아직 미완성 기능입니다",Toast.LENGTH_LONG).show();
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"아직 미완성 기능입니다",Toast.LENGTH_LONG).show();
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"아직 미완성 기능입니다",Toast.LENGTH_LONG).show();
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"아직 미완성 기능입니다",Toast.LENGTH_LONG).show();
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
