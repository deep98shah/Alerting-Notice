package com.project.android.takshashilaacollege2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayNotice extends AppCompatActivity {

    TextView textViewSubject,textViewDescription;
    String notice_subject,notice_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notice);
        textViewSubject = (TextView)findViewById(R.id.displayNewSubject);
        textViewDescription = (TextView) findViewById(R.id.displayNewDescription);
        notice_subject = getIntent().getExtras().getString("notice_subject");
        notice_desc = getIntent().getExtras().getString("notice_desc");
        textViewSubject.setText(notice_subject);
        textViewDescription.setText(notice_desc);
    }
}
