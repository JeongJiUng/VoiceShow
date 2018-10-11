package com.example.workbench.VoiceShow.STTModule;

import android.media.AudioFormat;
import android.media.AudioRecord;

public class cVoiceRecorder
{
    private static final int[]  SAMPLE_RATE_CANDIDATES = new int[]{16000, 11025, 22050, 44100};

    private static final int    CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    private static final int    ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    private static final int    AMPLITUDE_THRESHOLD = 1500;
    private static final int    SPEECH_TIMEOUT_MILLIS = 2000;
    private static final int    MAX_SPEECH_LENGTH_MILLIS = 30 * 1000;

    public static abstract class cCallback
    {
        public void onVoiceStart()
        {

        }

        public void onVoice(byte[] _data, int _size)
        {

        }

        public void onVoiceEnd()
        {

        }
    }

    private final cCallback mCallback;
    private final Object    mLock = new Object();

    private AudioRecord     mAudioRecord;
    private Thread          mThread;
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
            throw new RuntimeException("Cannot instantiate VoiceRecorder");

        // Start recording.
        mAudioRecord.startRecording();
        //Start processing the captured audio.
        mThread             = new Thread(new ProcessVoice());
        mThread.start();
    }

    /**
     * Stops recording audio.
     */
    public void stop()
    {

    }

    /**
     * Dismisses the currently ongoing utterance.
     */
    public void dismiss()
    {

    }

    /**
     * Retrieves the sample rate currently used to record audio.
     *
     * @return The sample rate of recorded audio.
     */
    public int getSampleRate()
    {

    }

    /**
     * Creates a new {@link AudioRecord}.
     *
     * @return A newly created {@link AudioRecord}, or null if it cannot be created (missing
     * permissions?).
     */
    private AudioRecord createAudioRecord()
    {

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

        }

        private void end()
        {

        }

        private boolean isHearingVoice(byte[] buffer, int size)
        {
            return false;
        }
    }
}
