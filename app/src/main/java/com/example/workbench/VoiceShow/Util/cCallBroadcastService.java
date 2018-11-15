package com.example.workbench.VoiceShow.Util;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.workbench.VoiceShow.R;
import com.example.workbench.VoiceShow.STTModule.cSTTModuleManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 앱이 실행중이지 않은 경우에도 기능을 실행하기 위한 서비스
 * 음성인식 모듈에 대한 처리를 이곳에서 진행
 * 오버레이 뷰를 사용.
 * 오버레이 뷰에 발신자의 전화번호 표시
 * 오버레이 뷰에 발신자와 수신자의 대화 내용 기록
 * 윈도우 매니저 레이아웃 타입 : TYPE_APPLICATION_OVERLAY
 * 필요 퍼미션 : SYSTEM_ALERT_WINDOW, ACTION_MANAGE_OVERLAY_PERMISSION
 */
public class cCallBroadcastService extends Service
{
    String                  TAG = "PHONE_STATE_SERVICE";

    private final IBinder   mBinder = new cLocalBinder();

    boolean                 isRinging = false;                                      // Ringing 상태를 거치지 않고 바로 OFFHOOK인 경우 엑티비티 초기화를 위해 사용 됨.
    String                  mCallNumber;
    String                  mTelephonyState;
    protected View          mRootView;

    public static final String  EXTRA_CALL_NUMBER = "call_number";
    public static final String  EXTRA_TELEPHONY_STATE = "telephony_state";          // EXTRA_STATE_OFFHOOK : 통화 상태, EXTRA_STATE_IDLE : 통화 종료
    WindowManager.LayoutParams  mParams;
    private WindowManager   mWindowManager;
    cCustomAdapter          mAdapter;
    cSTTModuleManager       mReceiver;      // 수신자 음성인식
    cSTTModuleManager       mCaller;        // 발신자 음성인식

    @BindView(R.id.CALL_NUMBER)
    TextView                mTvCallNumber;
    @BindView(R.id.BTN_CLOSE)
    ImageButton             mCloseBtn;
    @BindView(R.id.LISTVIEW_CHATLIST)
    ListView                mListView;

    class cLocalBinder extends Binder
    {
        cCallBroadcastService getService()
        {
            return cCallBroadcastService.this;
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        initLayout();

        // 채팅 UI 초기화
        mAdapter            = new cCustomAdapter();
        mListView.setAdapter(mAdapter);

        // cSTTModuleManager 초기 세팅
        mReceiver           = new cSTTModuleManager()
        {
            @Override
            public void GetResultText()
            {
                super.GetResultText();
                mAdapter.addItem(GetSpeechToTextResult(), 1);
                refreshLsitView();
            }
        };

        mCaller             = new cSTTModuleManager()
        {
            @Override
            public void GetResultText()
            {
                super.GetResultText();
                mAdapter.addItem(GetSpeechToTextResult(), 0);
                refreshLsitView();
            }
        };
    }

    public void initLayout()
    {
        // 팝업으로 사용 될 Layout 크기 조정
        mWindowManager      = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display             display = mWindowManager.getDefaultDisplay();
        int                 width = (int)(display.getWidth() * 0.9);    // Display 사이즈의 90%

        mParams             = new WindowManager.LayoutParams(width,
                                                            WindowManager.LayoutParams.WRAP_CONTENT,
                                                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,    // 오버레이 뷰를 사용하려면 TYPE_APPLICATION_OVERLAY 타입으로 하고, SYSTEM_ALERT_WINDOW 퍼미션과 ACTION_MANAGE_OVERLAY_PERMISSION를 줘야 함.
                                                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                                                            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                                                            PixelFormat.TRANSLUCENT);

        LayoutInflater      layoutInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        mRootView           = layoutInflater.inflate(R.layout.overlay_chatview, null);

        try
        {
            ButterKnife.bind(this, mRootView);
        }
        catch (Exception e)
        {
            Log.d(TAG, e.toString());
        }

        setDraggable();

        // 종료버튼 리스너 등록
        mCloseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                removeOverlay();
            }
        });
    }

    private void setDraggable()
    {
        mRootView.setOnTouchListener(new View.OnTouchListener()
        {
            private int     initialX;
            private int     initialY;
            private float   initialTouchX;
            private float   initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        initialX    = mParams.x;
                        initialY    = mParams.y;
                        initialTouchX   = event.getRawX();
                        initialTouchY   = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_UP:
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        mParams.x   = initialX + (int)(event.getRawX() - initialTouchX);
                        mParams.y   = initialY + (int)(event.getRawY() - initialTouchY);

                        if (mRootView != null)
                            mWindowManager.updateViewLayout(mRootView, mParams);
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * 세팅해 놓은 팝업View 를 WindowManager 에 추가
     * 넘겨 받은 전화번호 정보를 가져와서 팝업View 의 TextView 에도 세팅
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // Extra로 넘어온 데이터 추출
        setExtra(intent);

        switch (mTelephonyState)
        {
            case "OFFHOOK":
                if (isRinging == false)
                {
                    onStartRinging();
                    InitSTT();
                }
                StartSTT();
                break;

            case "IDLE":
                StopSTT();
                // TODO:: 음성 데이터 저장
                break;

            case "RINGING":
                onStartRinging();
                break;
        }

        return START_REDELIVER_INTENT;  // service가 강제종료 되더라도 다시 시작해주고 이전에 넘겨받았던 intent를 그대로 넘겨받을 수 있다.
    }

    /**
     * RINGING 상태로 서비스가 넘어왔을 때 해당 함수 수행
     */
    private void onStartRinging()
    {
        // WindowManager에 팝업View 등록
        try
        {
            mWindowManager.addView(mRootView, mParams);
        }
        catch (Exception e)
        {
            Log.i(TAG, e.toString());
        }

        if (!TextUtils.isEmpty(mCallNumber))
            mTvCallNumber.setText(mCallNumber);

        isRinging           = true;
    }

    private void setExtra(Intent intent)
    {
        if (intent == null)
        {
            removeOverlay();
            return;
        }

        mCallNumber         = intent.getStringExtra(EXTRA_CALL_NUMBER);
        mTelephonyState     = intent.getStringExtra(EXTRA_TELEPHONY_STATE);
    }

    private void removeOverlay()
    {
        if (mRootView != null && mWindowManager != null)
        {
            mWindowManager.removeView(mRootView);
            mRootView       = null;
            stopSelf();
        }
    }

    @Override
    public IBinder onBind(Intent _intent)
    {
        return mBinder;
    }

    final Handler           mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            mAdapter.notifyDataSetChanged();
        }
    };

    private void refreshLsitView()
    {
        Message         msg = mHandler.obtainMessage();
        mHandler.sendMessage(msg);
    }

    public void InitSTT()
    {
        Log.i(TAG, "Init STT");
        mReceiver.onInit();
        //mCaller.onInit();
    }

    public void StartSTT()
    {
        Log.i(TAG, "Start STT");
        mReceiver.onStart();
        //mCaller.onStart();
    }

    public void StopSTT()
    {
        Log.i(TAG, "Stop STT");
        mReceiver.onStop();
        //mCaller.onStop();
    }
}
