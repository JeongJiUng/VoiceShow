package com.example.workbench.VoiceShow.MainFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.workbench.VoiceShow.R;
import com.example.workbench.VoiceShow.Util.getAddressBook;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends ListFragment{

    AddressListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Adapter 생성 및 Adapter 지정
        adapter = new AddressListViewAdapter();
        setListAdapter(adapter);//어뎁터 연결

        getAddressBook addressBook = new getAddressBook();



        //첫번째 아이템 추가
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.profileimg),
                getString(addressBook.getSize()), "010-2612-1370");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.profileimg),
                "Circle", "000-0000-0000") ;
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.profileimg),
                "Ind", "000-0000-0000") ;




        //return inflater.inflate(R.layout.fragment_address, container, false);
        return super.onCreateView(inflater, container,savedInstanceState);
    }
}
