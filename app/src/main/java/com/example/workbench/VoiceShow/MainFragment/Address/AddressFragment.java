package com.example.workbench.VoiceShow.MainFragment.Address;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.workbench.VoiceShow.MainActivity;
import com.example.workbench.VoiceShow.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends ListFragment {

    AddressListViewAdapter adapter;

    private ArrayList phoneNameList;
    private ArrayList<String> phoneNumberList;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //Toast.makeText(getContext(), position + " 번째 선택", Toast.LENGTH_SHORT).show();
//        Toast toast = new Toast(this.getContext());
//        toast.setView(getLayoutInflater().inflate(R.layout.address_simple_menu,null));
//        toast.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Adapter 생성 및 Adapter 지정
        adapter = new AddressListViewAdapter();
        setListAdapter(adapter);//어뎁터 연결

        phoneNameList = ((MainActivity)getActivity()).getNames();
        phoneNumberList = ((MainActivity)getActivity()).getNumbers();

        // 아이템 추가
        for(int i = 0; i<phoneNumberList.size();i++){
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.profileimg),
                    phoneNameList.get(i).toString(), phoneNumberList.get(i));
        }


        //return inflater.inflate(R.layout.fragment_address, container, false);
        return super.onCreateView(inflater, container,savedInstanceState);
    }
}
