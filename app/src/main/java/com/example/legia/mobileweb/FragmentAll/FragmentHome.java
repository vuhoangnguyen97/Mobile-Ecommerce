package com.example.legia.mobileweb.FragmentAll;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.legia.mobileweb.AdapterSanPham.SanPhamAdapterNew;
import com.example.legia.mobileweb.AdapterSanPham.SlidingAdapter;
import com.example.legia.mobileweb.DAO.sanPhamDAO;
import com.example.legia.mobileweb.DTO.sanPham;
import com.example.legia.mobileweb.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentHome extends Fragment {
    RecyclerView viewTest;
    ViewPager myPager;
    Timer timer;

    int currentPage = 0;
    final long DELAY_MS = 500;//delay in milliseconds
    final long PERIOD_MS = 3000; // time in milliseconds
    int hinh[] = {R.drawable.hinh1, R.drawable.hinh2, R.drawable.hinh3};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home, container, false);
        myPager =  view.findViewById(R.id.bannerNew);
        viewTest = view.findViewById(R.id.viewTest);

        final SlidingAdapter adapter = new SlidingAdapter(container.getContext(), hinh);
        myPager.setAdapter(adapter);
        final Handler handler = new Handler(container.getContext().getMainLooper());
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == adapter.getCount()) {
                    currentPage = 0;
                }
                myPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


        int numberOfColumns = 3;
        viewTest.setLayoutManager(new GridLayoutManager(container.getContext(), numberOfColumns));

        List<sanPham> dssp = sanPhamDAO.DocTatCa();
        SanPhamAdapterNew adapterNew = new SanPhamAdapterNew(container.getContext(), dssp, viewTest);

        viewTest.setAdapter(adapterNew);
        return view;
    }


}
