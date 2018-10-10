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
    private static cPermissionManager   mInstance;
    private int             mPermissionResult;

    private Context         mContext;
    private Activity        mActivity;

    public static cPermissionManager GetInst()
    {
        if (mInstance == null)
            mInstance       = new cPermissionManager();

        return mInstance;
    }

    public void SetContext(Context _context)
    {
        mContext            = _context;
    }

    public void SetActivity(Activity _act)
    {
        mActivity           = _act;
    }

    public void CheckPermissionDenied()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            GetPermission_CALL_PHONE();
            GetPermission_INTERNET();
            GetPermission_RECORD_AUDIO();
        }
    }

    public void GetPermission_CALL_PHONE()
    {
        mPermissionResult   = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
        if (mPermissionResult == PackageManager.PERMISSION_DENIED)
        {
            // 사용자가 CALL_PHONE 권한을 거부한 적이 있는지 확인한다.
            // 거부한적이 있으면 True 리턴
            // 거부한적이 없으면 False 리턴
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CALL_PHONE))
            {
                // 거부한 적이 있는 경우
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("권한이 필요합니다.").setMessage("이 기능을 사용하기 위해서는 \"전화걸기\" 권한이 필요합니다. 계속 하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // 새로운 인스턴스(onClickListener)를 생성했기 때문에 버전체크를 다시 해준다.
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                {
                                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                }
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(mActivity, "기능을 취소했습니다", Toast.LENGTH_SHORT).show();

                                mActivity.moveTaskToBack(true);
                                mActivity.finish();
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .create().show();
            }
            else
            {
                // 거부한 적이 없는 경우
                // CALL_PHONE 권한을 OS에 요청
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CALL_PHONE}, 1000);
            }
        }
    }

    public void GetPermission_INTERNET()
    {
        mPermissionResult   = ContextCompat.checkSelfPermission(mContext, Manifest.permission.INTERNET);
        if (mPermissionResult == PackageManager.PERMISSION_DENIED)
        {
            // 사용자가 CALL_PHONE 권한을 거부한 적이 있는지 확인한다.
            // 거부한적이 있으면 True 리턴
            // 거부한적이 없으면 False 리턴
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.INTERNET))
            {
                // 거부한 적이 있는 경우
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("권한이 필요합니다.").setMessage("이 기능을 사용하기 위해서는 \"인터넷 연결\" 권한이 필요합니다. 계속 하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // 새로운 인스턴스(onClickListener)를 생성했기 때문에 버전체크를 다시 해준다.
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                {
                                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.INTERNET}, 1000);
                                }
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(mActivity, "기능을 취소했습니다", Toast.LENGTH_SHORT).show();

                                mActivity.moveTaskToBack(true);
                                mActivity.finish();
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .create().show();
            }
            else
            {
                // 거부한 적이 없는 경우
                // CALL_PHONE 권한을 OS에 요청
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.INTERNET}, 1000);
            }
        }
    }

    public void GetPermission_RECORD_AUDIO()
    {
        mPermissionResult   = ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO);
        if (mPermissionResult == PackageManager.PERMISSION_DENIED)
        {
            // 사용자가 CALL_PHONE 권한을 거부한 적이 있는지 확인한다.
            // 거부한적이 있으면 True 리턴
            // 거부한적이 없으면 False 리턴
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.RECORD_AUDIO))
            {
                // 거부한 적이 있는 경우
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("권한이 필요합니다.").setMessage("이 기능을 사용하기 위해서는 \"인터넷 연결\" 권한이 필요합니다. 계속 하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // 새로운 인스턴스(onClickListener)를 생성했기 때문에 버전체크를 다시 해준다.
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                {
                                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.RECORD_AUDIO}, 1000);
                                }
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(mActivity, "기능을 취소했습니다", Toast.LENGTH_SHORT).show();

                                mActivity.moveTaskToBack(true);
                                mActivity.finish();
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .create().show();
            }
            else
            {
                // 거부한 적이 없는 경우
                // CALL_PHONE 권한을 OS에 요청
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.RECORD_AUDIO}, 1000);
            }
        }
    }
}
