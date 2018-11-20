package com.example.workbench.VoiceShow.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;

public class cCallBroadcastReceiver extends BroadcastReceiver
{
    String                  TAG = "PHONE STATE";

    private final           Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d(TAG, "onReceive()");
        String              state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state))
        {
            
        }

        throw new UnsupportedOperationException("Not yet implemented");
    }
}