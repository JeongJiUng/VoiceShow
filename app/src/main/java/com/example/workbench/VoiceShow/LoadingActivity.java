package com.example.workbench.VoiceShow;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.workbench.VoiceShow.Permissions.cPermissionManager;

public class LoadingActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        cPermissionManager.GetInst().Initialize(this);
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strings, int[] ints)
            {
                if (ints.length > 0 && ints[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // 권한 획득에 성공하였을 때 수행할 내용
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                    finish();
                }
                else
                {
            // 권한 획득에 실패했을 때 수행할 내용
            // 퍼미션을 거부했으면 어플리케이션 강제 종료.
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}
