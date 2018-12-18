package com.example.semin.creative_fusion_competition;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Calender extends AppCompatActivity {
    TextView calendartext;
    Button graphbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        calendartext = (TextView)findViewById(R.id.calendar_text);
        final ArrayList<CalenderFormObject> list = new ArrayList<>();

        try{
            BufferedReader buf = new BufferedReader(new FileReader( getFilesDir() +"calendar.txt"));
            String str= null;
            while((str = buf.readLine())!= null){
                String[] one_record = str.split(",");
                int txt_year = Integer.parseInt(one_record[0]);
                int txt_month = Integer.parseInt(one_record[1]);
                int txt_day = Integer.parseInt(one_record[2]);
                int txt_status = Integer.parseInt(one_record[3]);
                list.add(new CalenderFormObject(txt_year,txt_month,txt_day,txt_status,one_record[4]));
            }
            buf.close();
        }catch(FileNotFoundException e) {
            // Toast.makeText(ChatLog.this,"File not found",Toast.LENGTH_SHORT).show();

        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }


        CalendarView calendar = (CalendarView)findViewById(R.id.real_calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                for(int i =0; i<list.size();i++){
                    if(list.get(i).getYear() == year){
                        if(list.get(i).getMonth() == month +1){
                            if(list.get(i).getDay() == dayOfMonth){
                                calendartext.setText(list.get(i).getComment());
                            }
                        }

                    }
                }
            }

        });
        graphbtn = (Button) findViewById(R.id.graph_btn);
        graphbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Calender.this, Graph.class);
                startActivity(intent);
            }
        });

    }
}
