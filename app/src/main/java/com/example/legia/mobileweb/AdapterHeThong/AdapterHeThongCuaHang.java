package com.example.legia.mobileweb.AdapterHeThong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.legia.mobileweb.DTO.chiNhanh;
import com.example.legia.mobileweb.R;

import java.util.List;

public class AdapterHeThongCuaHang extends BaseAdapter {
    private Context context;
    private List<chiNhanh> danhSachChiNhan;

    public AdapterHeThongCuaHang(Context context, List<chiNhanh> danhSachChiNhan) {
        this.context = context;
        this.danhSachChiNhan = danhSachChiNhan;
    }

    @Override
    public int getCount() {
        return danhSachChiNhan.size();
    }

    @Override
    public Object getItem(int position) {
        return danhSachChiNhan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_hethong,parent,false);
        }

        TextView txtDiaChiCuaHang = convertView.findViewById(R.id.txtDiaChiCuaHang);
        WebView webCuaHang = convertView.findViewById(R.id.webChiNhanh);

        txtDiaChiCuaHang.setText(danhSachChiNhan.get(position).getDiaChi());
        String mime = "text/html";
        String encoding = "utf-8";
        String map = danhSachChiNhan.get(position).getUrlBanDo();

        webCuaHang.getSettings().setJavaScriptEnabled(true);
        webCuaHang.loadDataWithBaseURL(null, map, mime, encoding, null);

        return  convertView;
    }
}
