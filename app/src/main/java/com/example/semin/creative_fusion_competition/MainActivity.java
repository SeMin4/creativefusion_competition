package com.example.semin.creative_fusion_competition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button Calendarbtn, Meetbtn, Chatbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendarbtn = (Button)findViewById(R.id.calendar_btn);
        Meetbtn = (Button)findViewById(R.id.meeting_btn);
        Chatbtn = (Button)findViewById(R.id.chat_log_btn);
        Calendarbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Calender.class);
                startActivity(intent);
            }
        });
        Meetbtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, Meet.class);
                startActivity(intent);
            }
        });
        Chatbtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,ChatLog.class);
                startActivity(intent);
            }
        });
    }
}
