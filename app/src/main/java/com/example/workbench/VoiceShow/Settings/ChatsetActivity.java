package com.example.workbench.VoiceShow.Settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.util.DisplayMetrics;
import android.widget.RadioGroup;
import android.support.annotation.IdRes;

import com.example.workbench.VoiceShow.MainActivity;
import com.example.workbench.VoiceShow.R;
import com.example.workbench.VoiceShow.cSystemManager;

public class ChatsetActivity extends AppCompatActivity {
    public RadioGroup radioGroup;
    public RadioButton r_btn1;
    public RadioButton r_btn2;
    public RadioButton r_btn3;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatset);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));


        r_btn1 = (RadioButton) findViewById(R.id.radio1);
        r_btn2 = (RadioButton) findViewById(R.id.radio2);
        r_btn3 = (RadioButton) findViewById(R.id.radio3);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);

        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.radio1:
                        cSystemManager.getInstance().GetSettings().SetChatCapacity(50);
                        break;
                    case R.id.radio2:
                        cSystemManager.getInstance().GetSettings().SetChatCapacity(100);
                        break;
                    case R.id.radio3:
                        cSystemManager.getInstance().GetSettings().SetChatCapacity(150);
                        break;
                }
            }
        };
    }
}
