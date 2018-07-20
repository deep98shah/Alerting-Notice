package com.project.android.takshashilaacollege2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;


/**
 * Created by HP on 24-05-2018.
 */

public class NoticeAdapter extends ArrayAdapter {

    Context context;
    Activity activity;
    Notice notice;
    NoticeHolder noticeHolder;
    List list = new ArrayList();
    public NoticeAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public void add(Notice object) {
        super.add(object);
        list.add(object);
    }

    public void reverseList()
    {
        Collections.reverse(list);
    }

    public void clearList()
    {
        list.clear();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        context = getContext();
        View row;
        row = convertView;
        //NoticeHolder noticeHolder;
        if(row==null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            noticeHolder = new NoticeHolder();
            noticeHolder.textViewSubject = (TextView) row.findViewById(R.id.noticeSubject);
            noticeHolder.textViewDescription = (TextView) row.findViewById(R.id.noticeDescription);
            row.setTag(noticeHolder);
        }
        else
        {
            noticeHolder = (NoticeHolder) row.getTag();
        }

        notice = (Notice) getItem(position);
        noticeHolder.textViewSubject.setText(notice.getSubject());
        noticeHolder.textViewDescription.setText(notice.getDescription());
        Log.v("Notice.getID():",String.valueOf(notice.getId()));
        functionSP();
        final String subject = notice.getSubject();
        final String desc = notice.getDescription();
        noticeHolder.linearLayoutNotice = (LinearLayout)row.findViewById(R.id.linearLayoutNotice);
        noticeHolder.linearLayoutNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeHolder.textViewSubject.setTypeface(null,Typeface.NORMAL);
                Intent intent = new Intent(context,DisplayNotice.class);
                intent.putExtra("notice_subject",subject);
                intent.putExtra("notice_desc",desc);
                context.startActivity(intent);
            }
        });
        return row;
    }

    private void functionSP()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.user_pref,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.v("key_notice",String.valueOf(sharedPreferences.getInt(MainActivity.key_notice,0)));
        if(sharedPreferences.getInt(MainActivity.key_notice,0)<notice.getId())
        {
            noticeHolder.textViewSubject.setTypeface(null,Typeface.BOLD);
            editor.putInt(MainActivity.key_notice,notice.getId());
        }
        editor.commit();
        Log.v("key_notice",String.valueOf(sharedPreferences.getInt(MainActivity.key_notice,0)));
    }
    private static class NoticeHolder{
        TextView textViewSubject, textViewDescription;
        LinearLayout linearLayoutNotice;
    }
}

