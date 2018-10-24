package com.example.legia.mobileweb;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
//import com.google.android.gms.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legia.mobileweb.AdapterHeThong.AdapterHeThongCuaHang;
import com.example.legia.mobileweb.DTO.chiNhanh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class hethong extends AppCompatActivity {
    Spinner cbChiNhanh;
    TextView txtCount;
    ListView listCuaHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hethong);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.setTitle("Hệ thống cửa hàng");
        cbChiNhanh = findViewById(R.id.cbChiNhanh);
        txtCount = findViewById(R.id.txtCount);
        listCuaHang = findViewById(R.id.listHeThong);

        //String quan = getCurrentLocate();

        List<String> danhSachQuan = new ArrayList<>();
        danhSachQuan.add("Quận 1");
        danhSachQuan.add("Quận 2");
        danhSachQuan.add("Quận 3");
        danhSachQuan.add("Quận 4");
        danhSachQuan.add("Quận 5");
        danhSachQuan.add("Quận 6");
        danhSachQuan.add("Quận 7");
        danhSachQuan.add("Quận 8");
        danhSachQuan.add("Quận 10");
        danhSachQuan.add("Quận 11");
        danhSachQuan.add("Quận Gò Vấp");
        danhSachQuan.add("Quận Bình Thạnh");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhSachQuan);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        cbChiNhanh.setAdapter(dataAdapter);
        cbChiNhanh.setSelection(10);
        Handler dsDefault = new Handler(getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<chiNhanh> danhSachMacDinh = new ArrayList<>();
                danhSachMacDinh.add(quanGoVap("371 Nguyễn Kiệm", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3918.9357285312726!2d106.67562481314965!3d10.816230592294495!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x317528e19559e523%3A0x8e4133bdb1373cc9!2zMzcxIE5ndXnhu4VuIEtp4buHbSwgUGjGsOG7nW5nIDMsIEfDsiBW4bqlcCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0!5e0!3m2!1sen!2s!4v1533045856426\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>"));
                danhSachMacDinh.add(quanGoVap("429 Phan Văn Trị, Phường 1, Gò Vấp, Hồ Chí Minh, Việt Nam", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1959.431361811714!2d106.69263290537425!3d10.82181580967611!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x317528ee048fc577%3A0xd27458a4b7908309!2zNDI5IFBoYW4gVsSDbiBUcuG7iywgUGjGsOG7nW5nIDEsIEfDsiBW4bqlcCwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532926706155\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));

                txtCount.setText("Tìm thấy : " + danhSachMacDinh.size() + " chi nhánh");
                AdapterHeThongCuaHang adapter = new AdapterHeThongCuaHang(hethong.this, danhSachMacDinh);
                listCuaHang.setAdapter(adapter);
            }
        };
        dsDefault.post(runnable);


        // Chọn quận
        cbChiNhanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AdapterHeThongCuaHang adapterChiNhanh = null;
                switch (position){
                    case 0: // Quận 1
                        List<chiNhanh> chiNhanhQuan1 = new ArrayList<>();
                        chiNhanhQuan1.add(quan1("161 Đường Đề Thám, Phường Cô Giang, Quận 1", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d979.8971791628461!2d106.69390972922454!3d10.766146617018977!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752f15dde19319%3A0x8b953f061e155991!2zMTYxIMSQxrDhu51uZyDEkOG7gSBUaMOhbSwgUGjGsOG7nW5nIEPDtCBHaWFuZywgUXXhuq1uIDEsIEjhu5MgQ2jDrSBNaW5oLCBWaeG7h3QgTmFt!5e0!3m2!1svi!2s!4v1532925154241\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));
                        chiNhanhQuan1.add(quan1("345 Pasteur, Phường 8, Quận 1", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d979.8327914862354!2d106.68931622922449!3d10.785928116931562!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752f324144c78f%3A0x507c658463cef69d!2zMzQ1IFBhc3RldXIsIFBoxrDhu51uZyA4LCBRdeG6rW4gMywgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925233812\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));

                        txtCount.setText("Tìm thấy : " + chiNhanhQuan1.size() + " chi nhánh");
                        adapterChiNhanh = new AdapterHeThongCuaHang(hethong.this, chiNhanhQuan1);
                        listCuaHang.setAdapter(adapterChiNhanh);
                        break;
                    case 1: // Quận 2
                        List<chiNhanh> chiNhanhQuan2 = new ArrayList<>();
                        chiNhanhQuan2.add(quan2("51 Quốc Hương, Thảo Điền, Quận 2", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.0758864281333!2d106.7286779652604!3d10.80549986160995!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3175261912f0bd2f%3A0x6ad7f19f19f456fa!2zNTEgUXXhu5FjIEjGsMahbmcsIFRo4bqjbyDEkGnhu4FuLCBRdeG6rW4gMiwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925463648\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));
                        chiNhanhQuan2.add(quan2("B6/2 Lương Định Của, P. Bình An, Quận 2", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.3523236402502!2d106.7257145430864!3d10.784304425821045!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x317525fef2199ae5%3A0xb9ee14d1d232ecc6!2zQjYvMiBMxrDGoW5nIMSQ4buLbmggQ-G7p2EsIFAuIELDrG5oIEFuLCBRdeG6rW4gMiwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925531469\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));

                        txtCount.setText("Tìm thấy : " + chiNhanhQuan2.size() + " chi nhánh");
                        adapterChiNhanh = new AdapterHeThongCuaHang(hethong.this, chiNhanhQuan2);
                        listCuaHang.setAdapter(adapterChiNhanh);
                        break;
                    case 2: // Quận 3
                        List<chiNhanh> chiNhanhQuan3 = new ArrayList<>();
                        chiNhanhQuan3.add(quan3("177 Lý Chính Thắng, Phường 7, Quận 3",
                         "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.3688561559593!2d106.68266022861228!3d10.783035513680808!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752f2eec6750d1%3A0x5414897993aaf7d6!2zMTc3IEzDvSBDaMOtbmggVGjhuq9uZywgUGjGsOG7nW5nIDcsIFF14bqtbiAzLCBI4buTIENow60gTWluaCwgVmnhu4d0IE5hbQ!5e0!3m2!1svi!2s!4v1532925664520\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));
                        chiNhanhQuan3.add(quan3("35 Võ Văn Tần, Phường 6, Quận 3, Hồ Chí Minh, Việt Nam"
                         , "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1959.717413875346!2d106.69065271194006!3d10.777970558071694!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752f3a65431e09%3A0x7b06ffed0c90ab51!2zMzUgVsO1IFbEg24gVOG6p24sIFBoxrDhu51uZyA2LCBRdeG6rW4gMywgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925703267\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));
                        txtCount.setText("Tìm thấy : " + chiNhanhQuan3.size() + " chi nhánh");
                        adapterChiNhanh = new AdapterHeThongCuaHang(hethong.this, chiNhanhQuan3);
                        listCuaHang.setAdapter(adapterChiNhanh);

                        break;
                    case 3: // Quận 4
                        List<chiNhanh> chiNhanhQuan4 = new ArrayList<>();
                        chiNhanhQuan4.add(quan4("126 Xóm Chiếu, Quận 4","<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1904.0561159671952!2d106.70970666209917!3d10.75771051310518!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752f6f91e257f9%3A0x6230ddce919c6693!2zMTI2IFjDs20gQ2hp4bq_dSwgUGjGsOG7nW5nIDE2LCBRdeG6rW4gNCwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532886371033\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));

                        chiNhanhQuan4.add(quan4("109D1/12 Hoàng Diệu,Phường 9,Quận 4","<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d979.9091812066962!2d106.7022248292245!3d10.762455317035277!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752f6b781d1469%3A0x5fb150bf209af169!2zMTA5RDEvMTIgSG_DoG5nIERp4buHdSwgUGjGsOG7nW5nIDksIFF14bqtbiA0LCBI4buTIENow60gTWluaCwgVmnhu4d0IE5hbQ!5e0!3m2!1svi!2s!4v1532925802418\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));
                        txtCount.setText("Tìm thấy : " + chiNhanhQuan4.size() + " chi nhánh");
                        adapterChiNhanh = new AdapterHeThongCuaHang(hethong.this, chiNhanhQuan4);
                        listCuaHang.setAdapter(adapterChiNhanh);
                        break;
                    case 4: // Quận 5
                        List<chiNhanh> chiNhanhQuan5 = new ArrayList<>();
                        chiNhanhQuan5.add(quan5("86B Trần Phú,Phường 4,Quận 5",
                        "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1601.0920871406245!2d106.67680776719979!3d10.761829852938686!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752f1dc723217b%3A0x7e3dacf710a0b1ae!2zODZCIFRy4bqnbiBQaMO6LCBQaMaw4budbmcgNCwgUXXhuq1uIDUsIEjhu5MgQ2jDrSBNaW5oLCBWaeG7h3QgTmFt!5e0!3m2!1svi!2s!4v1532925852194\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));
                        chiNhanhQuan5.add(quan5("93-83 Nguyễn Duy Dương,phường 9,Quận 5",
                        "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.6885794383497!2d106.66926501526025!3d10.758466862465678!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752ee34eb73f2f%3A0xf98ac581aee56718!2zOTMtODMgTmd1eeG7hW4gRHV5IETGsMahbmcsIHBoxrDhu51uZyA5LCBRdeG6rW4gNSwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925919042\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));
                        txtCount.setText("Tìm thấy : " + chiNhanhQuan5.size() + " chi nhánh");
                        adapterChiNhanh = new AdapterHeThongCuaHang(hethong.this, chiNhanhQuan5);
                        listCuaHang.setAdapter(adapterChiNhanh);
                        break;
                    case 5: // Quận 6
                        List<chiNhanh> chiNhanhQuan6 = new ArrayList<>();
                        chiNhanhQuan6.add(quan6("267 Tân Hòa Đông, Phường 14, Quận 6, Hồ Chí Minh, Việt Nam", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.0758864281333!2d106.7286779652604!3d10.80549986160995!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3175261912f0bd2f%3A0x6ad7f19f19f456fa!2zNTEgUXXhu5FjIEjGsMahbmcsIFRo4bqjbyDEkGnhu4FuLCBRdeG6rW4gMiwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925463648\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));
                        chiNhanhQuan6.add(quan6("93 Kinh Dương Vương, Phường 12, Quận 6, Hồ Chí Minh, Việt Nam", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.3523236402502!2d106.7257145430864!3d10.784304425821045!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x317525fef2199ae5%3A0xb9ee14d1d232ecc6!2zQjYvMiBMxrDGoW5nIMSQ4buLbmggQ-G7p2EsIFAuIELDrG5oIEFuLCBRdeG6rW4gMiwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925531469\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));

                        txtCount.setText("Tìm thấy : " + chiNhanhQuan6.size() + " chi nhánh");
                        adapterChiNhanh = new AdapterHeThongCuaHang(hethong.this, chiNhanhQuan6);
                        listCuaHang.setAdapter(adapterChiNhanh);
                        break;
                    case 6: // Quận 7
                        List<chiNhanh> chiNhanhQuan7 = new ArrayList<>();
                        chiNhanhQuan7.add(quan7("329 Nguyễn Thị Thập,Tân Quy,Quận 7", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.0758864281333!2d106.7286779652604!3d10.80549986160995!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3175261912f0bd2f%3A0x6ad7f19f19f456fa!2zNTEgUXXhu5FjIEjGsMahbmcsIFRo4bqjbyDEkGnhu4FuLCBRdeG6rW4gMiwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925463648\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));
                        chiNhanhQuan7.add(quan7("B6/2 Lương Định Của, P. Bình An, Quận 2", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.3523236402502!2d106.7257145430864!3d10.784304425821045!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x317525fef2199ae5%3A0xb9ee14d1d232ecc6!2zQjYvMiBMxrDGoW5nIMSQ4buLbmggQ-G7p2EsIFAuIELDrG5oIEFuLCBRdeG6rW4gMiwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925531469\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));

                        txtCount.setText("Tìm thấy : " + chiNhanhQuan7.size() + " chi nhánh");
                        adapterChiNhanh = new AdapterHeThongCuaHang(hethong.this, chiNhanhQuan7);
                        listCuaHang.setAdapter(adapterChiNhanh);
                        break;
                    case 7: // Quận 8
                        List<chiNhanh> chiNhanhQuan8 = new ArrayList<>();
                        chiNhanhQuan8.add(quan8("3b Phú Định,Phường 16,Quận 8", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.0758864281333!2d106.7286779652604!3d10.80549986160995!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3175261912f0bd2f%3A0x6ad7f19f19f456fa!2zNTEgUXXhu5FjIEjGsMahbmcsIFRo4bqjbyDEkGnhu4FuLCBRdeG6rW4gMiwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925463648\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));

                        txtCount.setText("Tìm thấy : " + chiNhanhQuan8.size() + " chi nhánh");
                        adapterChiNhanh = new AdapterHeThongCuaHang(hethong.this, chiNhanhQuan8);
                        listCuaHang.setAdapter(adapterChiNhanh);
                        break;
                    case 8: // Quận 10
                        List<chiNhanh> chiNhanhQuan10 = new ArrayList<>();
                        chiNhanhQuan10.add(quan10("163 Tô Hiến Thành,Phường 13,Quận 10", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.0758864281333!2d106.7286779652604!3d10.80549986160995!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3175261912f0bd2f%3A0x6ad7f19f19f456fa!2zNTEgUXXhu5FjIEjGsMahbmcsIFRo4bqjbyDEkGnhu4FuLCBRdeG6rW4gMiwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925463648\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));
                        chiNhanhQuan10.add(quan10("324 Đường 3/2, Phường 12, Quận 10, Hồ Chí Minh, Việt Nam", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.3523236402502!2d106.7257145430864!3d10.784304425821045!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x317525fef2199ae5%3A0xb9ee14d1d232ecc6!2zQjYvMiBMxrDGoW5nIMSQ4buLbmggQ-G7p2EsIFAuIELDrG5oIEFuLCBRdeG6rW4gMiwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925531469\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));

                        txtCount.setText("Tìm thấy : " + chiNhanhQuan10.size() + " chi nhánh");
                        adapterChiNhanh = new AdapterHeThongCuaHang(hethong.this, chiNhanhQuan10);
                        listCuaHang.setAdapter(adapterChiNhanh);
                        break;
                    case 9: // Quận 11
                        List<chiNhanh> chiNhanhQuan11 = new ArrayList<>();
                        chiNhanhQuan11.add(quan11("70 Âu Cơ, Phường 9, Tân Bình, Hồ Chí Minh, Việt Nam", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.0758864281333!2d106.7286779652604!3d10.80549986160995!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3175261912f0bd2f%3A0x6ad7f19f19f456fa!2zNTEgUXXhu5FjIEjGsMahbmcsIFRo4bqjbyDEkGnhu4FuLCBRdeG6rW4gMiwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925463648\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));
                        chiNhanhQuan11.add(quan11("198 Lãnh Binh Thăng, Phường 13, Quận 11, Hồ Chí Minh, Việt Nam", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.3523236402502!2d106.7257145430864!3d10.784304425821045!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x317525fef2199ae5%3A0xb9ee14d1d232ecc6!2zQjYvMiBMxrDGoW5nIMSQ4buLbmggQ-G7p2EsIFAuIELDrG5oIEFuLCBRdeG6rW4gMiwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925531469\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));

                        txtCount.setText("Tìm thấy : " + chiNhanhQuan11.size() + " chi nhánh");
                        adapterChiNhanh = new AdapterHeThongCuaHang(hethong.this, chiNhanhQuan11);
                        listCuaHang.setAdapter(adapterChiNhanh);
                        break;
                    case 10: // Quận Gò Vấp
                        List<chiNhanh> chiNhanhQuanGoVap = new ArrayList<>();
                        chiNhanhQuanGoVap.add(quanGoVap("371 Nguyễn Kiệm, Gò Vấp, Hồ Chí Minh", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3918.935659675657!2d106.67562481526039!3d10.816235861414171!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x317528e195f816b7%3A0xfb5c0101490d8870!2zMzcxIE5ndXnhu4VuIEtp4buHbSwgUGjGsOG7nW5nIDMsIEfDsiBW4bqlcCwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532926674047\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));
                        chiNhanhQuanGoVap.add(quanGoVap("429 Phan Văn Trị, Phường 1, Gò Vấp, Hồ Chí Minh, Việt Nam", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1959.431361811714!2d106.69263290537425!3d10.82181580967611!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x317528ee048fc577%3A0xd27458a4b7908309!2zNDI5IFBoYW4gVsSDbiBUcuG7iywgUGjGsOG7nW5nIDEsIEfDsiBW4bqlcCwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532926706155\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));

                        txtCount.setText("Tìm thấy : " + chiNhanhQuanGoVap.size() + " chi nhánh");
                        adapterChiNhanh = new AdapterHeThongCuaHang(hethong.this, chiNhanhQuanGoVap);
                        listCuaHang.setAdapter(adapterChiNhanh);
                        break;
                    case 11: // Quận Bình Thạnh
                        List<chiNhanh> chiNhanhQuanBinhThanh = new ArrayList<>();
                        chiNhanhQuanBinhThanh.add(quanBinhThanh("73 Nguyễn Thượng Hiền, Phường 5, Bình Thạnh", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.0758864281333!2d106.7286779652604!3d10.80549986160995!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3175261912f0bd2f%3A0x6ad7f19f19f456fa!2zNTEgUXXhu5FjIEjGsMahbmcsIFRo4bqjbyDEkGnhu4FuLCBRdeG6rW4gMiwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925463648\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));
                        chiNhanhQuanBinhThanh.add(quanBinhThanh("29B Nơ Trang Long, Phường 7, Bình Thạnh, Hồ Chí Minh, Việt Nam", "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.3523236402502!2d106.7257145430864!3d10.784304425821045!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x317525fef2199ae5%3A0xb9ee14d1d232ecc6!2zQjYvMiBMxrDGoW5nIMSQ4buLbmggQ-G7p2EsIFAuIELDrG5oIEFuLCBRdeG6rW4gMiwgSOG7kyBDaMOtIE1pbmgsIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1532925531469\" width=\"400\" height=\"200\" frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>\n"));

                        txtCount.setText("Tìm thấy : " + chiNhanhQuanBinhThanh.size() + " chi nhánh");
                        adapterChiNhanh = new AdapterHeThongCuaHang(hethong.this, chiNhanhQuanBinhThanh);
                        listCuaHang.setAdapter(adapterChiNhanh);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private chiNhanh quan1(String diaChi, String url){
        chiNhanh chiNhanhQuan1 = new chiNhanh(diaChi, url);
        return chiNhanhQuan1;
    }

    private chiNhanh quan2(String diaChi, String url){
        chiNhanh chiNhanhQuan2 = new chiNhanh(diaChi, url);
        return chiNhanhQuan2;
    }

    private chiNhanh quan3(String diaChi, String url){
        chiNhanh chiNhanhQuan3 = new chiNhanh(diaChi, url);
        return chiNhanhQuan3;
    }

    private chiNhanh quan4(String diaChi, String url){
        chiNhanh chiNhanhQuan4 = new chiNhanh(diaChi, url);
        return chiNhanhQuan4;
    }

    private chiNhanh quan5(String diaChi, String url){
        chiNhanh chiNhanhQuan5 = new chiNhanh(diaChi, url);
        return chiNhanhQuan5;
    }

    private chiNhanh quan6(String diaChi, String url){
        chiNhanh chiNhanhQuan6 = new chiNhanh(diaChi, url);
        return chiNhanhQuan6;
    }

    private chiNhanh quan7(String diaChi, String url){
        chiNhanh chiNhanhQuan7 = new chiNhanh(diaChi, url);
        return chiNhanhQuan7;
    }

    private chiNhanh quan8(String diaChi, String url){
        chiNhanh chiNhanhQuan8 = new chiNhanh(diaChi, url);
        return chiNhanhQuan8;
    }

    private chiNhanh quan10(String diaChi, String url){
        chiNhanh chiNhanhQuan10 = new chiNhanh(diaChi, url);
        return chiNhanhQuan10;
    }

    private chiNhanh quan11(String diaChi, String url){
        chiNhanh chiNhanhQuan11 = new chiNhanh(diaChi, url);
        return chiNhanhQuan11;
    }

    private chiNhanh quanGoVap(String diaChi, String url){
        chiNhanh chiNhanhQuanGoVap = new chiNhanh(diaChi, url);
        return chiNhanhQuanGoVap;
    }

    private chiNhanh quanBinhThanh(String diaChi, String url){
        chiNhanh chiNhanhQuanBinhThanh = new chiNhanh(diaChi, url);
        return chiNhanhQuanBinhThanh;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                startActivity(new Intent(this, MainActivity.class));
                return true;
        }
        return false;
    }



    /*private String getCurrentLocate(){
        String quan = "";
        try {
            final double[] longitude = {0};
            final double[] latitude = { 0 };
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    longitude[0] = location.getLongitude();
                    latitude[0] = location.getLatitude();
                }
            };
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(latitude[0], longitude[0], 1);
            if (addresses.size() > 0){
                quan = addresses.get(0).getLocality();
            }


        }
        catch(SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quan;
    }
*/
}
