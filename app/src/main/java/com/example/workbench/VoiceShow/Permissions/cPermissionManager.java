package com.example.workbench.VoiceShow.Permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class cPermissionManager
{
    private static cPermissionManager mInstance;

    private Activity mActivity;

    //private static final int    MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private String[]        mPermissions = {Manifest.permission.CALL_PHONE,
                            Manifest.permission.INTERNET,
                            Manifest.permission.RECORD_AUDIO};

    public static cPermissionManager GetInst()
    {
        if (mInstance == null)
            mInstance = new cPermissionManager();

        return mInstance;
    }

    public void SetActivity(Activity _act)
    {
        mActivity = _act;
    }

    public void CheckPermissionDenied()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            GetPermissions();
        }
    }

    public void GetPermissions()
    {
        mActivity.requestPermissions(mPermissions, 1000);
    }

 /*   public ActivityCompat.OnRequestPermissionsResultCallback RequestPermissionsResult = new ActivityCompat.OnRequestPermissionsResultCallback()
    {
        @Override
        public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints)
        {
            switch (i)
            {
                case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                    if (ints.length > 0 && ints[0] == PackageManager.PERMISSION_GRANTED)
                    {
                        // TODO:: 권한 획득에 성공하였을 때 수행할 내용
                    }
                    else
                    {
                        // TODO:: 권한 획득에 실패했을 때 수행할 내용
                        // 퍼미션을 거부했으면 어플리케이션 강제 종료.
                        mActivity.moveTaskToBack(true);
                        mActivity.finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
            }
        }
    };*/
}
