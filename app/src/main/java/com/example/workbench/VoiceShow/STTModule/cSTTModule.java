package com.example.workbench.VoiceShow.STTModule;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;


import com.google.cloud.speech.v1p1beta1.RecognitionAudio;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1p1beta1.RecognizeResponse;
import com.google.cloud.speech.v1p1beta1.SpeechClient;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class cSTTModule
{
    public void Initialize()
    {
        //TODO:: Google Cloud to Text 기능 초기화
    }

    public void SetPermission(Context _context)
    {
        //TODO:: Android Permission 등록 및 확인
        // Permission : INTERNET , RECORD_AUDIO
        // 퍼미션 체크. 안드로이드 버전 23 이상이면 퍼미션 체크하는 알림창을 띄워줘야 한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int             permissionResult = ContextCompat.checkSelfPermission(_context, Manifest.permission.INTERNET);
        }
    }
}
