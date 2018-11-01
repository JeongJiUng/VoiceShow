package com.example.workbench.VoiceShow.Util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 앱이 실행중이지 않은 경우에도 기능을 실행하기 위한 서비스
 * 음성인식 모듈에 대한 처리를 이곳에서 진행
 */
public class cCallBroadcastService extends Service
{
    String                  TAG = "PHONE STATE";

    public cCallBroadcastService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
