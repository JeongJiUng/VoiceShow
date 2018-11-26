package com.example.workbench.VoiceShow.MainFragment.Calling;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workbench.VoiceShow.R;
import com.example.workbench.VoiceShow.cSystemManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallingFragment extends Fragment implements View.OnClickListener {

    private String          mPhoneNumber;                           // 핸드폰 번호 문자열


    public CallingFragment() {
        // Required empty public constructor
        mPhoneNumber ="";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calling, container, false);
    }

    @Override
    public void onClick(View v) {
        // activity_main 에서 발생되는 버튼 이벤트 처리.
        // 핸드폰 번호를 보여 줄 텍스트뷰 아이디
        TextView tv_PhoneNum = (TextView)getActivity().findViewById(R.id.TEXT_PHONE_NUM);

        switch(v.getId())
        {
            case R.id.BACK_SPACE:
                // 핸드폰번호 뒤에서 하나씩 지움.
                //int         len = mPhoneNumber.length();
                //mPhoneNumber.substring()
                Toast.makeText(getContext(), "BACK", Toast.LENGTH_SHORT).show();
                cSystemManager.getInstance().GetSTTModule().onStart();
                break;

            case R.id.ADD_PHONE_NUM:
                Toast.makeText(getContext(), "NUM", Toast.LENGTH_SHORT).show();
                cSystemManager.getInstance().GetSTTModule().onStop();
                break;

            case R.id.KEYPAD_0:
                Toast.makeText(getContext(), "0", Toast.LENGTH_SHORT).show();
                mPhoneNumber    += "0";
                break;
            case R.id.KEYPAD_1:
                Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
                mPhoneNumber    += "1";
                break;
            case R.id.KEYPAD_2:
                mPhoneNumber    += "2";
                break;
            case R.id.KEYPAD_3:
                mPhoneNumber    += "3";
                break;
            case R.id.KEYPAD_4:
                mPhoneNumber    += "4";
                break;
            case R.id.KEYPAD_5:
                mPhoneNumber    += "5";
                break;
            case R.id.KEYPAD_6:
                mPhoneNumber    += "6";
                break;
            case R.id.KEYPAD_7:
                mPhoneNumber    += "7";
                break;
            case R.id.KEYPAD_8:
                mPhoneNumber    += "8";
                break;
            case R.id.KEYPAD_9:
                mPhoneNumber    += "9";
                break;

//            case R.id.KEYPAD_STAR:
//                mPhoneNumber    += "*";
//                break;
            case R.id.KEYPAD_SHAP:
                mPhoneNumber    += "#";
                break;

//            case R.id.KEYPAD_VCALL:
//                // 영상 통화
//                break;
            case R.id.KEYPAD_CALL:
                // 음성 통화
                String      tel = "tel:" + mPhoneNumber;
                startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));

                // 통화 버튼을 누르면 전화번호와 텍스트뷰 초기화
                mPhoneNumber    = "";
                tv_PhoneNum.setText(mPhoneNumber);
                break;
//            case R.id.KEYPAD_HIDE:
//                // 키 패드 활성/비활성
//                break;
        }

//        // 영상/음성 통화, 키패드 활성/비활성 버튼이 아닌 경우 텍스트뷰에 핸드폰 번호 갱신.
//        if (v.getId() != R.id.KEYPAD_CALL && v.getId() != R.id.KEYPAD_VCALL && v.getId() != R.id.KEYPAD_HIDE)
        tv_PhoneNum.setText(mPhoneNumber);
    }
}
