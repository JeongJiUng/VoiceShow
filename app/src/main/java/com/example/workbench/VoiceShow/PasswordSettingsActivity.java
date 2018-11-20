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

public class PasswordSettingsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_settings);

        ImageButton backbutton = (ImageButton) findViewById((R.id.SETTINGS_BACK_BUTTON3));
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void ChangePassword(View v) {
        startActivity(new Intent(PasswordSettingsActivity.this,PasswordActivity.class));
    }
}
