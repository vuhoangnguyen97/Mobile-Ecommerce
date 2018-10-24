package com.example.legia.mobileweb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.legia.mobileweb.DAO.sanPhamDAO;
import com.example.legia.mobileweb.DTO.sanPham;

import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class sosanh extends AppCompatActivity {
    ImageView hinhSanPhamChinh, hinhSanPhamSoSanh;
    TextView txtTenSPChinh,txtGiaSPChinh ,txtHangSPChinh, txtTinhTrangSPChinh, txtTinhNangSPChinh, txtCameraTruocSPChinh, txtCameraSauSPChinh, txtDungLuongPinSPChinh;
    TextView txtTenSPSoSanh,txtGiaSPPhu ,txtHangSPSoSanh, txtTinhTrangSPSoSanh, txtTinhNangSPSoSanh, txtCameraTruocSPSoSanh, txtCameraSauSPSoSanh, txtDungLuongPinSPSoSanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sosanh);

        this.setTitle("So sánh sản phẩm");

        // Sản phẩm chính
        hinhSanPhamChinh = findViewById(R.id.imgHinhA);
        txtTenSPChinh = findViewById(R.id.txtTenSPChinh);
        txtGiaSPChinh = findViewById(R.id.txtGiaSPChinh);
        txtCameraSauSPChinh = findViewById(R.id.txtCameraSauSPChinh);
        txtCameraTruocSPChinh = findViewById(R.id.txtCameraTruocSPChinh);
        txtHangSPChinh = findViewById(R.id.txtHangSPChinh);
        txtTinhTrangSPChinh = findViewById(R.id.txtTinhTrangSPChinh);
        txtTinhNangSPChinh = findViewById(R.id.txtChiTietSPChinh);
        txtDungLuongPinSPChinh = findViewById(R.id.txtDungLuongPinSPChinh);

        // Sản phẩm so sánh
        txtTenSPSoSanh = findViewById(R.id.txtTenSPPhu);
        hinhSanPhamSoSanh = findViewById(R.id.imgHinhB);
        txtGiaSPPhu = findViewById(R.id.txtGiaSPPhu);
        txtCameraSauSPSoSanh = findViewById(R.id.txtCameraSauSPPhu);
        txtCameraTruocSPSoSanh = findViewById(R.id.txtCameraTruocSPPhu);
        txtHangSPSoSanh = findViewById(R.id.txtHangSPPhu);
        txtTinhTrangSPSoSanh = findViewById(R.id.txtTinhTrangSPPhu);
        txtTinhNangSPSoSanh = findViewById(R.id.txtChiTietSPPhu);
        txtDungLuongPinSPSoSanh = findViewById(R.id.txtDungLuongPinSPPhu);

        final int maSanPham, maSanPhamSoSanh;

        Intent i = getIntent();

        if(i!=null){
            Bundle b = i.getBundleExtra("soSanh");
            maSanPham = b.getInt("MaSanPhamChinh");
            maSanPhamSoSanh = b.getInt("MaSanPhamSoSanh");

            sanPhamChinh(maSanPham);
            sanPhamSoSanh(maSanPhamSoSanh);

        }
        else{
            // LỖI
        }
    }
    DecimalFormat df = new DecimalFormat("###,###.##");
    private void sanPhamChinh(int maSanPham){
        try {
            sanPham sanPhamChinh = sanPhamDAO.docTheoID(maSanPham);
            Blob blobSanPhamChinh = sanPhamChinh.getHinh_dai_dien();

            int blobLength = (int) blobSanPhamChinh.length();
            byte[] blobAsBytes = blobSanPhamChinh.getBytes(1, blobLength);
            Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes,0,blobAsBytes.length);

            hinhSanPhamChinh.setImageBitmap(btm);
            txtTenSPChinh.setText(sanPhamChinh.getTenSanPham());
            txtGiaSPChinh.setText(df.format(sanPhamChinh.getGiaSanPham())+"đ");
            txtHangSPChinh.setText(sanPhamChinh.getHangSanXuat());
            txtTinhTrangSPChinh.setText(sanPhamChinh.getTinhTrang());

            txtCameraTruocSPChinh.setText(sanPhamChinh.getCamera_truoc());
            txtCameraSauSPChinh.setText(sanPhamChinh.getCamera_sau());
            txtDungLuongPinSPChinh.setText(sanPhamChinh.getDung_luong_pin());
            txtTinhNangSPChinh.setText(Html.fromHtml(sanPhamChinh.getTinh_nang()));

        }
        catch (SQLException e){

        }

    }

    private void sanPhamSoSanh(int maSanPhamSoSanh){
        try {
            sanPham sanPhamSoSanh = sanPhamDAO.docTheoID(maSanPhamSoSanh);
            Blob blobSanPhamSoSanh = sanPhamSoSanh.getHinh_dai_dien();

            int blobLength = (int) blobSanPhamSoSanh.length();
            byte[] blobAsBytes = blobSanPhamSoSanh.getBytes(1, blobLength);
            Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes,0,blobAsBytes.length);

            hinhSanPhamSoSanh.setImageBitmap(btm);
            txtTenSPSoSanh.setText(sanPhamSoSanh.getTenSanPham());
            txtGiaSPPhu.setText(df.format(sanPhamSoSanh.getGiaSanPham())+"đ");
            txtHangSPSoSanh.setText(sanPhamSoSanh.getHangSanXuat());
            txtTinhTrangSPSoSanh.setText(sanPhamSoSanh.getTinhTrang());

            txtCameraTruocSPSoSanh.setText(sanPhamSoSanh.getCamera_truoc());
            txtCameraSauSPSoSanh.setText(sanPhamSoSanh.getCamera_sau());
            txtDungLuongPinSPSoSanh.setText(sanPhamSoSanh.getDung_luong_pin());
            txtTinhNangSPSoSanh.setText(Html.fromHtml(sanPhamSoSanh.getTinh_nang()));
        }
        catch (SQLException e){

        }

    }
}
