package com.project.android.takshashilaacollege2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Timer;
import java.util.TimerTask;

public class NoticeActivity extends AppCompatActivity {

    Activity activity;
    static String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    NoticeAdapter noticeAdapter;
    String subject,description;
    int id;
    static int count;
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView textViewSubject,textViewDesc;
    static int jsonArrayLength;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        json_string = getIntent().getExtras().getString("json_data");
        textViewSubject = (TextView)findViewById(R.id.noticeSubject);
        textViewDesc = (TextView)findViewById(R.id.noticeDescription);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        listView = (ListView)findViewById(R.id.listView);
        noticeAdapter = new NoticeAdapter(this,R.layout.row_layout);
        listView.setAdapter(noticeAdapter);
        parseJSON();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JSONOnSwipe();
            }
        });
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"ListItem Number "+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,DisplayNotice.class);
                intent.putExtra("notice_subject",listView.findViewById(R));
                intent.putExtra("notice_desc",desc);
                context.startActivity(intent);
            }
        });*/
    }
    public void parseJSON(){
        try {
            noticeAdapter.clearList();
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            count = 0;
            jsonArrayLength = jsonArray.length()+2;
            while (count <jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                subject = JO.getString("notice_subject");
                description = JO.getString("notice_desc");
                id = JO.getInt("notice_id");
                Notice notice = new Notice(subject, description,id);
                noticeAdapter.add(notice);
                count++;
            }
            //noticeAdapter.reverseList();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      /*  String jsonSP = MainActivity.sharedPreference.getString(MainActivity.key_notice,null);
        JSONObject jsonObject;
        JSONArray jsonArray;
        try {
            jsonObject = new JSONObject(jsonSP);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = (jsonArray.length())-1;
            while (count >=0) {
                JSONObject JO = jsonArray.getJSONObject(count);
                subject = JO.getString("notice_subject");
                description = JO.getString("notice_desc");
                id = JO.getInt("notice_id");
                Notice notice = new Notice(subject, description);
                noticeAdapter.add(notice);
                count--;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    public void JSONOnSwipe() {
        try {
            BackgroundTask backgroundTask = new BackgroundTask();
            backgroundTask.execute();
            noticeAdapter.clearList();
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            jsonArrayLength = jsonArray.length()+2;
            int count = 0;
            while (count <jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                subject = JO.getString("notice_subject");
                description = JO.getString("notice_desc");
                id = JO.getInt("notice_id");
                Notice notice = new Notice(subject, description,id);
                noticeAdapter.add(notice);
                count++;
            }
            //noticeAdapter.reverseList();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

}
