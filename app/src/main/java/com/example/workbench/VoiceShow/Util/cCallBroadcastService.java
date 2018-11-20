package com.example.workbench.VoiceShow.Util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workbench.VoiceShow.MainActivity;
import com.example.workbench.VoiceShow.R;
import com.example.workbench.VoiceShow.STTModule.cSTTModuleManager;
import com.example.workbench.VoiceShow.cSystemManager;

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
    String                  mCallNumber;
    cSTTModuleManager       mReceiver;      // 수신자 음성인식
    cSTTModuleManager       mCaller;        // 발신자 음성인식
    public static final String  EXTRA_CALL_NUMBER = "call_number";

    WindowManager.LayoutParams  mParams;
    private WindowManager   mWindowManager;
    protected View          mRootView;
    cCustomAdapter          mAdapter;

    @BindView(R.id.CALL_NUMBER)
    TextView                mTvCallNumber;
    @BindView(R.id.BTN_CLOSE)
    ImageButton             mCloseBtn;
    @BindView(R.id.LISTVIEW_CHATLIST)
    ListView                mListView;

    TelephonyManager        mTelManager; // 안드로이드 폰의 전화 서비스에 대한 정보에 접근하기 위한 객체
    public PhoneStateListener   mPhoneStateListener = new PhoneStateListener()
    {
        /**
         * CALL_STATE_IDLE : 아무 행동도 없는 상태
         * CALL_STATE_RINGING : 전화가 오고 있는 상태
         * CALL_STATE_OFFHOOK : 전화를 걸거나, 전화중인 상태(통화 시작)
         * @param _state
         * @param _incomingNumber
         */
        @Override
        public void onCallStateChanged(int _state, String _incomingNumber)
        {
            switch(_state)
            {
                case TelephonyManager.CALL_STATE_IDLE:
                    removeOverlay();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    StartSTT();
                    break;
            }
            super.onCallStateChanged(_state, _incomingNumber);
        }
    };

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
                //removeOverlay();
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
     * Notification을 클릭하면 MainActivity로 이동하도록 PendingIntent 등록
     * 사용자 정의 Notification 보여준다.
     */
    void startForegroundService()
    {
        /*Intent              notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent       pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        RemoteViews         remoteViews = new RemoteViews(getPackageName(), R.layout.activity_main);

        NotificationCompat.Builder  builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String          CHANNEL_ID = "voshow_service_channel";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Voshow Service Channel", NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            builder         = new NotificationCompat.Builder(this, CHANNEL_ID);
        }
        else
        {
            builder         = new NotificationCompat.Builder(this);
        }
        builder.setSmallIcon(R.mipmap.ic_launcher).setContent(remoteViews).setContentIntent(pendingIntent);
        startForeground(1, builder.build());*/

        NotificationCompat.Builder  builder = new NotificationCompat.Builder(this, "default");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(null);
        builder.setContentText(null);
        Intent              notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent       pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            manager.createNotificationChannel(new NotificationChannel("default", "default channel", NotificationManager.IMPORTANCE_DEFAULT));
        }

        Notification        notification = builder.build();
        startForeground(1, notification);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        startForegroundService();
        initLayout();

        // 채팅 UI 초기화
        mAdapter            = new cCustomAdapter();
        mListView.setAdapter(mAdapter);

        // Telephony State 리스너 등록
        mTelManager         = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        mTelManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

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

        InitSTT();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        setExtra(intent);

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

        return START_REDELIVER_INTENT;  // Service가 강제종료 되더라도 다시 시작해주고 이전에 넘겨받았떤 intent를 그대로 넘겨받을 수 있다.
    }

    private void setExtra(Intent intent)
    {
        if (intent == null)
        {
            removeOverlay();
            return;
        }

        mCallNumber         = intent.getStringExtra(EXTRA_CALL_NUMBER);
    }

    private void removeOverlay()
    {
        if (mRootView != null && mWindowManager != null)
        {
            mWindowManager.removeView(mRootView);
            mRootView       = null;

            StopSTT();
            stopForeground(true);
            stopSelf();
        }
    }

    /**
     * 메인 엑티비티가 아닌 곳에서 엑티비티의 뷰나 레이아웃을 변경하려면 Handler를 통해 접근해야한다.
     * 따라서, 리스트뷰의 내용을 refresh 하려면 다음과 같은 작업이 필요하다.
     * refreshLsitView()
     * handleMessage(Message msg)
     * mHandler
     */
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
        mReceiver.onInit(getApplicationContext());
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
        mReceiver.onStop(getApplicationContext());
        //mCaller.onStop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
