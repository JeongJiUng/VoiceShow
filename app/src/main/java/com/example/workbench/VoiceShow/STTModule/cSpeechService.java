package com.example.workbench.VoiceShow.STTModule;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.google.auth.oauth2.AccessToken;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechGrpc;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import com.google.cloud.speech.v1.StreamingRecognizeRequest;
import com.google.cloud.speech.v1.*;

import com.google.protobuf.ByteString;

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
        final StringBuilder language = new StringBuilder(locale.getLanguage());
        final String        country = locale.getCountry();

        if (!TextUtils.isEmpty(country))
        {
            language.append("-");
            language.append(country);
        }
        return language.toString();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    public void addListener(@NonNull Listener listener)
    {
        mListeners.add(listener);
    }

    public void removeListener(@NonNull Listener listener)
    {
        mListeners.remove(listener);
    }

    /**
     * Starts recognizing speech audio.
     *
     * @param sampleRate The sample rate of the audio.
     */
    public void startRecognizing(int sampleRate)
    {
        if (mApi == null)
        {
            Log.w(TAG, "API not ready. Ignoring the request.");
            return;
        }
        // Configure the API
        mRequestObserver = mApi.streamingRecognize(mResponseObserver);
        mRequestObserver.onNext(StreamingRecognizeRequest.newBuilder().setStreamingConfig(StreamingRecognitionConfig.newBuilder().setConfig(RecognitionConfig.newBuilder()
                                .setLanguageCode(getDefaultLanguageCode()).setEncoding(RecognitionConfig.AudioEncoding.LINEAR16).setSampleRateHertz(sampleRate).build())
                                .setInterimResults(true).setSingleUtterance(true).build()).build());
    }

    /**
     * Recognizes the speech audio. This method should be called every time a chunk of byte buffer
     * is ready.
     *
     * @param data The audio data.
     * @param size The number of elements that are actually relevant in the {@code data}.
     */
    public void recognize(byte[] data, int size)
    {
        if (mRequestObserver == null)
        {
            return;
        }
        // Call the streaming recognition API
        mRequestObserver.onNext(StreamingRecognizeRequest.newBuilder().setAudioContent(ByteString.copyFrom(data, 0, size)).build());
    }

    /**
     * Recognize all data from the specified {@link InputStream}.
     *
     * @param stream The audio data.
     */
    public void recognizeInputStream(InputStream stream)
    {
        try
        {
            mApi.recognize(RecognizeRequest.newBuilder().setConfig(RecognitionConfig.newBuilder().setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                                    .setLanguageCode("en-US").setSampleRateHertz(16000).build()).setAudio(RecognitionAudio.newBuilder().setContent(ByteString.readFrom(stream))
                                    .build()).build(), mFileResponseObserver);
        }
        catch (IOException e)
        {
            Log.e(TAG, "Error loading the input", e);
        }
    }

    private class SpeechBinder extends Binder
    {
        cSpeechService getService()
        {
            return cSpeechService.this;
        }
    }

    private final Runnable mFetchAccessTokenRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            fetchAccessToken();
        }
    };

    private class AccessTokenTask extends AsyncTask<Void, Void, AccessToken>
    {
        @Override
        protected AccessToken doInBackground(Void... voids)
        {
            final SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            String          tokenValue = prefs.getString(PREF_ACCESS_TOKEN_VALUE, null);
            long            expirationTime = prefs.getLong(PREF_ACCESS_TOKEN_EXPIRATION_TIME, -1);

            // Check if the current token is still valid for a while
            if (tokenValue != null && expirationTime > 0)
            {
                if (expirationTime > System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TOLERANCE)
                {
                    return new AccessToken(tokenValue, new Date(expirationTime));
                }
            }

            // TODO:: Access Token

            return null;
        }
    }
}
