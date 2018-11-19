package com.example.workbench.VoiceShow.MainFragment.Chatting;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.workbench.VoiceShow.ChattingRoom;
import com.example.workbench.VoiceShow.R;

import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChattingFragment extends Fragment {
    public ChattingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chatting,container,false);

        //채팅방이 잘 작동하는지 확인 하는 버튼
        Button button = (Button) view.findViewById(R.id.chattingButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ChattingRoom.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
