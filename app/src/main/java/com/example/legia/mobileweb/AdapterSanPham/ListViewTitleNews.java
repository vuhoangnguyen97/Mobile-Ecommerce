package com.example.legia.mobileweb.AdapterSanPham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.legia.mobileweb.R;

import org.w3c.dom.Text;

import java.util.List;

public class ListViewTitleNews extends BaseAdapter {
    private Context context;
    private List<String> dsTitle;

    public ListViewTitleNews(Context context, List<String> dsTitle) {
        this.context = context;
        this.dsTitle = dsTitle;
    }

    @Override
    public int getCount() {
        return dsTitle.size();
    }

    @Override
    public Object getItem(int position) {
        return dsTitle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_title_news,parent,false);
        }

        TextView txtTitleNews = convertView.findViewById(R.id.txtTitleNews);
        txtTitleNews.setText(dsTitle.get(position));

        return convertView;
    }
}
