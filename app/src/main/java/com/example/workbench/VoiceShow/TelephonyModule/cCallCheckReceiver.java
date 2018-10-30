package com.example.workbench.VoiceShow.TelephonyModule;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.workbench.VoiceShow.cSystemManager;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 전화 상태 및 전화 상태 변화에대한 리스너 관리 클래스
 */
public class cCallCheckReceiver
{
    TelephonyManager        mTelManager = (TelephonyManager)cSystemManager.getInstance().GetActivity().getSystemService(TELEPHONY_SERVICE); // 안드로이드 폰의 전화 서비스에 대한 정보에 접근하기 위한 객체
    int                     mInitialCallState = mTelManager.getCallState();                         // 어플리케이션 실행되고 최초 통화 상태

    public PhoneStateListener   mPhoneStateListener = new PhoneStateListener()
    {
        /**
         * CALL_STATE_IDLE : 아무 행동도 없는 상태
         * CALL_STATE_RINGING : 전화가 오고 있는 상태
         * CALL_STATE_OFFHOOK : 전화를 걸거나, 전화중인 상태
         * @param _state
         * @param _incomingNumber
         */
        @Override
        public void onCallStateChanged(int _state, String _incomingNumber)
        {
            Log.d("cCallCheckReceiver", "onCallStateChanged input");
            switch(_state)
            {
                case TelephonyManager.CALL_STATE_IDLE:
                    cSystemManager.getInstance().GetSTTModule().onStop();
                    break;

                case TelephonyManager.CALL_STATE_RINGING:
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    cSystemManager.getInstance().GetSTTModule().onStart();
                    break;
            }
        }
    };
}
