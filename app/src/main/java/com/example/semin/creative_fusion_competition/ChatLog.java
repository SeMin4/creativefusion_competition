package com.example.semin.creative_fusion_competition;

import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
    Button sendBtn;
    EditText chat_text;
    String send_text;
    ChatMessageAdapter chatMessageAdapter;
    int status = -1;//1:기쁨 2:보통 3:지침4:슬픔 5:화남
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_log);

        /* 달력에 넣을 초기 데이터 생성부분 난중에 지워야함 */






        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        chat_text = (EditText) findViewById(R.id.chat_send_text);
        sendBtn = (Button) findViewById(R.id.send_btn);
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
                status = -1;
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
                       // Toast.makeText(ChatLog.this, "저장완료", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(ChatLog.this, "File not found", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    chatMessageAdapter.notifyDataSetChanged();
                    listView.setSelection(chatMessageAdapter.getCount()-1);
                    String reply = replyAnswer(send_text);
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "chat_record.txt", true));
                        String result = "";
                        long now = System.currentTimeMillis();//현재 시간 받아오기
                        Date date = new Date(now);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String nowTime = simpleDateFormat.format(date);
                        result = nowTime + "," + "2" + "," + reply;
                        bw.write(result);
                        bw.write("\n");
                        bw.close();
                        record_all.add(new ChatFormObject(nowTime, "2", reply));
                       // Toast.makeText(ChatLog.this, "저장완료", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(ChatLog.this, "File not found", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(reply.equals("아니에요, 다음에도 이런일 있으시면 언제든지 들어드릴께요.")){
                        status = 4;
                    }else if(reply.equals("네, 화푸세요 주인님.")){
                        status = 5;
                    }else if(reply.equals("네, 수고하셨어요, 주인님 푹 쉬세요.")){
                        status = 3;
                    }
                    if(status != -1){
                        try {
                            BufferedWriter bw2 = new BufferedWriter(new FileWriter(getFilesDir() + "calendar.txt", true));
                            String result2 = "";
                            long now = System.currentTimeMillis();//현재 시간 받아오기
                            Date date = new Date(now);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy,MM,dd");
                            String nowTime = simpleDateFormat.format(date);
                            //1:기쁨 2:슬픔 3:화남 4:지침 5:보통
                            if(status == 1){
                                result2 = nowTime + "," + "1" + "," + "기분 좋은 것을 보니 저도 덩달아서 기분이 좋아지네요.";
                                bw2.write(result2);
                                bw2.write("\n");
                            }else if(status == 2){
                                result2 = nowTime + "," + "2" + "," + "오늘 하루도 좋은 하루 보내시길 바랄게요.";
                                bw2.write(result2);
                                bw2.write("\n");
                            }else if(status == 3){
                                result2 = nowTime + "," + "3" + "," + "오늘은 일찍 잠자리에 들어가는 것이 어떨까요?";
                                bw2.write(result2);
                                bw2.write("\n");
                            }else if(status == 4){
                                result2 = nowTime + "," + "4" + "," + "맛있는 음식을 먹고 기분을 풀어보세요.";
                                bw2.write(result2);
                                bw2.write("\n");
                            }else if(status == 5){
                                result2 = nowTime + "," + "5" + "," + "밖에 나가서 운동을 통해 스트레스를 풀어보는 것은 어떨까요?";
                                bw2.write(result2);
                                bw2.write("\n");
                            }

                            bw2.close();
                            // Toast.makeText(ChatLog.this, "저장완료", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            Toast.makeText(ChatLog.this, "File not found", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                   chatMessageAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    public String replyAnswer(String input) {
        String reply = "";
        input = input.replaceAll(" ","");
        if (input.equals("나너무슬픈데내이야기좀들어줄래")) {
            reply = "주인님 무슨 일이시죠?";
        } else if (input.equals("나오늘F학점받았어")) {
            reply = "무슨 과목이신데요?";
        } else if (input.equals("자료구조과목이야")) {
            reply = "그 교수님이 나쁜 교수님이시네요.";
        } else if (input.equals("그치내가못해서거런거아니지")) {
            reply = "과제랑 출석 다 하시지 않으셨어요?";
        } else if (input.equals("어맞아나결석도없었고과제도다냈어")) {
            reply = "그 교수님이 나쁜 사람이네요.";
        } else if (input.equals("응내말들어줘서고마워버디야")) {
            reply = "아니에요, 다음에도 이런일 있으시면 언제든지 들어드릴께요.";
        } else if (input.equals("아나오늘너무화나")) {
            reply = "무슨 일이시죠? 진정화세요.";
        } else if (input.equals("조별과제에조원들이참여를안해")) {
            reply = "그것 참 화나는 일이네요.";
        } else if (input.equals("PPT도나혼자다만들고의견을물어봐도답이없어")||
                input.equals("ppt도나혼자다만들고의견을물어봐도답이없어")) {
            reply = "정말 나쁜 사람들이네요. 가만히 계시지 말고, 그 사람들에게 불만을 말하세요.";
        } else if (input.equals("그럴까그래야겠다고마워버디야")) {
            reply = "네, 화푸세요 주인님.";
        } else if (input.equals("나너무힘들어")) {
            reply = "왜 그러세요?";
        }else if(input.equals("진짜과제가너무많고시험도많아이거언제다하지")){
            reply = "주인님, 힘내세요, 피할 수 없으면 즐기세요.";
        }else if(input.equals("어떻게그렇게말하니나오늘밤도새야한단말이야")){
            reply = "농담이었어요, 여태 수고많으셨어요, 오늘하루는 그냥 쉬어보는 것도 나쁘지 않을 것 같아요.";
        }else if(input.equals("그럴까")){
            reply = "네, 열심히 하셨잖아요.";
        }else if(input.equals("그래야겠다고마버디야")){
            reply = "네, 수고하셨어요, 주인님 푹 쉬세요.";
        }
        return reply;
    }

}
