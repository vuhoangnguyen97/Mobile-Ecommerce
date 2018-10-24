package com.example.legia.mobileweb.AdapterSanPham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.legia.mobileweb.DTO.newsProvider;
import com.example.legia.mobileweb.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListViewNewsProvider extends BaseAdapter {
    private Context context;
    private List<newsProvider> dsNews;

    public ListViewNewsProvider(Context context, List<newsProvider> dsNews) {
        this.context = context;
        this.dsNews = dsNews;
    }

    @Override
    public int getCount() {
        return dsNews.size();
    }

    @Override
    public Object getItem(int position) {
        return dsNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_menu_news,parent,false);
        }

        ImageView imgNewsProvider = convertView.findViewById(R.id.imgListNews);
        TextView txtNewsProvider = convertView.findViewById(R.id.txtNewsProvider);

        Picasso.get().load(dsNews.get(position).getImgNewProvider()).into(imgNewsProvider);
        txtNewsProvider.setText(dsNews.get(position).getNameProvider());

        return convertView;
    }
}
