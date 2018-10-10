package com.example.workbench.VoiceShow.Permissions;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class cPermissionManager
{
    private static cPermissionManager mInstance;
    private int mPermissionResult;

    private Context mContext;
    private Activity mActivity;
    private String[] mPermissions = {Manifest.permission.CALL_PHONE,
            Manifest.permission.INTERNET,
            Manifest.permission.RECORD_AUDIO};

    public static cPermissionManager GetInst()
    {
        if (mInstance == null)
            mInstance = new cPermissionManager();

        return mInstance;
    }

    public void SetContext(Context _context)
    {
        mContext = _context;
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
        ActivityCompat.requestPermissions(mActivity, mPermissions, 1000);
        CheckPermission_CALL_PHONE();
        CheckPermission_INTERNET();
        CheckPermission_RECORD_AUDIO();
    }

    ActivityCompat.OnRequestPermissionsResultCallback onRequestPermissionsResult()
    {
        
    }

    public void CheckPermission_CALL_PHONE()
    {
        mPermissionResult   = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
        if (mPermissionResult == PackageManager.PERMISSION_DENIED)
        {
            // 사용자가 CALL_PHONE 권한을 거부한 적이 있는지 확인한다.
            // 거부한적이 있으면 True 리턴
            // 거부한적이 없으면 False 리턴
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CALL_PHONE))
            {
                // 퍼미션을 거부했으면 어플리케이션 강제 종료.
                mActivity.moveTaskToBack(true);
                mActivity.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }

    public void CheckPermission_INTERNET()
    {
        mPermissionResult   = ContextCompat.checkSelfPermission(mContext, Manifest.permission.INTERNET);
        if (mPermissionResult == PackageManager.PERMISSION_DENIED)
        {
            // 사용자가 INTERNET 권한을 거부한 적이 있는지 확인한다.
            // 거부한적이 있으면 True 리턴
            // 거부한적이 없으면 False 리턴
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.INTERNET))
            {
                // 퍼미션을 거부했으면 어플리케이션 강제 종료.
                mActivity.moveTaskToBack(true);
                mActivity.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }

    public void CheckPermission_RECORD_AUDIO()
    {
        mPermissionResult   = ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO);
        if (mPermissionResult == PackageManager.PERMISSION_DENIED)
        {
            // 사용자가 RECORD_AUDIO 권한을 거부한 적이 있는지 확인한다.
            // 거부한적이 있으면 True 리턴
            // 거부한적이 없으면 False 리턴
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.RECORD_AUDIO))
            {
                // 퍼미션을 거부했으면 어플리케이션 강제 종료.
                mActivity.moveTaskToBack(true);
                mActivity.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }
}
