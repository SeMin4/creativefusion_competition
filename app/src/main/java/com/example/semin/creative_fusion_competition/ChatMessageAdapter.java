package com.example.semin.creative_fusion_competition;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ChatFormObject> data;
    private int layout;

    public ChatMessageAdapter(Context context, int layout, ArrayList<ChatFormObject> data){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data =data;
        this.layout = layout;
    }

    @Override
    public int getCount(){
        return data.size();
    }
    @Override
    public String getItem(int position){
        return null;
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    public ChatFormObject getChat(int position){
        return data.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        ChatFormObject listviewitem=data.get(position);
        TextView name=(TextView)convertView.findViewById(R.id.chatmessage);
        name.setText(listviewitem.getMessage());

        return convertView;
    }
}
