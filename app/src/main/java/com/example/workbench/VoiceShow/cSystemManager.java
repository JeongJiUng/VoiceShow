package com.example.workbench.VoiceShow;

class cSystemManager
{
    private static final cSystemManager ourInstance = new cSystemManager();

    static cSystemManager getInstance()
    {
        return ourInstance;
    }

    private cSystemManager()
    {
    }

}
