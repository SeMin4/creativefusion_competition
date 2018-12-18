package com.example.semin.creative_fusion_competition;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class Meet extends AppCompatActivity {
    Button record_button;
    TextView txtView;
    Intent intent;
    SpeechRecognizer mRecognizer;
    private TextToSpeech tts;
    private final int MY_PERMISSONS_RECORD_AUDIO = 1;
    ImageView sample;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);
        if(ContextCompat.checkSelfPermission(Meet.this,Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
           if(ActivityCompat.shouldShowRequestPermissionRationale(Meet.this,Manifest.permission.RECORD_AUDIO)){
           }
           else {
               ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},5);
           }
        }
        //tts를 생성 뒤 OnInitListener로 초기화 합니다.
        tts = new TextToSpeech(Meet.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
        sample = (ImageView) findViewById(R.id.banana_gif);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(sample);
        Glide.with(Meet.this).load(R.drawable.image_talk).into(gifImage);


        record_button = (Button)findViewById(R.id.record_btn);
        txtView = (TextView)findViewById(R.id.conv_text);
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(recognitionListener);
        record_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mRecognizer.startListening(intent);
            }
        });
    }

    private RecognitionListener recognitionListener =   new
            RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {
                    GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(sample);
                    Glide.with(Meet.this).load(R.drawable.image_hear).into(gifImage);
                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float v) {

                }

                @Override
                public void onBufferReceived(byte[] bytes) {

                }

                @Override
                public void onEndOfSpeech() {
                    GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(sample);
                    Glide.with(Meet.this).load(R.drawable.image_talk).into(gifImage);
                }

                @Override
                public void onError(int i) {
                    txtView.setText("너무 늦게 말하면 오류뜹니다");
                }

                @Override
                public void onResults(Bundle bundle) {
                    String key = "";
                    key = SpeechRecognizer.RESULTS_RECOGNITION;
                    ArrayList<String> mResult = bundle.getStringArrayList(key);
                    String[] rs = new String[mResult.size()];
                    mResult.toArray(rs);
                    txtView.setText(rs[0]);
                    String send_text = txtView.getText().toString();
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
                       // Toast.makeText(Meet.this, "저장완료", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(Meet.this, "File not found", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String answer = replyAnswer(send_text);
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "chat_record.txt", true));
                        String result = "";
                        long now = System.currentTimeMillis();//현재 시간 받아오기
                        Date date = new Date(now);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String nowTime = simpleDateFormat.format(date);
                        result = nowTime + "," + "2" + "," + answer;
                        bw.write(result);
                        bw.write("\n");
                        bw.close();
                        // Toast.makeText(Meet.this, "저장완료", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(Meet.this, "File not found", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tts.speak(answer,TextToSpeech.QUEUE_ADD,null);


                }
                @Override
                public void onPartialResults(Bundle bundle) {

                }
                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            };
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
        } else if (input.equals("응내말들어줘서고마워**야")) {
            reply = "아니에요, 다음에도 이런일 있으시면 언제든지 들어드릴께요.";
        } else if (input.equals("아나오늘너무화나")) {
            reply = "무슨 일이시죠? 진정화세요.";
        } else if (input.equals("조별과제에조원들이참여를안해")) {
            reply = "그것 참 화나는 일이네요.";
        } else if (input.equals("PPT도나혼자다만들고의견을물어봐도답이없어")||
                input.equals("ppt도나혼자다만들고의견을물어봐도답이없어")) {
            reply = "정말 나쁜 사람들이네요. 가만히 계시지 말고, 그 사람들에게 불만을 말하세요.";
        } else if (input.equals("그럴까그래야겠다고마워**야")) {
            reply = "네, 화푸세요 주인님.";
        } else if (input.equals("나너무힘들어")) {
            reply = "왜 그러세요?";
        }else if(input.equals("진짜과제가너무많고시험도많아이거언제다하지")){
            reply = "주인님, 힘내세요, 피할 수 없으면 즐기세요.";
        }else if(input.equals("어떻게그렇게말하니나오늘밤도새야한단말이야")){
            reply = "농담이었어요, 여태 수고많으셨어요, 오늘하루는 그냥 쉬어보는 것도 나쁘지 않을 것 같아요.";
        }else if(input.equals("그럴까")){
            reply = "네, 열심히 하셨잖아요.";
        }else if(input.equals("그래야겠다고마워**야")){
            reply = "네, 수고하셨어요, 주인님 푹 쉬세요.";
        }else{
            reply = "제가 잘 모르는 말이에요. 가르쳐 주세요.";
        }
        return reply;
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
