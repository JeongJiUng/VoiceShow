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
                        i=50;break;
                    case R.id.radio2:
                        i=100;break;
                    case R.id.radio3:
                        i=150;break;
                }
            }
        };
        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup1); // 라디오그룹 객체 맵핑
        RadioButton selectedRdo = (RadioButton)findViewById(rg.getCheckedRadioButtonId()); // rg 라디오그룹의 체크된(getCheckedRadioButtonId) 라디오버튼 객체 맵핑
        String selectedValue = selectedRdo.getText().toString(); // 해당 라디오버튼 객체의 값 가져오기
        selectedValue = selectedValue.equals("무한") ? "00" : selectedValue; // 삼항연산자 (체크된 값이 "무한" 이 참이면 "00"으로, 거짓이면 원래 selectedValue 그대로)

        Intent intent = new Intent(ChatsetActivity.this, MainActivity.class);    // 보내는 클래스, 받는 클래스
        intent.putExtra("chatNum", selectedValue); // "TIME"이란 키 값으로 selectedValue를 넘김
        startActivity(intent);
    }
}
