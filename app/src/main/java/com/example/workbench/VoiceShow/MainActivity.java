package com.example.workbench.VoiceShow;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.workbench.VoiceShow.R.id.BACK_SPACE;

public class MainActivity extends AppCompatActivity
{
    //private String          mPhoneNumber;                           // 핸드폰 번호 문자열

    //전화번호부 가져오기위한 리스트
    private ArrayList<String> nameList;
    private ArrayList<String> numberList;

    //전화 상태 및 전화 상태 변화에대한 리스너 및 관련 객체
    TelephonyManager        mTelManager;    // 안드로이드 폰의 전화 서비스에 대한 정보에 접근하기 위한 객체
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
                    cSystemManager.getInstance().GetSTTModule().onStop();
                    Log.i("Telephony", "STATE_IDLE");
                    break;

                case TelephonyManager.CALL_STATE_RINGING:
                    Log.i("Telephony", "STATE_RINGING");
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    cSystemManager.getInstance().GetSTTModule().onStart();
                    Log.i("Telephony", "STATE_OFFHOOK");
                    break;
            }
            super.onCallStateChanged(_state, _incomingNumber);
        }
    };

    /**
     * 어플리케이션 최초 실행 여부 확인.
     */
    private void CheckFirstTime()
    {
        SharedPreferences   pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
        boolean             first = pref.getBoolean("isFirst", false);

        if (first == false)
        {
            Log.d("Is first Time?", "first");
            SharedPreferences.Editor    editor = pref.edit();
            editor.putBoolean("isFirst", true);
            editor.commit();
            // 앱 최초 실행시 수행할 작업
        }
        else
        {
            Log.d("Is first Time?", "not first");
        }
    }

    private void Initialize()
    {
        // 변수 초기화
        //mPhoneNumber        = "";

        mTelManager         = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        mTelManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        // 기능 초기화
        CheckFirstTime();

        cSystemManager.getInstance().Initialize(this, getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(com.example.workbench.VoiceShow.R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tl_tabs);// 텝 레이아웃 을 찾아준다.
        ViewPager viewPager = findViewById(R.id.vp_pager); //뷰 페이져
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        Initialize();
        getAddressBooks(); // 전화번호부 가져오기.
       // startActivity(new Intent("android.intent.action.DIAL"));
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

//    @Override
//    public void onClick(View v)
//    {
//        // activity_main 에서 발생되는 버튼 이벤트 처리.
//        // 핸드폰 번호를 보여 줄 텍스트뷰 아이디
//        TextView        tv_PhoneNum = (TextView)findViewById(R.id.TEXT_PHONE_NUM);
//
//        switch(v.getId())
//        {
//            case R.id.BACK_SPACE:
//                // 핸드폰번호 뒤에서 하나씩 지움.
//                //int         len = mPhoneNumber.length();
//                //mPhoneNumber.substring()
//                Toast.makeText(this, "BACK", Toast.LENGTH_SHORT).show();
//                cSystemManager.getInstance().GetSTTModule().onStart();
//                break;
//
//            case R.id.ADD_PHONE_NUM:
//                Toast.makeText(this, "NUM", Toast.LENGTH_SHORT).show();
//                cSystemManager.getInstance().GetSTTModule().onStop();
//                break;
//
//            case R.id.KEYPAD_0:
//                Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
//                mPhoneNumber    += "0";
//                break;
//            case R.id.KEYPAD_1:
//                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
//                mPhoneNumber    += "1";
//                break;
//            case R.id.KEYPAD_2:
//                mPhoneNumber    += "2";
//                break;
//            case R.id.KEYPAD_3:
//                mPhoneNumber    += "3";
//                break;
//            case R.id.KEYPAD_4:
//                mPhoneNumber    += "4";
//                break;
//            case R.id.KEYPAD_5:
//                mPhoneNumber    += "5";
//                break;
//            case R.id.KEYPAD_6:
//                mPhoneNumber    += "6";
//                break;
//            case R.id.KEYPAD_7:
//                mPhoneNumber    += "7";
//                break;
//            case R.id.KEYPAD_8:
//                mPhoneNumber    += "8";
//                break;
//            case R.id.KEYPAD_9:
//                mPhoneNumber    += "9";
//                break;
//
////            case R.id.KEYPAD_STAR:
////                mPhoneNumber    += "*";
////                break;
//            case R.id.KEYPAD_SHAP:
//                mPhoneNumber    += "#";
//                break;
//
////            case R.id.KEYPAD_VCALL:
////                // 영상 통화
////                break;
//            case R.id.KEYPAD_CALL:
//                // 음성 통화
//                String      tel = "tel:" + mPhoneNumber;
//                startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
//
//                // 통화 버튼을 누르면 전화번호와 텍스트뷰 초기화
//                mPhoneNumber    = "";
//                tv_PhoneNum.setText(mPhoneNumber);
//                break;
////            case R.id.KEYPAD_HIDE:
////                // 키 패드 활성/비활성
////                break;
//        }
//
////        // 영상/음성 통화, 키패드 활성/비활성 버튼이 아닌 경우 텍스트뷰에 핸드폰 번호 갱신.
////        if (v.getId() != R.id.KEYPAD_CALL && v.getId() != R.id.KEYPAD_VCALL && v.getId() != R.id.KEYPAD_HIDE)
//        tv_PhoneNum.setText(mPhoneNumber);
//    }

    public void MoveToSettings(View v)
    {
        startActivity(new Intent(MainActivity.this,activity_SETTINGS.class));
    }

    public void getAddressBooks ()
    {
        //주소록 가져오는 부분
        nameList = new ArrayList();
        numberList = new ArrayList();

        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null,null,null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");

        //전화기록을 가져오기위한 커서
//        Cursor call = getContentResolver().query(Uri.parse("content://call_log/calls"),
//                null,null,null,
//                ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP + " asc");


        while(c.moveToNext()){
            //연락처 id 값
            String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID)); // 아이디 가져온다.
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)); //이름을 가져온다.
//            String call_ID = call.getString(call.getColumnIndex(android.provider.CallLog.Calls._ID));//콜 인덱스 아이디 가져온다.
//            String call_Number = call.getString(call.getColumnIndex(CallLog.Calls.NUMBER)); //콜 아이디
//            String call_Date = call.getString(call.getColumnIndex(android.provider.CallLog.Calls.DATE)); // 콜 날짜
//            String call_Type = call.getString(call.getColumnIndex(android.provider.CallLog.Calls.NEW)); // 콜 타입
            nameList.add(name);

            Cursor phoneCursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                    null,null);

            if(phoneCursor.moveToNext()){
                String number = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                numberList.add(number);
            }
            phoneCursor.close();
        }
        c.close();

    }

    // 주소록을 보내주는 함수.
    public  ArrayList getNames(){
        return this.nameList;
    }
    public ArrayList getNumbers(){
        return this.numberList;
    }


}
