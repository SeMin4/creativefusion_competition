package com.example.semin.creative_fusion_competition;

import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;


public class ChatLog extends AppCompatActivity {
    Button sendBtn, sendBtn2;
    EditText chat_text;
    String send_text;
    private TextToSpeech tts;
    ChatMessageAdapter chatMessageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_log);
        chat_text = (EditText) findViewById(R.id.chat_send_text);
        sendBtn = (Button) findViewById(R.id.send_btn);
        sendBtn2 = (Button) findViewById(R.id.send2_btn);
        //tts를 생성 뒤 OnInitListener로 초기화 합니다.
        tts = new TextToSpeech(ChatLog.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
        final ListView listView = (ListView)findViewById(R.id.listview);
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
            public void onClick(View view) {
                send_text = chat_text.getText().toString();
                if (send_text.isEmpty()) {

                } else {
                    chat_text.setHint(R.string.hint);
                    chat_text.setText("");
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "chat_record.txt", true));
                        String result = "";
                        long now = System.currentTimeMillis();//현재 시간 받아오기
                        Date date = new Date(now);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String nowTime = simpleDateFormat.format(date);
                        result = nowTime + "," + "1" + "," + send_text;
                        bw.write(result);
                        bw.write("\n");
                        bw.close();
                        record_all.add(new ChatFormObject(nowTime, "1", send_text));
                        Toast.makeText(ChatLog.this, "저장완료", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(ChatLog.this, "File not found", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    chatMessageAdapter.notifyDataSetChanged();
                    listView.setSelection(chatMessageAdapter.getCount()-1);
                }
            }
        });
        sendBtn2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                send_text = chat_text.getText().toString();
                if (send_text.isEmpty()) {

                } else {
                    chat_text.setHint(R.string.hint);
                    chat_text.setText("");
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "chat_record.txt", true));
                        String result = "";
                        long now = System.currentTimeMillis();//현재 시간 받아오기
                        Date date = new Date(now);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String nowTime = simpleDateFormat.format(date);
                        result = nowTime + "," + "2" + "," + send_text;
                        bw.write(result);
                        bw.write("\n");
                        bw.close();
                        record_all.add(new ChatFormObject(nowTime, "2", send_text));
                        Toast.makeText(ChatLog.this, "저장완료", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(ChatLog.this, "File not found", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    chatMessageAdapter.notifyDataSetChanged();
                    listView.setSelection(chatMessageAdapter.getCount()-1);
                    tts.speak(send_text,TextToSpeech.QUEUE_ADD,null);
                }
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(tts != null){
            tts.stop();
            tts.shutdown();
            tts= null;
        }
    }
}
