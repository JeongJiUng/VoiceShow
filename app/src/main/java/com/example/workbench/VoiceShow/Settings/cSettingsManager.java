package com.example.workbench.VoiceShow.Settings;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import com.example.workbench.VoiceShow.R;
import com.example.workbench.VoiceShow.cSystemManager;

public class cSettingsManager
{
    private int       mChatCapacity;                         //채팅 글자 수
    private int       mDeleteFreq;                           //삭제 주기
    private int       mThemeID;                               //테마
    private String    mPassword;                               //설정된 비밀번호
    private boolean  mbSecure;                               //비밀번호가 설정되어 있는지 확인
    private String    mVersion;                               //버젼 정보

    public void Initialize(){
        mChatCapacity = 50;
        mDeleteFreq = 1;
        mThemeID = 1;
        mPassword = "-1111";
        mbSecure = false;
        mVersion = "1.0.0";
    }

    //테마 변경
    public void ChangeTheme(int _id){

        //추후에 시간 남으면 작성

    }
    //설정된 테마 아이디 반환
    public int GetTheme(){
        return mThemeID;
    }
    //글자 수 변경
    public void SetChatCapacity(int _capacitiy){
        mChatCapacity = _capacitiy;
    }
    //설정된 글자 수 반환
    public int GetChatCapacity(){
        return mChatCapacity;
    }
    //삭제 주기 변경
    public void SetDeleteFreq(int _deleteFreq){
        mDeleteFreq = _deleteFreq;
    }
    //삭제 주기 반환
    public int GetDeleteFreq(){
        return mDeleteFreq;
    }
    //보안 설정 여부 반환
    public boolean GetEnabledSecure(){
        return mbSecure;
    }
    //보안 설정 여부 변수 설정
    public void SetmbSecure(boolean _mbSecure){
        mbSecure = _mbSecure;
    }
    //패스워드 설정
    public void SetPassword(String _Pass){
        mPassword = _Pass;
    }
    //패스워드 반환
    public String GetPassword(){
        if(cSystemManager.getInstance().GetSettings().GetEnabledSecure()) {
            return mPassword;
        }
        else{
            return "-1111";
        }
    }
    //현재 버전과 최신 버전 비교. 같을 경우 True반환, 다를 경우 False 반환
    public boolean CheckVersion(){
        if(mVersion.equals("1.0.0")) {
            return true;
        }
        else{
            UpdateVersion();
            return false;
        }
    }
    //버전이 맞지 않을 경우 업데이트
    public void UpdateVersion(){

    }
}
