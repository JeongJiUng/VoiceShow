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
    private int       mThemeID;                               //테마
    private int       mdDleteFreq;                           //삭제 주기
    private int      []password         = new int[4];       //설정된 비밀번호
    private int      []password_check  = new int[4];       //비밀번호 확인을 위해 입력된 번호
    private boolean  mbSecure;                              //비밀번호가 설정되어 있는지 확인
    private String    mVersion;                              //버젼 정보

    //보안 설정 여부 반환
    public boolean GetEnabledSecure(){

        return true;
    }
    //현재 버전과 최신 버전 비교. 같을 경우 True반환, 다를 경우 False 반환
    public boolean CheckVersion(){

        return true;
    }
    //테마 변경
    public void ChangeTheme(int _id){

    }
    //글자 수 변경
    public void SetChatCapacity(int _capacitiy){

    }
    //패스워드 설정
    public void SetPassword(String _Pass){

    }
    //설정된 테바 아이디 반환
    public int GetTheme(){

        return -1;
    }
    //설정된 글자 수 반환
    public int GetChatCapacity(){

        return -1;
    }
    //패스워드 반환
    public String GetPassword(){

        return "";
    }
    //버전이 맞지 않을 경우 업데이트
    public void UpdateVersion(){

    }
}
