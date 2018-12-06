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
import android.widget.Toast;

import com.example.workbench.VoiceShow.ChattingRoom;
import com.example.workbench.VoiceShow.MainActivity;
import com.example.workbench.VoiceShow.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChattingFragment extends ListFragment {

    private ChattingAdapter adapter;
    private ArrayList<String> chattingID;
    private ArrayList<ChattingListData> chattingListData;

    public ChattingFragment() {
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //Toast.makeText(getContext(), chattingListData.get(position).chattingName, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(),ChattingRoom.class);
        intent.putExtra("ID",chattingListData.get(position).chattingID);
        intent.putExtra("NAME",chattingListData.get(position).chattingName);
        intent.putExtra("NTIME",chattingListData.get(position).chattingTime);
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
        chattingListData = new ArrayList();

        //리스트 만든다.
        for(int i=0;i<chattingID.size();i++){
            ArrayList<String> temp = divideLetter(chattingID.get(i));
            Long time_ = Long.parseLong(temp.get(1));
            ChattingListData temp_ = new ChattingListData(chattingID.get(i), temp.get(0), time_);
            chattingListData.add(temp_);
        }

        //이곳을 그냥 버블정렬로 했는데 다음에는 잘 해서 다른 정렬법으로 해야겠다
        //클래스 만들어서해야한다.
        for(int i=0;i<chattingListData.size();i++){
            for(int j=i;j<chattingListData.size();j++){
                if(chattingListData.get(i).chattingTime < chattingListData.get(j).chattingTime){
                    ChattingListData temp = chattingListData.get(i);
                    chattingListData.set(i,chattingListData.get(j));
                    chattingListData.set(j,temp);
                }
            }
        }

        //번호는 Info가 가지고있고 Name은 바뀐다.
        findName();

        // 아이템 추가
        for(int i = 0; i<chattingListData.size();i++){
            Date date = new Date(chattingListData.get(i).chattingTime);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String getTime = simpleDateFormat.format(date);
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.profileimg_purple),
                    chattingListData.get(i).chattingName,
                    getTime);
        }

        return super.onCreateView(inflater, container,savedInstanceState);
    }

    //전화번호와 시간을 나누어주는 함수이다.
    public ArrayList divideLetter(String str){
        ArrayList<String> arrayList = new ArrayList<>();
        String[] array = str.split("#");
        arrayList.add(array[0]);
        arrayList.add(array[1]);

        return arrayList;
    }

    public void findName(){
        ArrayList<String> addressNumber = ((MainActivity)getActivity()).getNumbers();
        ArrayList<String> addressName = ((MainActivity)getActivity()).getNames();

        //번호 같은게 있으면 저장된 번호가 이름으로 바뀌어 나온다.
        for(int i=0;i<chattingListData.size();i++){
            for(int j=0;j<addressName.size();j++){
                if(chattingListData.get(i).chattingName.equals(addressNumber.get(j))){
                    this.chattingListData.get(i).chattingName = addressName.get(j);
                }
            }
        }
    }
    public class ChattingListData{
        private String chattingID;
        private String chattingName;
        private Long chattingTime;

        public ChattingListData (String chattingID, String chattingName, Long chattingTime){
            this.chattingID = chattingID;
            this.chattingName = chattingName;
            this.chattingTime = chattingTime;

        }
    }
}
