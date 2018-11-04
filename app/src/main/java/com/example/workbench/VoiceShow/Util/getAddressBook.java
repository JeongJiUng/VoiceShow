package com.example.workbench.VoiceShow.Util;
//기존 핸드폰의 주소록을 가져오는 클래스

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.database.Cursor;
import java.util.ArrayList;

import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class getAddressBook extends AppCompatActivity {

    ArrayList nameList;
    ArrayList numberList;


    public getAddressBook (){
        checkPermission();
        nameList = new ArrayList();
        numberList = new ArrayList();
        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null,null,null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");

        int i = 0;
        while(c.moveToNext()){
            //연락처 id 값

            String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID)); // 아이디 가져온다.
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)); //이름을 가져온다.
            nameList.add(i,name);

            Cursor phoneCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "id",
                    null,null);

            if(phoneCursor.moveToNext()){
                String number = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                numberList.add(i,number);
            }
            phoneCursor.close();
            i++;
        }
        c.close();
    }

    public  ArrayList getName(){
        return nameList;
    }

    public ArrayList getNumber(){
        return numberList;
    }

    public int getSize(){
        return nameList.size();
    }
    // 0. 요청코드 세팅
    private final int REQ_CODE = 100;

    // 1. 권한체크 (런타임 권한은 마시멜로우 이상 버전에서만 사용가능)
    @TargetApi(Build.VERSION_CODES.M) // Target 지정 애너테이션
    private void checkPermission(){
        // 1.1 런타임 권한체크
        if( checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
                ){
            // 1.2 요청할 권한 목록 작성
            String permArr[] = {Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.CALL_PHONE
                    , Manifest.permission.READ_CONTACTS};
            // 1.3 시스템에 권한요청
            requestPermissions(permArr, REQ_CODE);
        }else{
            return;
        }
    }

    // 2. 권한체크 후 콜백되는 함수 < 사용자가 확인후 시스템이 호출하는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_CODE){
            // 2.1 배열에 넘긴 런타임권한을 체크해서 승인이 됬으면
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED ){
                // 2.2 프로그램 실행
                return;
            }else{
                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할 수 없습니다.", Toast.LENGTH_LONG).show();
                // 선택 : 1 종료, 2 권한체크 다시 물어보기
                finish();
            }
        }
    }
}
