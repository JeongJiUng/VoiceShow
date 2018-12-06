package com.example.workbench.VoiceShow.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.workbench.VoiceShow.cSystemManager;

/**
 * 단말기의 통화 상태를 감지하기 위해 리시버를 사용하여 브로드케스팅 한다.
 * 통화의 상태에 따른 처리를 이곳에서 한다.
 * CALL_STATE_IDLE : 아무 행동도 없는 상태
 * CALL_STATE_RINGING : 전화가 오고 있는 상태
 * CALL_STATE_OFFHOOK : 전화를 걸거나, 전화중인 상태(통화 시작)
 */
public class cCallBroadcastReceiver extends BroadcastReceiver
{
    boolean                 isFirst = true;                                                         // 본인이 전화를 걸때는 OFFHOOK으로, 전화가 왔을 때는 RINGING으로 통화 상태가 넘어옴. 이걸 구분해서 서비스에 연결하기 위해 해당 변수 추가.
    String                  TAG = "PHONE_STATE_RECEIVER";

    private static String   mLastState;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String              state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (state.equals(mLastState))
        {
            return;
        }
        else
        {
            mLastState      = state;
        }

        cSystemManager.getInstance().SetContext(context);
        cSystemManager.getInstance().SetIntent(intent);

        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING))
        {
            // 전화가 왔을 때
            String          incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER); // 전화 온 번호
            //String          incomingNumber = "01089471758";                                                     // 테스트용 임시
            final String    phoneNumber = PhoneNumberUtils.formatNumber(incomingNumber);                        // String 형으로 변경
            Intent          serviceIntent = new Intent(context, cCallBroadcastService.class);                   // 현재 화면(리시버)에서 넘어갈 컴포넌트 설정(서비스);
            serviceIntent.putExtra(cCallBroadcastService.EXTRA_CALL_NUMBER, phoneNumber);                       // 서비스에 전달 할 데이터

            try
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    context.startForegroundService(serviceIntent);                                              // 포어그라운드 서비스 시작
                }
                else
                {
                    context.startService(serviceIntent);                                                        // 서비스 시작
                }
            }
            catch (Exception e)
            {
                Log.i(TAG, "RINGING_START_SERVICE " + e.toString());
            }

            isFirst         = false;
        }
        else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
        {
            if (isFirst == true)
            {
                //String          incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER); // 전화 온 번호
                String          incomingNumber = "01089471758";                                                 // 테스트용 임시
                final String    phoneNumber = PhoneNumberUtils.formatNumber(incomingNumber);                    // String 형으로 변경
                Intent          serviceIntent = new Intent(context, cCallBroadcastService.class);               // 현재 화면(리시버)에서 넘어갈 컴포넌트 설정(서비스);
                serviceIntent.putExtra(cCallBroadcastService.EXTRA_CALL_NUMBER, phoneNumber);                   // 서비스에 전달 할 데이터

                try
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        context.startForegroundService(serviceIntent);                                          // 포어그라운드 서비스 시작
                    }
                    else
                    {
                        context.startService(serviceIntent);                                                    // 서비스 시작
                    }
                }
                catch (Exception e)
                {
                    Log.i(TAG, "OFFHOOK_START_SERVICE " + e.toString());
                }
            }
        }
        else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE))
        {
            isFirst         = true;
        }
    }
}