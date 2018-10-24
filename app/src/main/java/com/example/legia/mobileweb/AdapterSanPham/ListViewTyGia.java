package com.example.legia.mobileweb.AdapterSanPham;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.legia.mobileweb.DTO.TyGia;
import com.example.legia.mobileweb.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ListViewTyGia extends BaseAdapter {
    private Context context;
    private List<TyGia> tyGia;


    public ListViewTyGia(Context context, List<TyGia> tyGia) {
        this.context = context;
        this.tyGia = tyGia;
    }

    @Override
    public int getCount() {
        return tyGia.size();
    }

    @Override
    public Object getItem(int position) {
        return tyGia.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_tygia,parent,false);
        }

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#D2D7D3"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#ECECEC"));
        }

        TextView txtMaTyGia = convertView.findViewById(R.id.txtMaTyGia);
        TextView txtGiaMua = convertView.findViewById(R.id.txtGiaMua);
        TextView txtGiaBan = convertView.findViewById(R.id.txtGiaBan);

        txtGiaBan.setText(tyGia.get(position).getGiaBan()+"");
        txtGiaMua.setText(tyGia.get(position).getGiaMua()+"");
        txtMaTyGia.setText(tyGia.get(position).getMaTyGia());

        return convertView;
    }


}
