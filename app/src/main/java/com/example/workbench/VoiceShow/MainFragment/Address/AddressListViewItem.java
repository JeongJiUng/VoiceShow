package com.example.workbench.VoiceShow.MainFragment.Address;

import android.graphics.drawable.Drawable;

public class AddressListViewItem {

    private Drawable iconDrawable;
    private String name;
    private String number;

    public void setIcon(Drawable icon){
        iconDrawable = icon;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setNumber(String number){
        this.number = number;
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
}
