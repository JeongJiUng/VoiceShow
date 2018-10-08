package com.example.workbench.VoiceShow.Permissions;

import android.Manifest;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.workbench.VoiceShow.Util.cSingleton;

public class cPermissionManager extends cSingleton
{
    private int             mPermissionResult;
    private Context         mContext;

    public void SetContext(Context _context)
    {
        mContext            = _context;
    }

    public int GetPermissionDenied()
    {
        GetPermission_CALL_PHONE();
        GetPermission_INTERNET();

        return mPermissionResult;
    }

    public int GetPermission_CALL_PHONE()
    {
        mPermissionResult   = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
        return mPermissionResult;
    }

    public int GetPermission_INTERNET()
    {
        mPermissionResult   = ContextCompat.checkSelfPermission(mContext, Manifest.permission.INTERNET);
        return mPermissionResult;
    }
}
