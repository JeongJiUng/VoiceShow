package com.example.workbench.VoiceShow;

import android.app.Activity;

import com.example.workbench.VoiceShow.STTModule.cSTTModuleManager;

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
    private cSTTModuleManager mSTTModule;

    public void Initialize(Activity _act)
    {
        mActivity           = _act;
    }

    public Activity GetActivity()
    {
        return mActivity;
    }
}
