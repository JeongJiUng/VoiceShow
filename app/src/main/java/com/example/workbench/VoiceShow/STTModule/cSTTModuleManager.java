package com.example.workbench.VoiceShow.STTModule;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.workbench.VoiceShow.cSystemManager;

public class cSTTModuleManager
{
    private cSpeechService  mSpeechService;
    private cVoiceRecorder  mVoiceRecorder;

    public cSTTModuleManager()
    {
        mSpeechService      = new cSpeechService();
    }

    /**
     * 음성 녹음 콜백 모듈
     */
    private final cVoiceRecorder.cCallback  mVoiceCallback = new cVoiceRecorder.cCallback()
    {
        @Override
        public void onVoiceStart()
        {
            super.onVoiceStart();
            if (mSpeechService != null)
                mSpeechService.startRecognizing(mVoiceRecorder.getSampleRate());
        }

        @Override
        public void onVoice(byte[] _data, int _size)
        {
            super.onVoice(_data, _size);
            if (mSpeechService != null)
                mSpeechService.recognize(_data, _size);
        }

        @Override
        public void onVoiceEnd()
        {
            super.onVoiceEnd();
            if (mSpeechService != null)
                mSpeechService.finishRecognizing();
        }
    };

    private final ServiceConnection mServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mSpeechService  = cSpeechService.from(service);
            mSpeechService.addListener(mSpeechServiceListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mSpeechService = null;
        }
    };

    /**
     * 음성인식 시작
     * 음성인식에 필요한 구글 SpeechService 기능이 초기화 되어있지 않다면 초기화 수행.
     * 음성인식에 필요한 리스너(mServiceConnection) 등록 및 서비스 바인드
     */
    public void onStart()
    {
        if (mSpeechService == null)
            mSpeechService  = new cSpeechService();
        // Prepare Cloud Speech API
        cSystemManager.getInstance().GetActivity().bindService(new Intent(cSystemManager.getInstance().GetActivity(), cSpeechService.class), mServiceConnection, Context.BIND_AUTO_CREATE);

        // Start listening to voices
        startVoiceRecorder();
    }

    /**
     * 음성인식 중단
     * 리스너 해제와 서비스 언바인드.
     * 음성인식에 필요한 구글 SpeechService 기능 해제
     */
    public void onStop()
    {
        // Stop listening to voice
        stopVoiceRecorder();
        if (mSpeechService != null)
        {
            mSpeechService.removeListener(mSpeechServiceListener);
            cSystemManager.getInstance().GetActivity().unbindService(mServiceConnection);
            mSpeechService.onDestroy();
            mSpeechService  = null;
        }
    }

    public void onSaveInstanceState(Bundle outState)
    {

    }

    /**
     * 마이크를 통한 음성 녹음 기능 시작
     */
    private void startVoiceRecorder()
    {
        if (mVoiceRecorder != null)
        {
            mVoiceRecorder.stop();
        }

        // mVoiceRecorder 초기화 및 mVoiceCallback 등록
        mVoiceRecorder      = new cVoiceRecorder(mVoiceCallback);
        mVoiceRecorder.start();
    }

    /**
     * 마이크를 통한 음성 녹음 기능 중단
     */
    private void stopVoiceRecorder()
    {
        if (mVoiceRecorder != null)
        {
            mVoiceRecorder.stop();
            mVoiceRecorder  = null;
        }
    }

    private final cSpeechService.Listener mSpeechServiceListener = new cSpeechService.Listener()
    {
        @Override
        public void onSpeechRecognized(String _text, boolean _isFinal)
        {
            if (_isFinal)
            {
                mVoiceRecorder.dismiss();
            }
            Log.d("Speech Debug", _text);
        }
    };
}
