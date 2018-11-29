package com.example.workbench.VoiceShow.Settings;

import android.app.Dialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;
import com.example.workbench.VoiceShow.cSystemManager;

import com.example.workbench.VoiceShow.R;

public class SettingsActivity extends AppCompatActivity {
    Dialog dialog;
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        dialog = new Dialog(this);

        Button buttonView1 = (Button)findViewById(R.id.SETTINGS_THEME);
        Button buttonView3 = (Button)findViewById(R.id.SETTINGS_DEL_FREQ);
        Button buttonView4 = (Button)findViewById(R.id.SETTINGS_PASSWORD);
        Button buttonView5 = (Button)findViewById(R.id.SETTINGS_VERSION);
        ImageView backbutton = (ImageView)findViewById((R.id.SETTINGS_BACK_BUTTON));

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    public void openThemeActivity(View v){
        startActivity(new Intent(SettingsActivity.this,ThemeActivity.class));
    }
    public void setChatNum(View v){
        Button b2;
        dialog.setContentView(R.layout.activity_chatset);
        b2 = (Button)dialog.findViewById(R.id.SETTINGS_CHATLETTER);

        dialog.show();
    }
    public void setDeleteFrequency(View v){
        Button b3;
        dialog.setContentView(R.layout.activity_delete_freq);
        b3 = (Button)dialog.findViewById(R.id.SETTINGS_DEL_FREQ);

        dialog.show();
    }
    public void setSecurity(View v){
        Intent intent = new Intent(SettingsActivity.this,PasswordSettingsActivity.class);
        //boolean value = cSystemManager.getInstance().GetSettings().GetEnabledSecure();
        //intent.putExtra("isPasswordOn",value);
        startActivity(intent);
    }
    public void showVersion(View v){
        Toast.makeText(getApplicationContext(),"최신 버젼입니다",Toast.LENGTH_LONG).show();
    }
}
