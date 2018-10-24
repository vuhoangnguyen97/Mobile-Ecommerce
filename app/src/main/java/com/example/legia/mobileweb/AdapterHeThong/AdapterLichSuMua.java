package com.example.legia.mobileweb.AdapterHeThong;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.legia.mobileweb.DTO.hoaDon;
import com.example.legia.mobileweb.Encryption.encrypt;
import com.example.legia.mobileweb.R;

import java.util.List;

public class AdapterLichSuMua extends BaseAdapter {
    private Context context;
    private List<hoaDon> dsHoaDon;

    public AdapterLichSuMua(Context context, List<hoaDon> dsHoaDon) {
        this.context = context;
        this.dsHoaDon = dsHoaDon;
    }

    @Override
    public int getCount() {
        return dsHoaDon.size();
    }

    @Override
    public Object getItem(int position) {
        return dsHoaDon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_lichsumua,parent,false);
        }
        TextView txtLichSu = convertView.findViewById(R.id.txtLichSu);
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV

        txtLichSu.setText(encrypt.decryptAES(key, initVector, dsHoaDon.get(position).getChiTiet()));

        return convertView;
    }
}
