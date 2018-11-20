package com.example.workbench.VoiceShow.MainFragment.Chatting;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workbench.VoiceShow.MainFragment.Address.AddressListViewItem;
import com.example.workbench.VoiceShow.R;

import java.util.ArrayList;


public class ChattingAdapter extends BaseAdapter {

    //Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ChattingListViewItem> chattingListViewItems = new ArrayList<>();

    public ChattingAdapter(){
    }

    @Override
    public int getCount() {
        return chattingListViewItems.size();
    }

    //지정된(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴.
    @Override
    public Object getItem(int position) {
        return chattingListViewItems.get(position);
    }

    //지정된 위치에 있는 데이터 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(Drawable icon, String name, String Info, Drawable goToChattingRoom) {
        ChattingListViewItem item = new ChattingListViewItem();

        item.setIcon(icon);
        item.setName(name);
        item.setInfo(Info);
        item.setGoToChattingRoom(goToChattingRoom);

        chattingListViewItems.add(item);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "fragment_chatting"의 Layout을 inflate하여 convertView 참조 획득?
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_chatting,parent,false);
        }

        // 화면에 표시될 View(Layout이 inflate 된)으로부터 위젯에 대한 참조 획득
        ImageView chattingProfileView = convertView.findViewById(R.id.chattingProfile);
        TextView chattingNameView = convertView.findViewById(R.id.chattingName);
        TextView chattingInfoView = convertView.findViewById(R.id.chattingInfo);
        ImageView goToChattingImageView = convertView.findViewById(R.id.goToChattingRoom);

        //Data Set(ChattingListViewItem)에서 position에 위치한 데이터 참조 획득
        ChattingListViewItem listViewItem = chattingListViewItems.get(position);

        //아이템 내 각 위젯에 데이터 반영
        chattingProfileView.setImageDrawable(listViewItem.getIcon());
        chattingNameView.setText(listViewItem.getName());
        chattingInfoView.setText(listViewItem.getInfo());
        goToChattingImageView.setImageDrawable(listViewItem.getGoToChattingRoom());

        chattingProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, pos + " 번째 이미지 선택", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
