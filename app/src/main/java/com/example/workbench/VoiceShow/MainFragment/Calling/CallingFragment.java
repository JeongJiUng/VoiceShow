package com.example.workbench.VoiceShow.MainFragment.Calling;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workbench.VoiceShow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallingFragment extends Fragment{

    public CallingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calling, container, false);
    }
}
