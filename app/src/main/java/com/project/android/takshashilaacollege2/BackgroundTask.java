package com.project.android.takshashilaacollege2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HP on 27-05-2018.
 */

public class BackgroundTask extends AsyncTask<Object, Object, String>
{
    String JSON_STRING;
    private String json_url;
    @Override
    protected void onPreExecute() {
        //json_url="https://api.myjson.com/bins/8izxi";
        json_url="http://shahdeep1615.000webhostapp.com/init.php";
        //json_url="http://192.168.43.160/takshashila/getdata.php";
        //json_url="http://192.168.0.100/takshashila/getdata.php";
    }

    @Override
    protected String doInBackground(Object... params) {

        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            while((JSON_STRING=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(JSON_STRING).append("\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            //Log.d("Debug","Value: "+ stringBuilder.toString().trim());
            //String json_string = stringBuilder.toString().trim();

            return stringBuilder.toString().trim();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        /*TextView textView = (TextView) findViewById(R.id.notice);
        textView.setText(result);*/
        //Log.v("Record json check: ",result);
        MainActivity.JSON_STRINGMain = result;
        NoticeActivity.json_string = result;
    }
}