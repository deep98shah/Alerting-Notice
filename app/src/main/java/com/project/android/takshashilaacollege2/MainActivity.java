package com.project.android.takshashilaacollege2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.project.android.takshashilaacollege2.BackgroundTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    static String JSON_STRINGMain;
    Button noticeButton;
    ImageView imageView;
    public static final String user_pref = "user_pref";
    public static final String key_notice = "key_notice";
    public static final String key_count = "key_count";
    static int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageNotification);
        noticeButton = (Button)findViewById(R.id.noticeButton);
        callAsynchronousTaskCount();
        callAsynchronousTask();
        //Log.v("Debug",MainActivity.JSON_STRINGMain);
        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.GONE);
                if(JSON_STRINGMain==null)
                {
                    Toast.makeText(getApplicationContext(),"Error getting JSON",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent noticeIntent = new Intent(MainActivity.this,NoticeActivity.class);
                    noticeIntent.putExtra("json_data",JSON_STRINGMain);
                    startActivity(noticeIntent);
                }
            }
        });
    }

    private class BackgroundTaskCount extends AsyncTask<Void,Void,Integer>
    {
        private String count_url;
        private int COUNT_INTEGER;
        @Override
        protected void onPreExecute() {
            count_url = "http://shahdeep1615.000webhostapp.com/getcount.php";
            //count_url = "http://192.168.43.160/takshashila/getcountwamp.php";
        }

        @Override
        protected Integer doInBackground(Void... params) {
            String COUNT_STRING;
            try {
                URL url = new URL(count_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((COUNT_STRING=bufferedReader.readLine())!=null) {
                    stringBuilder.append(COUNT_STRING).append("\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                COUNT_INTEGER = Integer.parseInt(stringBuilder.toString().trim());
                //Log.v("COUNT_INTEGER",String.valueOf(COUNT_INTEGER));
                return COUNT_INTEGER;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            count = result;
            if(count != -1)
            {
                SharedPreferences sharedPreferences = getSharedPreferences(user_pref,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //Log.v("key_count: ",String.valueOf(sharedPreferences.getInt(key_count,0)));
                if(sharedPreferences.getInt(key_count,0)<count)
                {
                    imageView.setVisibility(View.VISIBLE);
                    editor.putInt(key_count,count);
                    editor.commit();
                }
                else if(sharedPreferences.getInt(key_count,0)>count)
                {
                    editor.putInt(key_count,count);
                    editor.commit();
                }

            }
            //Log.v("RecordCount: ",String.valueOf(count));
        }
    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            //Toast.makeText(getApplicationContext(),"JSONFetch",Toast.LENGTH_SHORT).show();
                            BackgroundTask backgroundTask = new BackgroundTask();
                            // PerformBackgroundTask this class is the class that extends AsynchTask
                            backgroundTask.execute();
                            ;
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 50000 ms
    }

    public void callAsynchronousTaskCount() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            //Toast.makeText(getApplicationContext(),"JSONFetchCount",Toast.LENGTH_SHORT).show();
                            BackgroundTaskCount backgroundTaskCount = new BackgroundTaskCount();
                            // PerformBackgroundTask this class is the class that extends AsynchTask
                            backgroundTaskCount.execute();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 2000); //execute in every 50000 ms
    }

    /*@Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }*/
    //Runnable task
    /*private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            Toast.makeText(MainActivity.this,"in runnable",Toast.LENGTH_SHORT).show();
            MainActivity.this.mHandler.postDelayed(m_Runnable,10000);
        }

    };*/

}