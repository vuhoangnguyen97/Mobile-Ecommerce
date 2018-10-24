package com.example.legia.mobileweb;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.legia.mobileweb.AdapterSanPham.ListViewSanPhamSoSanh;
import com.example.legia.mobileweb.AdapterSanPham.ListViewSanPhamTimKiem;
import com.example.legia.mobileweb.AdapterSanPham.SanPhamAdapterNew;
import com.example.legia.mobileweb.DAO.sanPhamDAO;
import com.example.legia.mobileweb.DAO.themVaoGioHang;
import com.example.legia.mobileweb.DTO.sanPham;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class search extends AppCompatActivity {
    ListView listResultSearch;
    Spinner searchHang, searchGia;
    ListViewSanPhamTimKiem adapterTimKiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.setTitle("Tìm kiếm");
        listResultSearch = findViewById(R.id.listResultSearch);
        searchGia = findViewById(R.id.searchGia);
        searchHang = findViewById(R.id.searchHang);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        List<String> dsGiaSpinner = new ArrayList<>();
        dsGiaSpinner.add("--Chọn mức giá--");
        dsGiaSpinner.add("Dưới 1 Triệu");
        dsGiaSpinner.add("1 - 3 Triệu");
        dsGiaSpinner.add("3 - 7 Triệu");
        dsGiaSpinner.add("7 - 10 Triệu");
        dsGiaSpinner.add("Trên 10 Triệu");

        List<String> dsHangSpinner = new ArrayList<>();
        dsHangSpinner.add("--Chọn hãng: --");
        dsHangSpinner.add("Apple");
        dsHangSpinner.add("Samsung");
        dsHangSpinner.add("Oppo");
        dsHangSpinner.add("Sony");
        dsHangSpinner.add("Asus");
        dsHangSpinner.add("HTC");
        dsHangSpinner.add("Nokia");
        dsHangSpinner.add("Philips");
        dsHangSpinner.add("Vivo");

        // Creating adapter for spinner
        ArrayAdapter<String> giaSpinnerArrayAdapter = new ArrayAdapter<>(search.this, android.R.layout.simple_spinner_item, dsGiaSpinner);


        giaSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Creating adapter for spinner
        ArrayAdapter<String> hangSpinnerArrayAdapter = new ArrayAdapter<>(search.this, android.R.layout.simple_spinner_item, dsHangSpinner);

        hangSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        searchGia.setAdapter(giaSpinnerArrayAdapter);
        searchHang.setAdapter(hangSpinnerArrayAdapter);

        // Search theo hãng / giá
        searchHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mucGia = searchGia.getSelectedItem().toString();
                String hangSP = searchHang.getSelectedItem().toString();


                switch (position){
                    case 0: // Apple
                        switch (mucGia){
                            case "--Chọn mức giá--":
                                List<sanPham> listTatCaHang = sanPhamDAO.timKiemTheoHang(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTatCaHang);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Dưới 1 Triệu":
                                List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listDuoi1Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "1 - 3 Triệu":
                                List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list1Den3Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "3 - 7 Triệu":
                                List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list3Den7Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "7 - 10 Triệu":
                                List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list7Den10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Trên 10 Triệu":
                                List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTren10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                        }
                        break;
                    case 1:
                        switch (mucGia){
                            case "--Chọn mức giá--":
                                List<sanPham> listTatCaHang = sanPhamDAO.timKiemTheoHang(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTatCaHang);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Dưới 1 Triệu":
                                List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listDuoi1Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "1 - 3 Triệu":
                                List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list1Den3Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "3 - 7 Triệu":
                                List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list3Den7Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "7 - 10 Triệu":
                                List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list7Den10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Trên 10 Triệu":
                                List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTren10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                        }
                        break;
                    case 2:
                        switch (mucGia){
                            case "--Chọn mức giá--":
                                List<sanPham> listTatCaHang = sanPhamDAO.timKiemTheoHang(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTatCaHang);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Dưới 1 Triệu":
                                List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listDuoi1Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "1 - 3 Triệu":
                                List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list1Den3Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "3 - 7 Triệu":
                                List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list3Den7Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "7 - 10 Triệu":
                                List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list7Den10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Trên 10 Triệu":
                                List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTren10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                        }
                        break;
                    case 3:
                        switch (mucGia){
                            case "--Chọn mức giá--":
                                List<sanPham> listTatCaHang = sanPhamDAO.timKiemTheoHang(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTatCaHang);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Dưới 1 Triệu":
                                List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listDuoi1Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "1 - 3 Triệu":
                                List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list1Den3Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "3 - 7 Triệu":
                                List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list3Den7Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "7 - 10 Triệu":
                                List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list7Den10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Trên 10 Triệu":
                                List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTren10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                        }
                        break;
                    case 4:
                        switch (mucGia){
                            case "--Chọn mức giá--":
                                List<sanPham> listTatCaHang = sanPhamDAO.timKiemTheoHang(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTatCaHang);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Dưới 1 Triệu":
                                List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listDuoi1Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "1 - 3 Triệu":
                                List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list1Den3Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "3 - 7 Triệu":
                                List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list3Den7Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                ;
                            case "7 - 10 Triệu":
                                List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list7Den10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Trên 10 Triệu":
                                List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTren10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                        }
                        break;
                    case 5:
                        switch (mucGia){
                            case "--Chọn mức giá--":
                                List<sanPham> listTatCaHang = sanPhamDAO.timKiemTheoHang(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTatCaHang);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Dưới 1 Triệu":
                                List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listDuoi1Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "1 - 3 Triệu":
                                List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list1Den3Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "3 - 7 Triệu":
                                List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list3Den7Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                ;
                            case "7 - 10 Triệu":
                                List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list7Den10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Trên 10 Triệu":
                                List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTren10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                        }
                        break;
                    case 6:
                        switch (mucGia){
                            case "--Chọn mức giá--":
                                List<sanPham> listTatCaHang = sanPhamDAO.timKiemTheoHang(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTatCaHang);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Dưới 1 Triệu":
                                List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listDuoi1Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "1 - 3 Triệu":
                                List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list1Den3Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "3 - 7 Triệu":
                                List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list3Den7Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                ;
                            case "7 - 10 Triệu":
                                List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list7Den10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Trên 10 Triệu":
                                List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTren10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                        }
                        break;
                    case 7:
                        switch (mucGia){
                            case "--Chọn mức giá--":
                                List<sanPham> listTatCaHang = sanPhamDAO.timKiemTheoHang(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTatCaHang);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Dưới 1 Triệu":
                                List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listDuoi1Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "1 - 3 Triệu":
                                List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list1Den3Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "3 - 7 Triệu":
                                List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list3Den7Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                ;
                            case "7 - 10 Triệu":
                                List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list7Den10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Trên 10 Triệu":
                                List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTren10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                        }
                        break;
                    case 8:
                        switch (mucGia){
                            case "--Chọn mức giá--":
                                List<sanPham> listTatCaHang = sanPhamDAO.timKiemTheoHang(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTatCaHang);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Dưới 1 Triệu":
                                List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listDuoi1Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "1 - 3 Triệu":
                                List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list1Den3Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "3 - 7 Triệu":
                                List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list3Den7Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                ;
                            case "7 - 10 Triệu":
                                List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list7Den10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Trên 10 Triệu":
                                List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTren10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                        }
                        break;
                    case 9:
                        switch (mucGia){
                            case "--Chọn mức giá--":
                                List<sanPham> listTatCaHang = sanPhamDAO.timKiemTheoHang(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTatCaHang);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Dưới 1 Triệu":
                                List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listDuoi1Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "1 - 3 Triệu":
                                List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list1Den3Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "3 - 7 Triệu":
                                List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list3Den7Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                ;
                            case "7 - 10 Triệu":
                                List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list7Den10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                            case "Trên 10 Triệu":
                                List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTren10Trieu);
                                listResultSearch.setAdapter(adapterTimKiem);
                                break;
                        }
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchGia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListViewSanPhamTimKiem adapterTimKiem;
                String hangSP = searchHang.getSelectedItem().toString();
                //Thiếu Tìm kiếm theo giá only.

                switch (position){
                    case 0:
                        List<sanPham> listSanPhamHang = sanPhamDAO.timKiemTheoHang(hangSP);
                        adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listSanPhamHang);
                        listResultSearch.setAdapter(adapterTimKiem);
                        break;
                    case 1: // Dưới 1 Triệu
                        List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                        adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listDuoi1Trieu);
                        listResultSearch.setAdapter(adapterTimKiem);
                        break;
                    case 2: // 1 - 3 Triệu
                        List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                        adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list1Den3Trieu);
                        listResultSearch.setAdapter(adapterTimKiem);
                        break;
                    case 3: // 3- 7 Triệu
                        List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                        adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list3Den7Trieu);
                        listResultSearch.setAdapter(adapterTimKiem);
                        break;
                    case 4: // 7 - 10 Triệu
                        List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                        adapterTimKiem = new ListViewSanPhamTimKiem(search.this, list7Den10Trieu);
                        listResultSearch.setAdapter(adapterTimKiem);
                        break;
                    case 5: // Trên 10 Triệu
                        List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                        adapterTimKiem = new ListViewSanPhamTimKiem(search.this, listTren10Trieu);
                        listResultSearch.setAdapter(adapterTimKiem);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.searchBar);

        SearchView searchView = (SearchView) item.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Tìm kiếm...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // WHILE USER TYPING
                Boolean injected = checkInjection(newText);
                if(injected==true){
                    List<String> error = new ArrayList<>();
                    error.add("Không được nhập ký tự đặc biệt");
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                            (search.this, android.R.layout.simple_list_item_1, error);

                    listResultSearch.setAdapter(arrayAdapter);
                }
                else{
                    searchHang.setSelection(0);
                    searchGia.setSelection(0);
                    List<sanPham> dsSanPhamSearch = sanPhamDAO.timTheoTen(newText);
                    adapterTimKiem = new ListViewSanPhamTimKiem(search.this, dsSanPhamSearch);
                    if(dsSanPhamSearch.size()>=1){
                        listResultSearch.setAdapter(adapterTimKiem);
                    }
                    else{
                        List<String> error = new ArrayList<>();
                        error.add("Không tìm thấy sản phẩm");
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                (search.this, android.R.layout.simple_list_item_1, error);
                        listResultSearch.setAdapter(arrayAdapter);
                    }
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
    int count = gioHang.countSoLuongMua();
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        if(count>=1){
            intent.putExtra("countGioHang", 0);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(this, MainActivity.class);
                if(count>=1){
                    intent.putExtra("countGioHang", 0);
                }
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
                return true;
        }
        return false;
    }

    private Boolean checkInjection(String s) {
        Boolean injection = false;
        Pattern p = Pattern.compile("'(''|[^'])*'");
        Matcher m = p.matcher(s);
        boolean b = m.find();
        if (b == true)
            injection = true;
        else
            injection = false;

        return injection;
    }

}
