package com.example.workbench.VoiceShow.MainFragment.Chatting;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.workbench.VoiceShow.ChattingRoom;
import com.example.workbench.VoiceShow.R;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class ChattingFragment extends ListFragment {

    ChattingAdapter adapter;

    private ArrayList<String> chattingRoomName;
    private ArrayList<String> chattingRoomInfo;

    public ChattingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(),ChattingRoom.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        adapter = new ChattingAdapter();
        setListAdapter(adapter);
        //채팅창이 잘 나오는지 확인 추후 데이터를 이곳에 받아서 출력 및 넘겨주도록 한다.
        chattingRoomName = new ArrayList();
        chattingRoomName.add("first");

        chattingRoomInfo = new ArrayList();
        chattingRoomInfo.add("first");

        //뷰를 만들어 준다.
//        View view = inflater.inflate(R.layout.fragment_chatting,container,false);


        // 아이템 추가
        for(int i = 0; i<chattingRoomName.size();i++){
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.profileimg),
                    chattingRoomName.get(i),
                    chattingRoomInfo.get(i),
                    ContextCompat.getDrawable(getActivity(), R.drawable.chatting_blue_img));
        }

        return super.onCreateView(inflater, container,savedInstanceState);
    }
}
