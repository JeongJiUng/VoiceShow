package com.example.workbench.VoiceShow.MainFragment.Chatting;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.workbench.VoiceShow.MainFragment.Address.AddressListViewItem;
import com.example.workbench.VoiceShow.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ChattingAdapter extends BaseAdapter {

    private ArrayList<ChattingListViewItem> chattingListViewItems = new ArrayList<>();

    public ChattingAdapter(){
    }

    @Override
    public int getCount() {
        return chattingListViewItems.size();
    }

    @Override
    public Object getItem(int position) {
        return chattingListViewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem( String name, String number) {
        ChattingListViewItem item = new ChattingListViewItem();
        item.setName(name);
        item.setNumber(number);

        chattingListViewItems.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);convertView = inflater.inflate(R.layout.fragment_address,parent,false);
            convertView = inflater.inflate(R.layout.fragment_chatting,parent,false);
        }

        TextView nameView = convertView.findViewById(R.id.addressName);
        TextView numberView = convertView.findViewById(R.id.addressNum);

        //Data Set(AddresslistViewItem)에서 position에 위치한 데이터 참조 획득
        ChattingListViewItem listViewItem = chattingListViewItems.get(position);

        nameView.setText(listViewItem.getName());
        numberView.setText(listViewItem.getNumber());

        return convertView;
    }
}
