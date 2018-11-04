package com.example.workbench.VoiceShow.MainFragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workbench.VoiceShow.R;

import java.util.ArrayList;

public class AddressListViewAdapter extends BaseAdapter{

    //Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<AddressListViewItem> addressListViewItems = new ArrayList<AddressListViewItem>();

    public AddressListViewAdapter(){

    }

    @Override
    public int getCount() {
        return addressListViewItems.size();
    }

    //지정된(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴.
    @Override
    public Object getItem(int position) {
        return addressListViewItems.get(position);
    }

    //지정된 위치에 있는 데이터 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    //아이템 데이터를 추가를 위한 함수.
    public void addItem(Drawable icon, String name, String number) {
        AddressListViewItem item = new AddressListViewItem();

        item.setIcon(icon);
        item.setName(name);
        item.setNumber(number);

        addressListViewItems.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //final int pos = position;
        final Context context = parent.getContext();

        // "fragment_address"의 Layout을 inflate하여 convertView 참조 획득?
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_address,parent,false);
        }

        // 화면에 표시될 View(Layout이 inflate 된)으로부터 위젯에 대한 참조 획득
        ImageView profileImageView = (ImageView) convertView.findViewById(R.id.profileImage);
        TextView nameView = (TextView) convertView.findViewById(R.id.addressName);
        TextView numberView = convertView.findViewById(R.id.addressNum);

        //Data Set(AddresslistViewItem)에서 position에 위치한 데이터 참조 획득
        AddressListViewItem listViewItem = addressListViewItems.get(position);

        //아이템 내 각 위젯에 데이터 반영
        profileImageView.setImageDrawable(listViewItem.getIcon());
        nameView.setText(listViewItem.getName());
        numberView.setText(listViewItem.getNumber());

        return convertView;
    }
}
