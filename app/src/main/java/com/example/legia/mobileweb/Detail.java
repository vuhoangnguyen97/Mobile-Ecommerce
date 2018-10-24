package com.example.legia.mobileweb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.legia.mobileweb.AdapterSanPham.BottomNavigationViewHelper;
import com.example.legia.mobileweb.AdapterSanPham.ListViewSanPhamSoSanh;
import com.example.legia.mobileweb.AdapterSanPham.SanPhamAdapterNew;
import com.example.legia.mobileweb.DAO.sanPhamDAO;
import com.example.legia.mobileweb.DAO.themVaoGioHang;
import com.example.legia.mobileweb.DTO.sanPham;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareDialog;


import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Detail extends AppCompatActivity {
    ImageView hinhSanPham;
    BottomNavigationView menuChiTiet;
    Button btnThemVaoGio, btnSoSanh;
    TextView txtTenSanPhamChiTiet, txtHangSanXuatChiTiet, txtGiaSanPhamChiTiet, txtCameraTruocChiTiet, txtCameraSauChiTiet, txtTinhTrangChiTiet;
    int maSanPham;
    LikeView likeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        FacebookSdk.setApplicationId("254499031809848");
        FacebookSdk.sdkInitialize(Detail.this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.setTitle("Xem chi tiết");
        hinhSanPham = findViewById(R.id.imgHinhSanPham);
        btnThemVaoGio = findViewById(R.id.btnMua);
        btnSoSanh = findViewById(R.id.btnSoSanh);
        menuChiTiet = findViewById(R.id.menuChiTiet);

        txtTenSanPhamChiTiet = findViewById(R.id.txtTenChiTietSanPham);
        txtHangSanXuatChiTiet = findViewById(R.id.txtHangChiTietSanPham);
        txtCameraSauChiTiet = findViewById(R.id.txtCameraSauChiTietSanPham);
        txtCameraTruocChiTiet = findViewById(R.id.txtCameraTruocChiTietSanPham);
        txtGiaSanPhamChiTiet = findViewById(R.id.txtGiaTienChiTiet);
        txtTinhTrangChiTiet = findViewById(R.id.txtTinhTrangChiTietSanPham);
        //likeButton = findViewById(R.id.)

        BottomNavigationViewHelper.disableShiftMode(menuChiTiet);
        menuChiTiet.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.general:
                        break;
                    case R.id.comment:
                        Intent intent = getIntent();
                        Bundle b = intent.getBundleExtra("SanPhamChon");
                        int id = b.getInt("MaSanPham");
                        String url = "http://localhost:8081/web-mobile/xemChiTietSanPhamServlet?id="+id;

                        Intent intent2 = new Intent(Detail.this, comment.class);
                        intent2.putExtra("url", url);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);


                        break;
                }
                return false;

            }
        });

        // Thêm mục options
        btnSoSanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Spinner cboHang, cboGia;
                final ListView listDanhSachSoSanh;

                LayoutInflater li = LayoutInflater.from(Detail.this);
                final View viewSoSanh = li.inflate(R.layout.layout_sosanh, null);

                cboHang = viewSoSanh.findViewById(R.id.spinnerHang);
                cboGia = viewSoSanh.findViewById(R.id.spinnerGia);
                listDanhSachSoSanh = viewSoSanh.findViewById(R.id.listDanhSachSoSanh);

                List<String> dsGiaSpinner = new ArrayList<>();
                dsGiaSpinner.add("--Chọn mức giá--");
                dsGiaSpinner.add("Dưới 1 Triệu");
                dsGiaSpinner.add("1 - 3 Triệu");
                dsGiaSpinner.add("3 - 7 Triệu");
                dsGiaSpinner.add("7 - 10 Triệu");
                dsGiaSpinner.add("Trên 10 Triệu");

                List<String> dsHangSpinner = new ArrayList<>();
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
                ArrayAdapter<String> giaSpinnerArrayAdapter = new ArrayAdapter<>(Detail.this, android.R.layout.simple_spinner_item, dsGiaSpinner);

                // Drop down layout style - list view with radio button
                giaSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Creating adapter for spinner
                ArrayAdapter<String> hangSpinnerArrayAdapter = new ArrayAdapter<>(Detail.this, android.R.layout.simple_spinner_item, dsHangSpinner);

                // Drop down layout style - list view with radio button
                hangSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                cboGia.setAdapter(giaSpinnerArrayAdapter);
                cboHang.setAdapter(hangSpinnerArrayAdapter);

                Intent intent = getIntent();
                Bundle bundle = intent.getBundleExtra("SanPhamChon");
                maSanPham = bundle.getInt("MaSanPham");

                sanPham sanPham = DocChiTiet(maSanPham);
                String hang_default = sanPham.getHangSanXuat();

                int position = dsHangSpinner.indexOf(hang_default);
                cboHang.setSelection(position);

                // Spinner giá sản phẩm
                cboGia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String hangSP = cboHang.getSelectedItem().toString();
                        ListViewSanPhamSoSanh adapterSoSanh;

                        switch (position){
                            case 0:
                                List<sanPham> listSanPhamHang = sanPhamDAO.timKiemTheoHang(hangSP);
                                adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, listSanPhamHang, maSanPham);
                                listDanhSachSoSanh.setAdapter(adapterSoSanh);
                                break;
                            case 1: // Dưới 1 Triệu
                                List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, listDuoi1Trieu, maSanPham);
                                listDanhSachSoSanh.setAdapter(adapterSoSanh);
                                break;
                            case 2: // 1 - 3 Triệu
                                List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list1Den3Trieu, maSanPham);
                                listDanhSachSoSanh.setAdapter(adapterSoSanh);
                                break;
                            case 3: // 3- 7 Triệu
                                List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list3Den7Trieu, maSanPham);
                                listDanhSachSoSanh.setAdapter(adapterSoSanh);
                                break;
                            case 4: // 7 - 10 Triệu
                                List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list7Den10Trieu, maSanPham);
                                listDanhSachSoSanh.setAdapter(adapterSoSanh);
                                break;
                            case 5: // Trên 10 Triệu
                                List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, listTren10Trieu, maSanPham);
                                listDanhSachSoSanh.setAdapter(adapterSoSanh);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                // Spinner hãng sản phẩm
                cboHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String mucGia = cboGia.getSelectedItem().toString();
                        String hangSP = cboHang.getSelectedItem().toString();
                        ListViewSanPhamSoSanh adapterSoSanh;

                        switch (position){
                            case 0: // Apple
                                switch (mucGia){
                                    case "Dưới 1 Triệu":
                                        List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, listDuoi1Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "1 - 3 Triệu":
                                        List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list1Den3Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "3 - 7 Triệu":
                                        List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list3Den7Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "7 - 10 Triệu":
                                        List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list7Den10Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                         break;
                                    case "Trên 10 Triệu":
                                        List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, listTren10Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);
                                        break;
                                }
                                break;
                            case 1:
                                switch (mucGia){
                                    case "Dưới 1 Triệu":
                                        List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, listDuoi1Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "1 - 3 Triệu":
                                        List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list1Den3Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "3 - 7 Triệu":
                                        List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list3Den7Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "7 - 10 Triệu":
                                        List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list7Den10Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                         break;
                                    case "Trên 10 Triệu":
                                        List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, listTren10Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);
                                        break;
                                }
                                break;
                            case 2:
                                switch (mucGia){
                                    case "Dưới 1 Triệu":
                                        List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, listDuoi1Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "1 - 3 Triệu":
                                        List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list1Den3Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "3 - 7 Triệu":
                                        List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list3Den7Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "7 - 10 Triệu":
                                        List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list7Den10Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                         break;
                                    case "Trên 10 Triệu":
                                        List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, listTren10Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);
                                        break;
                                }
                                break;
                            case 3:
                                switch (mucGia){
                                    case "Dưới 1 Triệu":
                                        List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, listDuoi1Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "1 - 3 Triệu":
                                        List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list1Den3Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "3 - 7 Triệu":
                                        List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list3Den7Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "7 - 10 Triệu":
                                        List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list7Den10Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                         break;
                                    case "Trên 10 Triệu":
                                        List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, listTren10Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);
                                        break;
                                }
                                break;
                            case 4:
                                switch (mucGia){
                                    case "Dưới 1 Triệu":
                                        List<sanPham> listDuoi1Trieu = sanPhamDAO.timTheoHangGiaDuoi1Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, listDuoi1Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "1 - 3 Triệu":
                                        List<sanPham> list1Den3Trieu = sanPhamDAO.timTheoHangGia1Den3Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list1Den3Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "3 - 7 Triệu":
                                        List<sanPham> list3Den7Trieu = sanPhamDAO.timTheoHangGia3Den7rieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list3Den7Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                        break;
                                    case "7 - 10 Triệu":
                                        List<sanPham> list7Den10Trieu = sanPhamDAO.timTheoHangGia7Den10rieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, list7Den10Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);                                         break;
                                    case "Trên 10 Triệu":
                                        List<sanPham> listTren10Trieu = sanPhamDAO.timTheoHangGiaTren10Trieu(hangSP);
                                        adapterSoSanh = new ListViewSanPhamSoSanh(Detail.this, listTren10Trieu, maSanPham);
                                        listDanhSachSoSanh.setAdapter(adapterSoSanh);
                                        break;
                                }
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);

                // Set title value.
                builder.setTitle("Chọn sản phẩm cần so sánh");
                builder.setView(viewSoSanh);



                AlertDialog dialog=builder.create();

                dialog.show();

            }
        });

        btnThemVaoGio.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
                gioHang.them(maSanPham, 1);
                Intent intent = new Intent(Detail.this, MainActivity.class);
                intent.putExtra("count", gioHang.countSoLuongMua());
                startActivity(intent);

            }
        });

        GetBundle();
    }

    private void GetBundle(){
        Intent i = getIntent();

        if(i!=null){
            try{
                Bundle b = i.getBundleExtra("SanPhamChon");
                maSanPham = b.getInt("MaSanPham");

                sanPham sanPham = DocChiTiet(maSanPham);

                Blob blob = sanPham.getHinh_dai_dien();

                int blobLength = (int) blob.length();
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes,0,blobAsBytes.length);

                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length, options);
                options.inMutable = true;
                options.inBitmap = btm;
                options.inJustDecodeBounds = false;
                Bitmap bitmapTwo = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length, options);
                //imageView.setImageBitmap(bitmapTwo);


                hinhSanPham.setImageBitmap(bitmapTwo);
                DecimalFormat df = new DecimalFormat("###,###.##");
                txtGiaSanPhamChiTiet.setText(df.format(sanPham.getGiaSanPham())+"đ");
                txtHangSanXuatChiTiet.setText(sanPham.getHangSanXuat());
                txtTinhTrangChiTiet.setText(sanPham.getTinhTrang());
                txtCameraTruocChiTiet.setText(sanPham.getCamera_truoc());
                txtCameraSauChiTiet.setText(sanPham.getCamera_sau());
                txtTenSanPhamChiTiet.setText(sanPham.getTenSanPham());
            }
            catch (SQLException e){

            }

        }
        else{

        }
    }

    private sanPham DocChiTiet(int ma_san_pham){
        sanPham sp = sanPhamDAO.docTheoID(ma_san_pham);
        return sp;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart, menu);
        Intent i = getIntent();

        if(i!=null) {
            Bundle b = i.getBundleExtra("SanPhamChon");
            if (b != null) {

            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:
                ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                        .setQuote("Chia sẻ")
                        .build();
                ShareDialog.show(this,shareLinkContent);
                break;
            case android.R.id.home:
                this.finish();
                return true;
        }
        return true;
    }
}
