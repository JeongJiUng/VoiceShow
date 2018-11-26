package com.example.workbench.VoiceShow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ChattingRoom extends AppCompatActivity {

    ListView chattingRoomView;
    ChattingRoomAdapter chattingRoomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);

        //커스텀 어댑터 생성
        chattingRoomAdapter = new ChattingRoomAdapter();

        //Xml에서 추가한 ListView 연결
        chattingRoomView = findViewById(R.id.listView22);

        //ListView에 어댑터 연결
        chattingRoomView.setAdapter(chattingRoomAdapter);

        chattingRoomAdapter.add("이건 뭐지",1);
        chattingRoomAdapter.add("쿨쿨",1);
        chattingRoomAdapter.add("쿨쿨쿨쿨",0);

    }
}
