package com.example.semin.creative_fusion_competition;

import android.os.Environment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ChatLog extends AppCompatActivity {
    Button sendBtn;
    EditText chat_text;
    String send_text;

    ChatMessageAdapter chatMessageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_log);
        chat_text = (EditText) findViewById(R.id.chat_send_text);
        sendBtn = (Button) findViewById(R.id.send_btn);


        ListView listView = (ListView)findViewById(R.id.listview);
        final ArrayList<ChatFormObject> record_all = new ArrayList<>();
        String line = null;
        try{
            BufferedReader buf = new BufferedReader(new FileReader( getFilesDir() +"chat_record.txt"));
            String str= null;
            while((str = buf.readLine())!= null){
                String[] one_record = str.split(",");
                record_all.add(new ChatFormObject(one_record[0],one_record[1],one_record[2]));
            }
            buf.close();
        }catch(FileNotFoundException e) {
           // Toast.makeText(ChatLog.this,"File not found",Toast.LENGTH_SHORT).show();
            
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        chatMessageAdapter = new ChatMessageAdapter(ChatLog.this,R.layout.chatting_message,record_all);
        listView.setAdapter(chatMessageAdapter);
        sendBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                send_text = chat_text.getText().toString();
                chat_text.setHint(R.string.hint);
                chat_text.setText("");
                try{
                    BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "chat_record.txt",true));
                    String result = "";
                    long now = System.currentTimeMillis();//현재 시간 받아오기
                    Date date = new Date(now);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String nowTime = simpleDateFormat.format(date);
                    result  = nowTime + "," + "1" + "," + send_text;
                    bw.write(result);
                    bw.write("\n");
                    bw.close();
                    record_all.add(new ChatFormObject(nowTime,"1",send_text));
                    Toast.makeText(ChatLog.this, "저장완료", Toast.LENGTH_SHORT).show();
                }catch(FileNotFoundException e) {
                    Toast.makeText(ChatLog.this,"File not found",Toast.LENGTH_SHORT).show();
                }catch(IOException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }
                chatMessageAdapter.notifyDataSetChanged();
            }
        });

    }
}
