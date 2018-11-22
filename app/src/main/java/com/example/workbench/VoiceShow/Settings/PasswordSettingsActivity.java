package com.example.workbench.VoiceShow.Settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.workbench.VoiceShow.R;

public class PasswordSettingsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_settings);

        final Switch PasswordOn = (Switch)findViewById(R.id.IsPasswordOn);
        ImageButton backbutton = (ImageButton) findViewById((R.id.SETTINGS_BACK_BUTTON3));
        PasswordOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PasswordOn.isChecked()){

                }
                else{

                }
            }
        });
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
