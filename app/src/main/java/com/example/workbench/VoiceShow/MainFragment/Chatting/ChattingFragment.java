package com.example.workbench.VoiceShow.MainFragment.Chatting;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.workbench.VoiceShow.ChattingRoom;
import com.example.workbench.VoiceShow.MainActivity;
import com.example.workbench.VoiceShow.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChattingFragment extends ListFragment {

    private boolean         isLongable;
    private int             mItemPos;
    private ChattingAdapter adapter;
    private ArrayList<String> chattingID;
    private ArrayList<ChattingListData> chattingListData;

    public ChattingFragment() {
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (isLongable == false)
        {
            //Toast.makeText(getContext(), chattingListData.get(position).chattingName, Toast.LENGTH_SHORT).show();
            Intent          intent = new Intent(getActivity(), ChattingRoom.class);
            intent.putExtra("ID", chattingListData.get(position).chattingID);
            intent.putExtra("NAME", chattingListData.get(position).chattingName);
            intent.putExtra("NTIME", chattingListData.get(position).chattingTime);
            startActivity(intent);
        }
        isLongable          = false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        isLongable          = false;

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                isLongable  = true;
                mItemPos    = position;

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("대화 기록을 지우시겠습니까?").setPositiveButton("네", mDialogClickListener).setNegativeButton("아니요", mDialogClickListener).show();

                return false;
            }
        });
    }

    DialogInterface.OnClickListener mDialogClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch(which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    String      key = chattingListData.get(mItemPos).chattingID;
                    String      callerText_key = "Key_"+key+"_CallerText";
                    String      recvText_key = "Key_"+key+"_ReceiveText";
                    String      info_key = "Key_"+key;

                    // chatingID에 있는 key값을 지우는 부분. 원하는 key를 지우면 다시 쉐어드프리퍼런스를 통해 Key_ID_LIST 저장
                    for (int i = 0; i < chattingID.size(); i++)
                        if (chattingID.get(i) == key)
                            chattingID.remove(i);

                    SharedPreferences           key_id_list = getContext().getSharedPreferences("PREF_CHAT_ID_LIST", Context.MODE_PRIVATE);
                    SharedPreferences.Editor    editor = key_id_list.edit();
                    Set<String>                 list = new LinkedHashSet<String>(chattingID);
                    editor.clear();
                    editor.putStringSet("Key_ID_LIST", list);
                    editor.commit();

                    adapter.removeItem(mItemPos);
                    refreshListView();

                    SharedPreferences           chat_list = getContext().getSharedPreferences("PREF_CHAT_LIST", Context.MODE_PRIVATE);
                    editor                      = chat_list.edit();
                    editor.remove(callerText_key);
                    editor.remove(recvText_key);
                    editor.remove(info_key);
                    editor.commit();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

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
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.icon_chatting),
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

    // ' - ' 이 없는 번호들중 이름이 있는걸 찾는 함수이다.
    public String findOtherName(String str){
        ArrayList<String> arrayList = new ArrayList<>();
        String[] array = str.split("-");
        String name = "";
        int i = 0;
        while(i<array.length){
            name = name + array[i];
            i++;
        }
        return name;
    }

    public void findName(){
        ArrayList<String> addressNumber = ((MainActivity)getActivity()).getNumbers(); // '-' 있는거
        ArrayList<String> addressName = ((MainActivity)getActivity()).getNames();

        //번호 같은게 있으면 저장된 번호가 이름으로 바뀌어 나온다.
        for(int i=0;i<chattingListData.size();i++){
            for(int j=0;j<addressName.size();j++){
                String otherName = findOtherName(addressNumber.get(i));
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

    final Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
        }
    };

    private void refreshListView()
    {
        Message         msg = mHandler.obtainMessage();
        mHandler.sendMessage(msg);
    }
}
