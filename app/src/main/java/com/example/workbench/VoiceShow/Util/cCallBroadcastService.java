package com.example.workbench.VoiceShow.Util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class cCallBroadcastService extends Service
{
    String                  TAG = "PHONE STATE";

    public cCallBroadcastService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
