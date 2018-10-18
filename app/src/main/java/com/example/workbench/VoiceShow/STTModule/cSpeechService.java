package com.example.workbench.VoiceShow.STTModule;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.cloud.speech.v1.SpeechGrpc;

public class cSpeechService extends Service
{
    // 구글 Cloud Speech to text 기능 구현 클래스
    public interface Listener
    {
        /**
         * Called when a new piece of text was recognized by the Speech API.
         *
         * @param _text    The text.
         * @param _isFinal {@code true} when the API finished processing audio.
         */
        void onSpeechRecognized(String _text, boolean _isFinal);
    }

    private static final String TAG = "SpeechService";

    private static final String PREFS = "SpeechService";
    private static final String PREF_ACCESS_TOKEN_VALUE = "access_token_value";
    private static final String PREF_ACCESS_TOKEN_EXPIRATION_TIME = "access_token_expiration_time";

    /** We reuse an access token if its expiration time is longer than this. */
    private static final int    ACCESS_TOKEN_EXPIRATION_TOLERANCE = 30 * 60 * 1000; // thirty minutes
    /** We refresh the current access token before it expires. */
    private static final int    ACCESS_TOKEN_FETCH_MARGIN = 60 * 1000; // one minute

    public static final List<String>    SCOPE = Collections.singletonList("https://www.googleapis.com/auth/cloud-platform");
    private static final String HOSTNAME = "speech.googleapis.com";
    private static final int    PORT = 443;

    private final SpeechBinder  mBinder = new SpeechBinder();
    private final ArrayList<Listener>   mListeners = new ArrayList<>();
    private volatile AccessTokenTask    mAccessTokenTask;
    private SpeechGrpc.SpeechStub   mApi;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
