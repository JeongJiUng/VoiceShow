package com.example.workbench.VoiceShow;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.workbench.VoiceShow.MainFragment.AddressFragment;
import com.example.workbench.VoiceShow.MainFragment.CallingFragment;
import com.example.workbench.VoiceShow.MainFragment.ChattingFragment;
import com.example.workbench.VoiceShow.MainFragment.RecentFragment;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mainPage; //메인페이지의 프레그먼트 저장

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);

        //메인페이지 리스트에 메인 페이지들을 넣었다.
        mainPage = new ArrayList<>();
        mainPage.add(new CallingFragment());
        mainPage.add(new AddressFragment());
        mainPage.add(new RecentFragment());
        mainPage.add(new ChattingFragment());
    }

    @Override // 메인페이지의 포지션을 꺼낸다.
    public Fragment getItem(int position) { return mainPage.get(position);
    }

    @Override // 메인페이지의 총 크기
    public int getCount() {
        return mainPage.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //포지션별로 탭에 나오는 글자 표현
        switch(position){
            case 0:
                return "전화";
            case 1:
                return "주소록";
            case 2:
                return "최근기록";
            case 3:
                return "음성 채팅";
            default:
                return "";

        }
    }
}
