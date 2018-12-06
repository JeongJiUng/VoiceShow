package com.example.workbench.VoiceShow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.protobuf.StringValue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChattingRoom extends AppCompatActivity {

    private ListView chattingRoomView;
    private ChattingRoomAdapter chattingRoomAdapter;
    private String chattingID;
    private ArrayList<ChattingData> chattingData;
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
        callerData = getChattingCallerData();
        receiverData = getChattingReceiveTextData();

        //chattingRoomAdapter.add(chattingID,1);
        //chattingRoomAdapter.add(chattingID,0);

        //chattingData 객체생성
        chattingData = new ArrayList();
        divideChatting(callerData, 0);
        divideChatting(receiverData, 1);
        dataSort();
        for(int i=0;i<chattingData.size();i++){
            chattingRoomAdapter.add(chattingData.get(i).msg, chattingData.get(i).who);
        }

    }

    //정렬
    public void dataSort(){
        for(int i=0;i<chattingData.size();i++){
            for(int j=i;j<chattingData.size();j++){
                if(chattingData.get(i).msgTime>chattingData.get(j).msgTime){
                    ChattingData temp = chattingData.get(i);
                    chattingData.set(i,chattingData.get(j));
                    chattingData.set(j,temp);
                }
            }
        }
    }
    public void divideChatting(ArrayList<String> data, int who){

        for(int i=0;i<data.size();i++){
            String[] array = data.get(i).split("@");
            ChattingData temp = new ChattingData(array[0], Long.parseLong(array[1]), who);

            chattingData.add(temp);
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

    public class ChattingData{
        private String msg;
        private Long msgTime;
        private int who;

        public ChattingData (String msg, Long msgTime, int who){
            this.msg = msg;
            this.msgTime = msgTime;
            this.who = who;
        }
    }
}
