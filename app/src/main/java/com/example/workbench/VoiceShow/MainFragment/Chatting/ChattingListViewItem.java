package com.example.workbench.VoiceShow.MainFragment.Chatting;

import android.graphics.drawable.Drawable;

public class ChattingListViewItem {

    private Drawable iconDrawable;
    private String name;
    private String Info;
    private Drawable goToChattingRoom;

    public void setIcon(Drawable icon){
        iconDrawable = icon;
    }
    public void setName(String name){
            this.name = name;
        }
        public void setInfo(String Info){
            this.Info = Info;
        }
        public void setGoToChattingRoom(Drawable goToChattingRoom){
            this.goToChattingRoom = goToChattingRoom;
        }

    public Drawable getIcon(){
        return this.iconDrawable;
    }
    public String getName(){
        return this.name;
    }
    public String getInfo(){
        return this.Info;
    }
    public Drawable getGoToChattingRoom(){
        return this.goToChattingRoom;
    }
}
