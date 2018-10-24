package com.example.legia.mobileweb.AdapterHeThong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.legia.mobileweb.R;

import java.util.List;

public class AdapterMemberCard extends BaseAdapter {
    private Context context;
    private List<Integer> listMemberCard;

    public AdapterMemberCard(Context context, List<Integer> listMemberCard) {
        this.context = context;
        this.listMemberCard = listMemberCard;
    }

    @Override
    public int getCount() {
        return listMemberCard.size();
    }

    @Override
    public Object getItem(int position) {
        return listMemberCard.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_membercard,parent,false);
        }
        ImageView imgMemberCard = convertView.findViewById(R.id.imgMembercard);
        imgMemberCard.setImageResource(listMemberCard.get(position));
        return convertView;
    }
}
