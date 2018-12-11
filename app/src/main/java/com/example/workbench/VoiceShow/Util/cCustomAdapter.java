package com.example.workbench.VoiceShow.Util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
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
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class cCustomAdapter extends BaseAdapter
{
    public class cListContents
    {
        String              mMsg;
        int                 mType;
        Long                mDate;

        cListContents(String _msg, int _type, Long _date)
        {
            this.mMsg       = _msg;
            this.mType      = _type;
            this.mDate      = _date;
        }
    }

    private class cCustomHolder
    {
        TextView            mTextView;
        LinearLayout        mLayout;
        View                mViewRight;
        View                mViewLeft;
    }

    private ArrayList<cListContents>    mList;

    public cCustomAdapter()
    {
        mList               = new ArrayList<cListContents>();
    }

    public cCustomAdapter getCustomAdapter()
    {
        return this;
    }

    public ArrayList<cListContents> getList()
    {
        return mList;
    }

    public void addItem(String _msg, int _type, long _date)
    {
        mList.add(new cListContents(_msg, _type, _date));
    }

    public void removeItem(int _pos)
    {
        mList.remove(_pos);
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public cListContents getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @BindView(R.id.TEXTVIEW)
    TextView            mText;

    @BindView(R.id.LAYOUT_CHAT)
    LinearLayout        mLayout;

    @BindView(R.id.VIEW_LEFT)
    View                mViewLeft;

    @BindView(R.id.VIEW_RIGHT)
    View                mViewRight;

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final int           pos = position;
        final Context       context = parent.getContext();

        cCustomHolder       holder = null;

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if (convertView == null)
        {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater  inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView     = inflater.inflate(R.layout.overlay_chatitem, parent, false);

            ButterKnife.bind(this, convertView);

            // 홀더 생성 및 Tag로 등록
            holder          = new cCustomHolder();
            holder.mTextView= mText;
            holder.mLayout  = mLayout;
            holder.mViewRight   = mViewRight;
            holder.mViewLeft    = mViewLeft;
            convertView.setTag(holder);
        }
        else
        {
            holder          = (cCustomHolder)convertView.getTag();
            mText           = holder.mTextView;
            mLayout         = holder.mLayout;
            mViewRight      = holder.mViewRight;
            mViewLeft       = holder.mViewLeft;
        }

        // Text 등록
        mText.setText(mList.get(position).mMsg);
        // Text 최대 길이 조정
        mText.setMaxWidth(800);

        if (mList.get(position).mType == 0)
        {
            mText.setBackgroundResource(R.drawable.purple_box1);
            mLayout.setGravity(Gravity.LEFT);
            mViewRight.setVisibility(View.GONE);
            mViewLeft.setVisibility(View.GONE);
        }
        else if (mList.get(position).mType == 1)
        {
            mText.setBackgroundResource(R.drawable.blue_box1);
            mLayout.setGravity(Gravity.RIGHT);
            mViewRight.setVisibility(View.GONE);
            mViewLeft.setVisibility(View.GONE);
        }
        else if (mList.get(position).mType == 2)
        {
            mText.setBackgroundResource(R.drawable.datebg);
            mLayout.setGravity(Gravity.CENTER);
            mViewRight.setVisibility(View.VISIBLE);
            mViewLeft.setVisibility(View.VISIBLE);
        }

//        // 리스트 아이템을 터치 했을 때 이벤트 발생
//        convertView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                // 터치 시 해당 아이템 이름 출력
//                Toast.makeText(context, "리스트 클릭 : "+mList.get(pos), Toast.LENGTH_SHORT).show();
//            }
//        });

        // 리스트 아이템을 길게 터치 했을때 이벤트 발생
        convertView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // 길게 터치시 해당 메세지 클립보드에 저장
                String chattingCopyData = mList.get(pos).mMsg;
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
}
