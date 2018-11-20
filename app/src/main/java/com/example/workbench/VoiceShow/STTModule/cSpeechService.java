package com.example.workbench.VoiceShow.STTModule;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
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
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.workbench.VoiceShow.cSystemManager;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.SpeechGrpc;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import com.google.cloud.speech.v1.StreamingRecognizeRequest;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.auth.ClientAuthInterceptor;
import io.grpc.okhttp.OkHttpChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.okhttp.OkHttpChannelProvider;

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

    public static final List<String>    SCOPE = Collections.singletonList("https://www.googleapis.com/auth/cloud-platform");
    private static final String HOSTNAME = "speech.googleapis.com";
    private static final int    PORT = 443;

    private final SpeechBinder  mBinder = new SpeechBinder();
    private final ArrayList<Listener>   mListeners = new ArrayList<>();
    private SpeechGrpc.SpeechStub   mApi;                                                           // 음성인식을 위한 음성녹음 모듈 (음성인식 클라이언트)

    public cSpeechService()
    {
        onCreate();
    }

    /**
     * Cloud 서버에 있는 Speech API를 사용하기 위해 비공개 키값을 사용한 인증 절차 수행
     * @param _host
     * @param _port
     * @param _credentials
     * @return
     * @throws IOException
     */
    private ManagedChannel CreateChannel(String _host, int _port, InputStream _credentials) throws IOException
    {
        GoogleCredentials   creds = GoogleCredentials.fromStream(_credentials);
        creds               = creds.createScoped(SCOPE);
        OkHttpChannelProvider   provider = new OkHttpChannelProvider();
        OkHttpChannelBuilder    builder = provider.builderForAddress(_host, _port);
        ManagedChannel      channel = builder.intercept(new ClientAuthInterceptor(creds, Executors.newSingleThreadExecutor())).build();
        _credentials.close();

        return channel;
    }

    /**
     * 실시간 음성인식 기능 모듈
     * 음성인식을 Cloud 서버로 전송하면서 결과를 얻어온다.
     */
    private final StreamObserver<StreamingRecognizeResponse>    mResponseObserver = new StreamObserver<StreamingRecognizeResponse>()
    {
        /**
         * 음성인식으로 변형된 텍스트를 가져온다.
         * @param value Cloud 서버로 전송 된 음성 데이터를 텍스트로 변환하여 얻은 결과
         */
        @Override
        public void onNext(StreamingRecognizeResponse value)
        {
            String          text = null;
            boolean         isFinal = false;

            Log.d("Speech Debug", "onNext");

            if (value.getResultsCount() > 0)
            {
                final StreamingRecognitionResult result = value.getResults(0);
                isFinal     = result.getIsFinal();

                if (result.getAlternativesCount() > 0)
                {
                    final SpeechRecognitionAlternative  alternative = result.getAlternatives(0);
                    text    = alternative.getTranscript();
                    Log.d("Speech Debug_onNext", text);
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

    private StreamObserver<StreamingRecognizeRequest>   mRequestObserver;                           // 음성인식 요청 객체

    public static cSpeechService from(IBinder binder)
    {
        return ((SpeechBinder)binder).getService();
    }

    public void onCreate()
    {
        // json 파일을 읽어들이기 위해 AssetManager 생성
        Context             context = cSystemManager.getInstance().GetContext();
        AssetManager        assetManager = context.getResources().getAssets();

        try
        {
            InputStream     credentials = assetManager.open("credentials.json");
            ManagedChannel  channel = CreateChannel(HOSTNAME, PORT, credentials);
            mApi            = SpeechGrpc.newStub(channel);
        }
        catch (Exception e)
        {
            Log.e(this.getClass().getSimpleName(), "Error onCreate", e);
        }
    }

    public void onDestroy()
    {
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
     * 음성 인식 결과를 받을 리스너(mResponseObserver)를 등록
     * 음성녹음으로 데이터를 얻고 얻은 음성 데이터를 mResponseObserver 통해 결과를 받고,
     * mRequestObserver 를 통해 Cloud 서버로 음성데이터를 텍스트로 변환 요청한다.
     * @param sampleRate The sample rate of the audio.
     */
    public void startRecognizing(int sampleRate)
    {
        Log.d("Speech Debug", "startRecognizing");
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
     * Finishes recognizing speech audio.
     */
    public void finishRecognizing()
    {
        if (mRequestObserver == null)
        {
            return;
        }
        mRequestObserver.onCompleted();
        mRequestObserver    = null;
    }


    private class SpeechBinder extends Binder
    {
        cSpeechService getService()
        {
            return cSpeechService.this;
        }
    }
}
