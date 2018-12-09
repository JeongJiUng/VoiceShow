package com.example.workbench.VoiceShow;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workbench.VoiceShow.R;

import java.util.ArrayList;


public class ChattingRoomAdapter extends BaseAdapter {

    //컨텐츠의 메세지와 타입
    public class ListContents{
        String msg;
        int type;
        ListContents(String msg, int type){
            this.msg = msg;
            this.type = type;
        }
    }

    private ArrayList<ListContents> chattingList;

    public ChattingRoomAdapter(){
        chattingList = new ArrayList();
    }
    //외부에서 아이템 추가 요청 시 사용
    public void add(String msg, int type){
        ListContents contents = new ListContents(msg,type);
        chattingList.add(contents);
    }
    //외부에서 아이템 삭제 요청 시 사용
    public void remove(int position){
        chattingList.remove(position);
    }

    @Override
    public int getCount() {
        return chattingList.size();
    }

    @Override
    public Object getItem(int position) {
        return chattingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        TextView text = null;
        CustomHolder holder = null;
        LinearLayout layout = null;
        View viewRight = null;
        View viewLeft = null;

        //리스트가 길어지면서 현재 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if (convertView == null){
            //view 가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatting_item,parent,false);

            layout = (LinearLayout) convertView.findViewById(R.id.chattingRoomLayout);
            text = convertView.findViewById(R.id.chattingText);
            viewRight = convertView.findViewById(R.id.imageViewRight);
            viewLeft = convertView.findViewById(R.id.imageViewLeft);

            //홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.textView = text;
            holder.layout = layout;
            holder.viewRight = viewRight;
            holder.viewLeft = viewLeft;
            convertView.setTag(holder);
        }
        else{
            holder = (CustomHolder) convertView.getTag();
            text = holder.textView;
            layout = holder.layout;
            viewRight = holder.viewRight;
            viewLeft = holder.viewLeft;
        }
        //Text등록 (이부분 msg문제 해결)
        text.setText(chattingList.get(pos).msg);
        //최대길이
        text.setMaxWidth(800);
        //줄간격
        //text.setLineSpacing(2,1);

        if(chattingList.get(position).type == 0){
            text.setBackgroundResource(R.drawable.inbox2);
            layout.setGravity(Gravity.LEFT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        }else if(chattingList.get(position).type == 1){
            text.setBackgroundResource(R.drawable.outbox2);
            layout.setGravity(Gravity.RIGHT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        }else if(chattingList.get(position).type == 2){
            text.setBackgroundResource(R.drawable.datebg);
            layout.setGravity(Gravity.CENTER);
            viewRight.setVisibility(View.VISIBLE);
            viewLeft.setVisibility(View.VISIBLE);
        }

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //터치 시 해당 아이템 이름 출력
//                String talk = get
//                Toast.makeText(context,"리스트 클릭 : " +chattingList.get(pos), Toast.LENGTH_SHORT).show();
//            }
//        });
        //길게 눌렀을 경우
        convertView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                // 길게 터치시 해당 메세지 클립보드에 저장
                String chattingCopyData = chattingList.get(pos).msg;
                ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("TouchMsg", chattingCopyData);
                clipboardManager.setPrimaryClip(clipData);

                // 터치 시 해당 아이템 이름 출력
                Toast.makeText(context,"메세지가 클립보드에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return convertView;
    }
    private class CustomHolder{
        TextView textView;
        LinearLayout layout;
        View viewRight;
        View viewLeft;
    }
}
