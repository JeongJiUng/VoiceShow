package com.example.workbench.VoiceShow.MainFragment.Chatting;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.workbench.VoiceShow.ChattingRoom;
import com.example.workbench.VoiceShow.MainActivity;
import com.example.workbench.VoiceShow.R;

import java.util.ArrayList;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChattingFragment extends ListFragment {

    ChattingAdapter adapter;

    private ArrayList<String> chattingRoomName;
    private ArrayList<String> chattingRoomInfo;
    private ArrayList<String> chattingID;

    public ChattingFragment() {
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

        chattingID = ((MainActivity)getActivity()).getChattingDataName();
        chattingRoomName =  new ArrayList<>();
        chattingRoomInfo = new ArrayList();

        for(int i=0;i<chattingID.size();i++){
            ArrayList<String> temp = divideLetter(chattingID.get(i));

            chattingRoomName.add(temp.get(0));
            chattingRoomInfo.add(temp.get(1));
        }

        //뷰를 만들어 준다.
//        View view = inflater.inflate(R.layout.fragment_chatting,container,false);


        // 아이템 추가
        for(int i = 0; i<chattingRoomInfo.size();i++){
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.profileimg),
                    chattingRoomName.get(i),
                    chattingRoomInfo.get(i),
                    ContextCompat.getDrawable(getActivity(), R.drawable.chatting_blue_img));
        }

        return super.onCreateView(inflater, container,savedInstanceState);
    }

    public ArrayList divideLetter(String str){
        ArrayList<String> arrayList = new ArrayList<>();

        String[] array = str.split("#");
        arrayList.add(array[0]);
        arrayList.add(array[1]);

        return arrayList;
    }

}
