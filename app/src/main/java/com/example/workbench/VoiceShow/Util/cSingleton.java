package com.example.workbench.VoiceShow.Util;

public class cSingleton
{
    private static cSingleton   mInstance;

    public static cSingleton GetInst()
    {
        if (mInstance == null)
            mInstance       = new cSingleton();

        return mInstance;
    }
}
