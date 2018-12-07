package com.example.workbench.VoiceShow.STTModule;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class cVoiceRecorder
{
    // 음성인식 기능 클래스
    private static final int[]  SAMPLE_RATE_CANDIDATES = new int[]{16000, 11025, 22050, 44100};

    private static final int    CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    private static final int    ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    private static final int    AMPLITUDE_THRESHOLD = 1500;
    private static final int    SPEECH_TIMEOUT_MILLIS = 2000;
    private static final int    MAX_SPEECH_LENGTH_MILLIS = 30 * 1000;

    /**
     * 음성녹음 콜백 추상 클래스
     * 음성녹음 기능을 사용할 객체에서 해당 함수를 재정의하여 사용한다.
     */
    public static abstract class cCallback
    {
        /**
         * Called when the recorder starts hearing voice.
         */
        public void onVoiceStart()
        {

        }

        /**
         * Called when the recorder is hearing voice.
         *
         * @param _data The audio data in {@link AudioFormat#ENCODING_PCM_16BIT}.
         * @param _size The size of the actual data in {@code data}.
         */
        public void onVoice(byte[] _data, int _size)
        {

        }

        /**
         * Called when the recorder stops hearing voice.
         */
        public void onVoiceEnd()
        {

        }
    }

    private final cCallback mCallback;                                                  // 콜백 추상 클래스를 사용하기 위해 생선된 객체. 생성자가 호출 될 때 외부에서 정의 된 해당 콜백 객체를 등록해준다.
    private final Object    mLock = new Object();

    private AudioRecord     mAudioRecord;
    private Thread          mThread;                                                    // 음성녹음을 수행할 스레드
    private byte[]          mBuffer;
    private long            mLastVoiceHeardMillis = Long.MAX_VALUE;                     // The timestamp of the last time that voice is heard.
    private long            mVoiceStartedMillis;                                        // The timestamp when the current voice is started.

    public cVoiceRecorder(cCallback mCallback)
    {
        this.mCallback = mCallback;
    }

    /**
     * Starts recording audio.
     *
     * <p>The caller is responsible for calling {@link #stop()} later.</p>
     */
    public void start()
    {
        // Stop recording if it is currently ongoing.
        stop();
        // Try to create a new recording session.
        mAudioRecord        = createAudioRecord();

        if (mAudioRecord == null)
        {
            Log.i("STT Service Info", "start() => mAudioRecord is null");
            throw new RuntimeException("Cannot instantiate VoiceRecorder");
        }

        // Start recording.
        mAudioRecord.startRecording();
        //Start processing the captured audio.
        mThread             = new Thread(new ProcessVoice());
        mThread.start();
        Log.d("Speech Debug", "Thread 생성 완료");
        Log.i("STT Service Info", "start()");
    }

    /**
     * Stops recording audio.
     */
    public void stop()
    {
        synchronized (mLock)
        {
            dismiss();
            if (mThread != null)
            {
                mThread.interrupt();
                mThread     = null;
            }

            if (mAudioRecord != null)
            {
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord= null;
            }

            mBuffer         = null;
            Log.d("Speech Debug", "Thread 제거");
        }
    }

    /**
     * Dismisses the currently ongoing utterance.
     */
    public void dismiss()
    {
        if (mLastVoiceHeardMillis != Long.MAX_VALUE)
        {
            mLastVoiceHeardMillis   = Long.MAX_VALUE;
            mCallback.onVoiceEnd();
        }
    }

    /**
     * Retrieves the sample rate currently used to record audio.
     *
     * @return The sample rate of recorded audio.
     */
    public int getSampleRate()
    {
        if (mAudioRecord != null)
            return mAudioRecord.getSampleRate();
        return 0;
    }

    /**
     * Creates a new {@link AudioRecord}.
     *
     * @return A newly created {@link AudioRecord}, or null if it cannot be created (missing
     * permissions?).
     */
    private AudioRecord createAudioRecord()
    {
        for (int sampleRate : SAMPLE_RATE_CANDIDATES)
        {
            final int       sizeInBytes = AudioRecord.getMinBufferSize(sampleRate, CHANNEL, ENCODING);
            if (sizeInBytes == AudioRecord.ERROR_BAD_VALUE)
                continue;

            final AudioRecord   audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, CHANNEL, ENCODING, sizeInBytes);
            if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED)
            {
                mBuffer     = new byte[sizeInBytes];
                return audioRecord;
            }
            else
                audioRecord.release();
        }
        return null;
    }

    /**
     * Continuously processes the captured audio and notifies {@link #mCallback} of corresponding
     * events.
     */
    private class ProcessVoice implements Runnable
    {
        @Override
        public void run()
        {
            Log.i("STT Service Info", "thread run");
            while (true)
            {
                synchronized (mLock)
                {
                    if (Thread.currentThread().isInterrupted())
                    {
                        break;
                    }

                    final int   size = mAudioRecord.read(mBuffer, 0, mBuffer.length);
                    final long  now = System.currentTimeMillis();
                    if (isHearingVoice(mBuffer, size))
                    {
                        if (mLastVoiceHeardMillis == Long.MAX_VALUE)
                        {
                            int tID = android.os.Process.getThreadPriority(android.os.Process.myTid());
                            Log.i("STT Service Info", "onVoiceStart ["+tID+"]");
                            mVoiceStartedMillis = now;
                            mCallback.onVoiceStart();
                        }
                        mCallback.onVoice(mBuffer, size);
                        mLastVoiceHeardMillis   = now;

                        if (now - mVoiceStartedMillis > MAX_SPEECH_LENGTH_MILLIS)
                        {
                            end();
                        }
                    }
                    else if (mLastVoiceHeardMillis != Long.MAX_VALUE)
                    {
                        mCallback.onVoice(mBuffer, size);
                        if (now - mLastVoiceHeardMillis > SPEECH_TIMEOUT_MILLIS)
                        {
                            end();
                        }
                    }
                }
            }
        }

        private void end()
        {
            mLastVoiceHeardMillis   = Long.MAX_VALUE;
            mCallback.onVoiceEnd();
        }

        private boolean isHearingVoice(byte[] buffer, int size)
        {
            for (int i = 0; i < size -1; i += 2)
            {
                // The buffer has LINEAR16 in little endian.
                int         s = buffer[i + 1];

                if (s < 0)
                {
                    s       *= -1;
                }

                s           <<= 8;
                s           += Math.abs(buffer[i]);

                if (s > AMPLITUDE_THRESHOLD)
                {
                    return true;
                }
            }
            return false;
        }
    }
}
