package com.example.workbench.VoiceShow.MainFragment.Chatting;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.workbench.VoiceShow.R;

import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChattingFragment extends Fragment {

    Vector<ContactsContract.Profile> profiles;

    public ChattingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_chatting, container, false);
    }

}
