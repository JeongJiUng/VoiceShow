package com.example.workbench.VoiceShow.Util;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 단말기의 통화 상태를 감지하기 위해 리시버를 사용하여 브로드케스팅 한다.
 * 통화의 상태에 따른 처리를 이곳에서 한다.
 * CALL_STATE_IDLE : 아무 행동도 없는 상태
 * CALL_STATE_RINGING : 전화가 오고 있는 상태
 * CALL_STATE_OFFHOOK : 전화를 걸거나, 전화중인 상태(통화 시작)
 */
public class cCallBroadcastReceiver extends BroadcastReceiver
{
    String                  TAG = "PHONE_STATE_RECEIVER";

    private final           Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i(TAG, "onReceive()");
        String              state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE))
        {
            // 임시 코드
            Toast.makeText(context, "EXTRA_STATE_IDLE", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "EXTRA_STATE_IDLE");

            Intent              serviceIntent = new Intent(context, cCallBroadcastService.class);           // 현재 화면(리시버)에서 넘어갈 컴포넌트 설정(서비스);
            serviceIntent.putExtra(cCallBroadcastService.EXTRA_TELEPHONY_STATE, state);                     // 현재 통화 상태 전달
            context.startService(serviceIntent);                                                            // 서비스 시작
        }
        else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING))
        {
            // 임시 코드
            Toast.makeText(context, "EXTRA_STATE_RINGING", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "EXTRA_STATE_RINGING");

            // 전화가 왔을 때
            String          incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER); // 전화 온 번호
            //String          incomingNumber = "01089471758";                                                 // 테스트용 임시
            final String    phoneNumber = PhoneNumberUtils.formatNumber(incomingNumber);                    // String 형으로 변경
            Intent              serviceIntent = new Intent(context, cCallBroadcastService.class);           // 현재 화면(리시버)에서 넘어갈 컴포넌트 설정(서비스);
            serviceIntent.putExtra(cCallBroadcastService.EXTRA_CALL_NUMBER, phoneNumber);                   // 서비스에 전달 할 데이터
            serviceIntent.putExtra(cCallBroadcastService.EXTRA_TELEPHONY_STATE, state);                     // 현재 통화 상태 전달
            context.startService(serviceIntent);                                                            // 서비스 시작
        }
        else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
        {
            // 임시 코드
            Toast.makeText(context, "EXTRA_STATE_OFFHOOK", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "EXTRA_STATE_OFFHOOK");

            Intent              serviceIntent = new Intent(context, cCallBroadcastService.class);           // 현재 화면(리시버)에서 넘어갈 컴포넌트 설정(서비스);
            serviceIntent.putExtra(cCallBroadcastService.EXTRA_TELEPHONY_STATE, state);                     // 현재 통화 상태 전달
            context.startService(serviceIntent);                                                            // 서비스 시작
        }
    }
}