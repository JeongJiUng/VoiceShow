package com.example.workbench.VoiceShow.Util;
//기존 핸드폰의 주소록을 가져오는 클래스


import android.database.Cursor;
import java.util.ArrayList;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;


public class getAddressBook extends AppCompatActivity {

    private ArrayList<String> nameList;
    private ArrayList<String> numberList;


    public getAddressBook (){
        nameList = new ArrayList<String>();
        numberList = new ArrayList<String>();

        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null,null,null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");

        while(c.moveToNext()){
            //연락처 id 값
            String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID)); // 아이디 가져온다.
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)); //이름을 가져온다.
            nameList.add(name);

            Cursor phoneCursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "id",
                    null,null);

            if(phoneCursor.moveToNext()){
                String number = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                numberList.add(number);
            }
            phoneCursor.close();
        }
        c.close();
    }

    public  ArrayList getName(){
        return nameList;
    }

    public ArrayList getNumber(){
        return numberList;
    }
}
