package com.example.workbench.VoiceShow.STTModule;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import com.example.workbench.VoiceShow.R;
import com.example.workbench.VoiceShow.cSystemManager;

public class cSTTModuleManager
{
    private cSpeechService  mSpeechService;
    private cVoiceRecorder  mVoiceRecorder;

    private String          mText;  // 음성인식 결과

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
     * 음성인식 시작 전 단계
     * 음성인식에 필요한 구글 SpeechService 기능이 초기화 되어있지 않다면 초기화 수행.
     * 음성인식에 필요한 리스너(mServiceConnection) 등록 및 서비스 바인드
     */
    public void onInit()
    {
        if (mSpeechService == null)
            mSpeechService  = new cSpeechService();
        // Prepare Cloud Speech API
        cSystemManager.getInstance().GetActivity().bindService(new Intent(cSystemManager.getInstance().GetActivity(), cSpeechService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
        mSpeechService.addListener(mSpeechServiceListener);
    }

    /**
     * 음성인식 시작
     */
    public void onStart()
    {
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

    /**
     * mSpeechService 에서 리스너로 등록하여 onNext에서 음성인식 결과를 해당 리스너로 받을 수 있도록 설정.
     */
    private final cSpeechService.Listener mSpeechServiceListener = new cSpeechService.Listener()
    {
        @Override
        public void onSpeechRecognized(String _text, boolean _isFinal)
        {
            mText           = _text;
            Log.i("Speech Debug_onSpeechRecognized", _text);

            // 문장이 완성 되었을 때 (더이상 아무 말도 하지 않았을 때)
            // TODO:: 이 부분에서 타이머 체크로 3초 이상 말 안했을 경우 처리 가능할 듯.
            if (_isFinal)
            {
                GetResultText();
                //mVoiceRecorder.dismiss();
            }
        }
    };

    public String GetSpeechToTextResult()
    {
        return mText;
    }

    /**
     * Override 전용 함수.
     * 해당 함수를 Override 해서 채팅 UI등 원하는 곳에서 결과 반환.
     */
    public void GetResultText()
    {

    }
}
