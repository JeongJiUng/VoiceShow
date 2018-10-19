package com.example.workbench.VoiceShow.STTModule;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechGrpc;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import com.google.cloud.speech.v1.StreamingRecognizeRequest;
import com.google.cloud.speech.v1.*;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

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
    private static Handler  mHandler;

    private final StreamObserver<StreamingRecognizeResponse>    mResponseObserver = new StreamObserver<StreamingRecognizeResponse>()
    {

        @Override
        public void onNext(StreamingRecognizeResponse value)
        {
            String          text = null;
            boolean         isFinal = false;
            if (value.getResultsCount() > 0)
            {
                final StreamingRecognitionResult result = value.getResults(0);
                isFinal     = result.getIsFinal();

                if (result.getAlternativesCount() > 0)
                {
                    final SpeechRecognitionAlternative  alternative = result.getAlternatives(0);
                    text    = alternative.getTranscript();
                }
            }

            if (text != null)
            {
                for (Listener listener : mListeners)
                {
                    listener.onSpeechRecognized(text, isFinal);
                }
            }
        }

        @Override
        public void onError(Throwable t)
        {
            Log.e(TAG, "Error calling the API.", t);
        }

        @Override
        public void onCompleted()
        {
            Log.i(TAG, "API completed.");
        }
    };

    private final StreamObserver<RecognizeResponse> mFileResponseObserver = new StreamObserver<RecognizeResponse>()
    {
        @Override
        public void onNext(RecognizeResponse value)
        {
            String text     = null;

            if (value.getResultsCount() > 0)
            {
                final SpeechRecognitionResult   result = value.getResults(0);

                if (result.getAlternativesCount() > 0)
                {
                    final SpeechRecognitionAlternative  alternative = result.getAlternatives(0);
                    text    = alternative.getTranscript();
                }
            }
            if (text != null)
            {
                for (Listener listener : mListeners)
                {
                    listener.onSpeechRecognized(text, true);
                }
            }
        }

        @Override
        public void onError(Throwable t)
        {
            Log.e(TAG, "Error calling the API.", t);
        }

        @Override
        public void onCompleted()
        {
            Log.i(TAG, "API completed.");
        }
    };

    private StreamObserver<StreamingRecognizeRequest>   mRequestObserver;

    public static cSpeechService from(IBinder binder)
    {
        return ((SpeechBinder)binder).getService();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mHandler            = new Handler();
        fetchAccessToken();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mHandler.removeCallbacks(mFetchAccessTokenRunnable);
        mHandler            = null;

        // Release the gRPC channel.
        if(mApi != null)
        {
            final ManagedChannel    channel = (ManagedChannel)mApi.getChannel();

            if (channel != null && !channel.isShutdown())
            {
                try
                {
                    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                }
                catch (InterruptedException e)
                {
                    Log.e(TAG, "Error shutting down the gRPC channel.", e);
                }
            }
        }
        mApi                = null;
    }

    private void fetchAccessToken()
    {
        if (mAccessTokenTask != null)
            return;

        mAccessTokenTask    = new AccessTokenTask();
        mAccessTokenTask.execute();
    }

    private String getDefaultLanguageCode()
    {
        final Locale        locale = Locale.getDefault();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
