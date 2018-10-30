package com.example.workbench.VoiceShow;

import android.app.Activity;
import android.content.Context;

import com.example.workbench.VoiceShow.STTModule.cSTTModuleManager;
import com.example.workbench.VoiceShow.TelephonyModule.cCallCheckReceiver;

public class cSystemManager
{
    private static final cSystemManager ourInstance = new cSystemManager();

    public static cSystemManager getInstance()
    {
        return ourInstance;
    }

    private cSystemManager()
    {
    }

    private Activity        mActivity;
    private Context         mContext;

    private cSTTModuleManager   mSTTModule;

    private cCallCheckReceiver  mCallReceiver;

    public void Initialize(Activity _act, Context _cont)
    {
        mActivity           = _act;
        mContext            = _cont;

        // initialize Modules
        mSTTModule          = new cSTTModuleManager();
        mCallReceiver       = new cCallCheckReceiver();
    }

    public Activity GetActivity()
    {
        return mActivity;
    }
    public Context GetContext()
    {
        return mContext;
    }

    public cSTTModuleManager GetSTTModule()
    {
        return mSTTModule;
    }
    public cCallCheckReceiver GetCallReceiver()
    {
        return mCallReceiver;
    }
}
