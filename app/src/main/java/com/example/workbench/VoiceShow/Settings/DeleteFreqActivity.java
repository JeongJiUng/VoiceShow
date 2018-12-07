package com.example.workbench.VoiceShow.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.util.DisplayMetrics;
import android.widget.RadioGroup;
import android.support.annotation.IdRes;
import android.widget.Toast;

import com.example.workbench.VoiceShow.MainActivity;
import com.example.workbench.VoiceShow.R;
import com.example.workbench.VoiceShow.cSystemManager;

public class DeleteFreqActivity extends AppCompatActivity {
    public RadioGroup radioGroup;
    public RadioButton r_btn1;
    public RadioButton r_btn2;
    public RadioButton r_btn3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_freq);

        //DisplayMetrics dm = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(dm);

        //int width = dm.widthPixels;
        //int height = dm.heightPixels;

        //getWindow().setLayout((int) (width * .8), (int) (height * .6));

        r_btn1 = (RadioButton) findViewById(R.id.radio1);
        r_btn2 = (RadioButton) findViewById(R.id.radio2);
        r_btn3 = (RadioButton) findViewById(R.id.radio3);

        if (cSystemManager.getInstance().GetSettings().GetDeleteFreq() == 1) {
            r_btn1.setChecked(true);
            r_btn2.setChecked(false);
            r_btn3.setChecked(false);
        } else if (cSystemManager.getInstance().GetSettings().GetDeleteFreq() == 2) {
            r_btn1.setChecked(false);
            r_btn2.setChecked(true);
            r_btn3.setChecked(false);
        } else if (cSystemManager.getInstance().GetSettings().GetDeleteFreq() == 3) {
            r_btn1.setChecked(false);
            r_btn2.setChecked(false);
            r_btn3.setChecked(true);
        }
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(rg);
    }
    RadioGroup.OnCheckedChangeListener rg = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.radio1){
                cSystemManager.getInstance().GetSettings().SetDeleteFreq(1);
                SharedPreferences s = getSharedPreferences("VoshowData", MODE_PRIVATE);
                SharedPreferences.Editor editor = s.edit();
                editor.putInt("deleteFreq",1);
                editor.commit();
                onBackPressed();
            }
            else if(checkedId == R.id.radio2){
                cSystemManager.getInstance().GetSettings().SetDeleteFreq(2);
                SharedPreferences s = getSharedPreferences("VoshowData", MODE_PRIVATE);
                SharedPreferences.Editor editor = s.edit();
                editor.putInt("deleteFreq",2);
                editor.commit();
                onBackPressed();
            }
            else if(checkedId == R.id.radio3){
                cSystemManager.getInstance().GetSettings().SetDeleteFreq(3);
                SharedPreferences s = getSharedPreferences("VoshowData", MODE_PRIVATE);
                SharedPreferences.Editor editor = s.edit();
                editor.putInt("deleteFreq",3);
                editor.commit();
                onBackPressed();
            }
        }
    };
}
