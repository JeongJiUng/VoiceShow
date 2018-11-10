package com.example.workbench.VoiceShow.MainFragment.Address;

import android.graphics.drawable.Drawable;

public class AddressListViewItem {

    private Drawable iconDrawable;
    private String name;
    private String number;
    private Drawable addressCalling;
    private Drawable addressChatting;

    public void setIcon(Drawable icon){
        iconDrawable = icon;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setNumber(String number){
        this.number = number;
    }
    public void setAddressCalling(Drawable addressCalling){
        this.addressCalling = addressCalling;
    }public void setAddressChatting(Drawable addressChatting){
        this.addressChatting = addressChatting;
    }

    public Drawable getIcon(){
        return this.iconDrawable;
    }
    public String getName(){
        return this.name;
    }
    public String getNumber(){
        return this.number;
    }
    public Drawable getAddressCalling(){
        return this.addressCalling;
    }
    public Drawable getAddressChatting(){
        return this.addressChatting;
    }
}
