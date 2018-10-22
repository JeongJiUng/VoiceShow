package com.example.workbench.VoiceShow.STTModule;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;

public class cSTTModuleManager
{
    private Activity        mActivity;
    private cSpeechService  mSpeechService;
    private cVoiceRecorder  mVoiceRecorder;

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

    private RecyclerView mRecyclerView;

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

    public void onStart()
    {
        // Prepare Cloud Speech API
        mActivity.bindService(new Intent(mActivity, cSpeechService.class), mServiceConnection, Context.BIND_AUTO_CREATE);

        // Start listening to voices
        startVoiceRecorder();
    }

    public void onStop()
    {
        // Stop listening to voice
        stopVoiceRecorder();
        mSpeechService.removeListener(mSpeechServiceListener);
        mActivity.unbindService(mServiceConnection);
        mSpeechService      = null;
    }

    public void onSaveInstanceState(Bundle outState)
    {

    }

    private void startVoiceRecorder()
    {
        if (mVoiceRecorder != null)
        {
            mVoiceRecorder.stop();
        }
        mVoiceRecorder      = new cVoiceRecorder(mVoiceCallback);
        mVoiceRecorder.start();
    }

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
        }
    };
}
