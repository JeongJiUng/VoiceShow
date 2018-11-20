package com.example.workbench.VoiceShow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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

    private Activity        mActivity = null;
    private Context         mContext = null;
    private Intent          mIntent = null;

    private cSTTModuleManager   mSTTModule;

    public void Initialize(Activity _act, Context _cont)
    {
        mActivity           = _act;
        mContext            = _cont;

        // initialize Modules
        mSTTModule          = new cSTTModuleManager();
    }

    public void SetActivity(Activity _act)
    {
        mActivity           = _act;
    }

    public void SetContext(Context _cont)
    {
        mContext            = _cont;
    }

    public void SetIntent(Intent _intent)
    {
        mIntent             = _intent;
    }

    public Activity GetActivity()
    {
        return mActivity;
    }
    public Context GetContext()
    {
        return mContext;
    }

    public Intent GetIntent()
    {
        return mIntent;
    }

    public cSTTModuleManager GetSTTModule()
    {
        return mSTTModule;
    }
}
