package com.example.semin.creative_fusion_competition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Graph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

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

        LineChart lineChart = (LineChart) findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<>();
        for(int i = 0; i<list.size(); i++){
            entries.add(new Entry(list.get(i).getStatus(),i));
        }


        LineDataSet dataset = new LineDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i = 0; i< list.size(); i++){
            labels.add(Integer.toString(i));
        }


        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        /*dataset.setDrawCubic(true); //선 둥글게 만들기
        dataset.setDrawFilled(true); //그래프 밑부분 색칠*/

        lineChart.setData(data);
        lineChart.animateY(5000);
    }
}
