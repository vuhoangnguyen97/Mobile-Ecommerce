package com.example.legia.mobileweb.FragmentAll;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.legia.mobileweb.AdapterSanPham.ListViewNewsProvider;
import com.example.legia.mobileweb.DTO.newsProvider;
import com.example.legia.mobileweb.News;
import com.example.legia.mobileweb.R;
import com.example.legia.mobileweb.tygia;

import java.util.ArrayList;
import java.util.List;

public class FragmentNews extends Fragment {
    ListView listNewsProvider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_news, container, false);
        listNewsProvider = view.findViewById(R.id.listNewsProvider);
        final List<newsProvider> listProvider = new ArrayList<>();
        listProvider.add(new newsProvider(R.drawable.vietcombank, "Xem tỉ giá", "https://www.vietcombank.com.vn/ExchangeRates/ExrateXML.aspx"));
        listProvider.add(new newsProvider(R.drawable.vnexpress, "VnExpress", "https://vnexpress.net/rss/so-hoa.rss"));
        listProvider.add(new newsProvider(R.drawable.thanhnien, "ThanhNien", "https://thanhnien.vn/rss/cong-nghe-thong-tin.rss"));
        listProvider.add(new newsProvider(R.drawable.apple, "Sự kiện ra mắt", "http://podcasts.apple.com/apple_keynotes/apple_keynotes.xml"));
        listProvider.add(new newsProvider(R.drawable.ict, "ICT News", "http://ictnews.vn/rss/the-gioi-so/di-dong"));
        listProvider.add(new newsProvider(R.drawable.tinhte, "Tinh Tế", "https://tinhte.vn/rss"));
        ListViewNewsProvider adapterNews = new ListViewNewsProvider(container.getContext(), listProvider);
        listNewsProvider.setAdapter(adapterNews);

        listNewsProvider.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent i1 = new Intent(getContext(), tygia.class);
                    Bundle b1 = new Bundle();

                    b1.putString("urlProvider", listProvider.get(position).getUrlProvider());

                    i1.putExtra("news", b1);

                    startActivity(i1);
                    Toast.makeText(getContext(), "Vietcombank", Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent i = new Intent(getContext(), News.class);
                    Bundle b = new Bundle();

                    b.putString("urlProvider", listProvider.get(position).getUrlProvider());
                    if(position==3){
                        b.putString("apple", "apple");
                    }
                    i.putExtra("news", b);

                    startActivity(i);
                }

            }
        });

        return view;
    }
}
