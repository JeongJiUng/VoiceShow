package com.example.workbench.VoiceShow.Settings;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import com.example.workbench.VoiceShow.cSystemManager;

import com.example.workbench.VoiceShow.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {
    Dialog dialog;
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        dialog = new Dialog(this);

        SharedPreferences s = getSharedPreferences("VoshowData", MODE_PRIVATE);
        cSystemManager.getInstance().GetSettings().SetChatCapacity(s.getInt("chatLength", 50));
        cSystemManager.getInstance().GetSettings().SetDeleteFreq(s.getInt("deleteFreq",7));
        cSystemManager.getInstance().GetSettings().SetPassword(s.getString("password","-1111"));
        cSystemManager.getInstance().GetSettings().SetmbSecure(s.getBoolean("isPasswordCheck",false));

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
        startActivity(new Intent(SettingsActivity.this,ChatsetActivity.class));
        //Button b2;
        //dialog.setContentView(R.layout.activity_chatset);
        //b2 = (Button)dialog.findViewById(R.id.SETTINGS_CHATLETTER);
        //dialog.show();
    }
    public void setDeleteFrequency(View v){
        startActivity(new Intent(SettingsActivity.this,DeleteFreqActivity.class));
        //Button b3;
        //dialog.setContentView(R.layout.activity_delete_freq);
        //b3 = (Button)dialog.findViewById(R.id.SETTINGS_DEL_FREQ);
        //dialog.show();
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
