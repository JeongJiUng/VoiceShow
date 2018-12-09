package com.example.workbench.VoiceShow.Settings;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.workbench.VoiceShow.cSystemManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.workbench.VoiceShow.R;
import com.google.protobuf.NullValue;

import static java.sql.Types.NULL;

public class PasswordSettingsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_settings);
        //Intent intent = getIntent();

        Boolean isPasswordOn = cSystemManager.getInstance().GetSettings().GetEnabledSecure();

        final Switch PasswordOn = (Switch)findViewById(R.id.IsPasswordOn);

        ImageButton backbutton = (ImageButton) findViewById((R.id.SETTINGS_BACK_BUTTON3));
        if(cSystemManager.getInstance().GetSettings().GetEnabledSecure()) {
            PasswordOn.setChecked(true);
        }

        PasswordOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordOn.setChecked(false);
                cSystemManager.getInstance().GetSettings().SetmbSecure(false);
                SharedPreferences s = getSharedPreferences("VoshowData", MODE_PRIVATE);
                SharedPreferences.Editor editor = s.edit();
                editor.putBoolean("isPasswordCheck",false);
                editor.putString("password","-1111");
                editor.commit();
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        final Switch PasswordOn = (Switch)findViewById(R.id.IsPasswordOn);

        if(cSystemManager.getInstance().GetSettings().GetEnabledSecure())
            PasswordOn.setChecked(true);

    }
    public void ChangePassword(View v) {
        startActivity(new Intent(PasswordSettingsActivity.this,PasswordActivity.class));
    }
}
