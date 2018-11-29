package com.example.workbench.VoiceShow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChattingRoom extends AppCompatActivity {

    ListView chattingRoomView;
    ChattingRoomAdapter chattingRoomAdapter;
    String chattingID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<String> callerData;
        ArrayList<String> receiverData;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);

        //ChattingFragment에서의 ID값을 넘겨 받는다.
        Intent intent = getIntent();
        chattingID = intent.getExtras().getString("ID");

        //커스텀 어댑터 생성
        chattingRoomAdapter = new ChattingRoomAdapter();

        //Xml에서 추가한 ListView 연결
        chattingRoomView = findViewById(R.id.listView22);

        //ListView에 어댑터 연결
        chattingRoomView.setAdapter(chattingRoomAdapter);

        //채팅 내용
        //아직 미완성 (정렬이 되어있지 않다.)
        callerData = getChattingCallerData();
        receiverData = getChattingReceiveTextData();

        chattingRoomAdapter.add(chattingID,1);
        chattingRoomAdapter.add(chattingID,0);
        chattingRoomAdapter.add("쿨쿨쿨쿨",0);
        chattingRoomAdapter.add("콜콜콜콜",1);

        for(int i=0;i<callerData.size();i++){
            if(receiverData.size()>0) {
                chattingRoomAdapter.add(callerData.get(i), 1);
                chattingRoomAdapter.add(receiverData.get(i), 0);
            }
        }
    }
    public ArrayList getChattingCallerData(){
        ArrayList<String> chattingCaller = new ArrayList<>();
        Set<String> chattingData;
        SharedPreferences sharedChattingData = getSharedPreferences("PREF_CHAT_LIST",MODE_PRIVATE);

        chattingData = (sharedChattingData.getStringSet("Key_"+chattingID+"_CallerText", new HashSet<String>()));

        Iterator<String> itr = chattingData.iterator();

        while(itr.hasNext()){
            chattingCaller.add(itr.next());
        }

        return chattingCaller;
    }
    public ArrayList getChattingReceiveTextData(){
        ArrayList<String> chattingReceiver = new ArrayList<>();
        Set<String> chattingData;
        SharedPreferences sharedChattingData = getSharedPreferences("PREF_CHAT_LIST",MODE_PRIVATE);

        chattingData = (sharedChattingData.getStringSet("Key_"+chattingID+"_ReceiveText", new HashSet<String>()));

        Iterator<String> itr = chattingData.iterator();

        while(itr.hasNext()){
            chattingReceiver.add(itr.next());
        }

        return chattingReceiver;
    }
}
