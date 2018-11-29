package com.example.workbench.VoiceShow.Settings;

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
import com.example.workbench.VoiceShow.cSystemManager;

import com.example.workbench.VoiceShow.R;

public class PasswordActivity extends AppCompatActivity {

    private int password_num1;
    private int password_num2;
    private int password_num3;
    private int password_num4;
    private String password_star1;
    private String password_star2;
    private String password_star3;
    private String password_star4;
    public int i = 0;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        ImageButton backbutton = (ImageButton)findViewById((R.id.SETTINGS_BACK_BUTTON4));
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void ButtonClick(View v) {
        // activity_main 에서 발생되는 버튼 이벤트 처리.
        // 핸드폰 번호를 보여 줄 텍스트뷰 아이디
        TextView    password_check1 = (TextView)findViewById(R.id.Password_Num1);
        TextView    password_check2 = (TextView)findViewById(R.id.Password_Num2);
        TextView    password_check3 = (TextView)findViewById(R.id.Password_Num3);
        TextView    password_check4 = (TextView)findViewById(R.id.Password_Num4);

        switch (v.getId()) {
            case R.id.Password_BACK_SPACE:
                if(i==0){
                    break;
                }
                else if(i == 1) {
                    password_star1 = "";
                    password_num1 = -1;
                    password_check1.setText(password_star1);
                }
                else if(i == 2) {
                    password_star2 = "";
                    password_num2 = -1;
                    password_check2.setText(password_star2);
                }
                else if(i == 3){
                    password_star3 = "";
                    password_num3 = -1;
                    password_check3.setText(password_star3);
                }
                i--;
                break;
            case R.id.Password_KEYPAD_0:
                if(i == 0) {
                    password_star1 = "*";
                    password_num1 = 0;
                    password_check1.setText(password_star1);
                    i++;
                }
                else if(i == 1) {
                    password_star2 = "*";
                    password_num2 = 0;
                    password_check2.setText(password_star2);
                    i++;
                }
                else if(i == 2){
                    password_star3 = "*";
                    password_num3 = 0;
                    password_check3.setText(password_star3);
                    i++;
                }
                else if(i == 3){
                    password_star4 = "*";
                    password_num4 = 0;
                    password_check4.setText(password_star4);
                    i = 0;
                    int pInt = password_num1 * 1000 + password_num2 * 100 + password_num3 *10 + password_num4;
                    String pString = String.valueOf(pInt);
                    cSystemManager.getInstance().GetSettings().SetPassword(pString);
                    cSystemManager.getInstance().GetSettings().SetmbSecure(true);
                    finish();
                }
                break;
            case R.id.Password_KEYPAD_1:
                if(i == 0) {
                    password_star1 = "*";
                    password_num1 = 1;
                    password_check1.setText(password_star1);
                    i++;
                }
                else if(i == 1) {
                    password_star2 = "*";
                    password_num2 = 1;
                    password_check2.setText(password_star2);
                    i++;
                }
                else if(i == 2){
                    password_star3 = "*";
                    password_num3 = 1;
                    password_check3.setText(password_star3);
                    i++;
                }
                else if(i == 3){
                    password_star4 = "*";
                    password_num4 = 1;
                    password_check4.setText(password_star4);
                    i = 0;
                    int pInt = password_num1 * 1000 + password_num2 * 100 + password_num3 *10 + password_num4;
                    String pString = String.valueOf(pInt);
                    cSystemManager.getInstance().GetSettings().SetPassword(pString);
                    cSystemManager.getInstance().GetSettings().SetmbSecure(true);
                    finish();
                }
                break;
            case R.id.Password_KEYPAD_2:
                if(i == 0) {
                    password_star1 = "*";
                    password_num1 = 2;
                    password_check1.setText(password_star1);
                    i++;
                }
                else if(i == 1) {
                    password_star2 = "*";
                    password_num2 = 2;
                    password_check2.setText(password_star2);
                    i++;
                }
                else if(i == 2){
                    password_star3 = "*";
                    password_num3 = 2;
                    password_check3.setText(password_star3);
                    i++;
                }
                else if(i == 3){
                    password_star4 = "*";
                    password_num4 = 2;
                    password_check4.setText(password_star4);
                    i = 0;
                    int pInt = password_num1 * 1000 + password_num2 * 100 + password_num3 *10 + password_num4;
                    String pString = String.valueOf(pInt);
                    cSystemManager.getInstance().GetSettings().SetPassword(pString);
                    cSystemManager.getInstance().GetSettings().SetmbSecure(true);
                    finish();
                }
                break;
            case R.id.Password_KEYPAD_3:
                if(i == 0) {
                    password_star1 = "*";
                    password_num1 = 3;
                    password_check1.setText(password_star1);
                    i++;
                }
                else if(i == 1) {
                    password_star2 = "*";
                    password_num2 = 3;
                    password_check2.setText(password_star2);
                    i++;
                }
                else if(i == 2){
                    password_star3 = "*";
                    password_num3 = 3;
                    password_check3.setText(password_star3);
                    i++;
                }
                else if(i == 3){
                    password_star4 = "*";
                    password_num4 = 3;
                    password_check4.setText(password_star4);
                    i = 0;
                    int pInt = password_num1 * 1000 + password_num2 * 100 + password_num3 *10 + password_num4;
                    String pString = String.valueOf(pInt);
                    cSystemManager.getInstance().GetSettings().SetPassword(pString);
                    cSystemManager.getInstance().GetSettings().SetmbSecure(true);
                    finish();
                }
                break;
            case R.id.Password_KEYPAD_4:
                if(i == 0) {
                    password_star1 = "*";
                    password_num1 = 4;
                    password_check1.setText(password_star1);
                    i++;
                }
                else if(i == 1) {
                    password_star2 = "*";
                    password_num2 = 4;
                    password_check2.setText(password_star2);
                    i++;
                }
                else if(i == 2){
                    password_star3 = "*";
                    password_num3 = 4;
                    password_check3.setText(password_star3);
                    i++;
                }
                else if(i == 3){
                    password_star4 = "*";
                    password_num4 = 4;
                    password_check4.setText(password_star4);
                    i = 0;
                    int pInt = password_num1 * 1000 + password_num2 * 100 + password_num3 *10 + password_num4;
                    String pString = String.valueOf(pInt);
                    cSystemManager.getInstance().GetSettings().SetPassword(pString);
                    cSystemManager.getInstance().GetSettings().SetmbSecure(true);
                    finish();
                }
                break;
            case R.id.Password_KEYPAD_5:
                if(i == 0) {
                    password_star1 = "*";
                    password_num1 = 5;
                    password_check1.setText(password_star1);
                    i++;
                }
                else if(i == 1) {
                    password_star2 = "*";
                    password_num2 = 5;
                    password_check2.setText(password_star2);
                    i++;
                }
                else if(i == 2){
                    password_star3 = "*";
                    password_num3 = 5;
                    password_check3.setText(password_star3);
                    i++;
                }
                else if(i == 3){
                    password_star4 = "*";
                    password_num4 = 5;
                    password_check4.setText(password_star4);
                    i = 0;
                    int pInt = password_num1 * 1000 + password_num2 * 100 + password_num3 *10 + password_num4;
                    String pString = String.valueOf(pInt);
                    cSystemManager.getInstance().GetSettings().SetPassword(pString);
                    cSystemManager.getInstance().GetSettings().SetmbSecure(true);
                    finish();
                }
                break;
            case R.id.Password_KEYPAD_6:
                if(i == 0) {
                    password_star1 = "*";
                    password_num1 = 6;
                    password_check1.setText(password_star1);
                    i++;
                }
                else if(i == 1) {
                    password_star2 = "*";
                    password_num2 = 6;
                    password_check2.setText(password_star2);
                    i++;
                }
                else if(i == 2){
                    password_star3 = "*";
                    password_num3 = 6;
                    password_check3.setText(password_star3);
                    i++;
                }
                else if(i == 3){
                    password_star4 = "*";
                    password_num4 = 6;
                    password_check4.setText(password_star4);
                    i = 0;
                    int pInt = password_num1 * 1000 + password_num2 * 100 + password_num3 *10 + password_num4;
                    String pString = String.valueOf(pInt);
                    cSystemManager.getInstance().GetSettings().SetPassword(pString);
                    cSystemManager.getInstance().GetSettings().SetmbSecure(true);
                    finish();
                }
                break;
            case R.id.Password_KEYPAD_7:
                if(i == 0) {
                    password_star1 = "*";
                    password_num1 = 7;
                    password_check1.setText(password_star1);
                    i++;
                }
                else if(i == 1) {
                    password_star2 = "*";
                    password_num2 = 7;
                    password_check2.setText(password_star2);
                    i++;
                }
                else if(i == 2){
                    password_star3 = "*";
                    password_num3 = 7;
                    password_check3.setText(password_star3);
                    i++;
                }
                else if(i == 3){
                    password_star4 = "*";
                    password_num4 = 7;
                    password_check4.setText(password_star4);
                    i = 0;
                    int pInt = password_num1 * 1000 + password_num2 * 100 + password_num3 *10 + password_num4;
                    String pString = String.valueOf(pInt);
                    cSystemManager.getInstance().GetSettings().SetPassword(pString);
                    cSystemManager.getInstance().GetSettings().SetmbSecure(true);
                    finish();
                }
                break;
            case R.id.Password_KEYPAD_8:
                if(i == 0) {
                    password_star1 = "*";
                    password_num1 = 8;
                    password_check1.setText(password_star1);
                    i++;
                }
                else if(i == 1) {
                    password_star2 = "*";
                    password_num2 = 8;
                    password_check2.setText(password_star2);
                    i++;
                }
                else if(i == 2){
                    password_star3 = "*";
                    password_num3 = 8;
                    password_check3.setText(password_star3);
                    i++;
                }
                else if(i == 3){
                    password_star4 = "*";
                    password_num4 = 8;
                    password_check4.setText(password_star4);
                    i = 0;
                    int pInt = password_num1 * 1000 + password_num2 * 100 + password_num3 *10 + password_num4;
                    String pString = String.valueOf(pInt);
                    cSystemManager.getInstance().GetSettings().SetPassword(pString);
                    cSystemManager.getInstance().GetSettings().SetmbSecure(true);
                    finish();
                }
                break;
            case R.id.Password_KEYPAD_9:
                if(i == 0) {
                    password_star1 = "*";
                    password_num1 = 9;
                    password_check1.setText(password_star1);
                    i++;
                }
                else if(i == 1) {
                    password_star2 = "*";
                    password_num2 = 9;
                    password_check2.setText(password_star2);
                    i++;
                }
                else if(i == 2){
                    password_star3 = "*";
                    password_num3 = 9;
                    password_check3.setText(password_star3);
                    i++;
                }
                else if(i == 3){
                    password_star4 = "*";
                    password_num4 = 9;
                    password_check4.setText(password_star4);
                    i = 0;
                    int pInt = password_num1 * 1000 + password_num2 * 100 + password_num3 *10 + password_num4;
                    String pString = String.valueOf(pInt);
                    cSystemManager.getInstance().GetSettings().SetPassword(pString);
                    cSystemManager.getInstance().GetSettings().SetmbSecure(true);
                    finish();
                }
                break;
        }
    }
}
