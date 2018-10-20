package com.example.workbench.VoiceShow.STTModule;

public class cSTTModuleManager
{
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
}
