package com.example.legia.mobileweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.legia.mobileweb.AdapterSanPham.GridViewAdapterSanPham;
import com.example.legia.mobileweb.DAO.sanPhamDAO;
import com.example.legia.mobileweb.DTO.sanPham;

import java.util.ArrayList;
import java.util.List;

public class gridProduct extends AppCompatActivity {
    GridView gridProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_product);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        gridProduct = findViewById(R.id.gridProduct);

        GetIntent();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:

                this.finish();
                return true;


        }
        return false;
    }

    private void GetIntent(){
        Intent i = getIntent();

        if(i!=null){
            try{
                Bundle b = i.getBundleExtra("chiTiet");
                String brand = b.getString("brand");
                String type = b.getString("type");

                if(type.equals("brandName")){
                    this.setTitle(brand);

                    List<sanPham> dsChiTiet = sanPhamDAO.timKiemTheoHang(brand);
                    GridViewAdapterSanPham adapterSanPham = new GridViewAdapterSanPham(this, dsChiTiet);
                    gridProduct.setAdapter(adapterSanPham);
                }
                else if(type.equals("sorter")){
                    List<sanPham> dsSorter;
                    GridViewAdapterSanPham adapterSanPham;
                    this.setTitle(brand);
                    switch (brand){
                        case "Dưới 1 Triệu":
                            dsSorter = danhSachDuoi1Trieu();
                            adapterSanPham = new GridViewAdapterSanPham(this, dsSorter);
                            gridProduct.setAdapter(adapterSanPham);
                            break;
                        case "1 Đến 3 Triệu":
                            dsSorter = danhSach1Den3Trieu();
                            adapterSanPham = new GridViewAdapterSanPham(this, dsSorter);
                            gridProduct.setAdapter(adapterSanPham);
                            break;
                        case "3 Đến 7 Triệu":
                            dsSorter = danhSach3Den7Trieu();
                            adapterSanPham = new GridViewAdapterSanPham(this, dsSorter);
                            gridProduct.setAdapter(adapterSanPham);
                            break;
                        case "Cao cấp":
                            dsSorter = danhSachCaoCap();
                            adapterSanPham = new GridViewAdapterSanPham(this, dsSorter);
                            gridProduct.setAdapter(adapterSanPham);
                            break;
                    }
                }

            }
            catch (Exception e){

            }

        }
        else{

        }
    }

    private List<sanPham> danhSachDuoi1Trieu(){
        List<sanPham> ds = sanPhamDAO.timTheoGiaDuoi1Trieu();
        return  ds;
    }

    private List<sanPham> danhSach1Den3Trieu(){
        List<sanPham> ds = sanPhamDAO.timTheoGia1TrieuDen3Trieu();
        return  ds;
    }

    private List<sanPham> danhSach3Den7Trieu(){
        List<sanPham> ds = sanPhamDAO.timTheoGia3TrieuDen7Trieu();
        return  ds;
    }

    private List<sanPham> danhSachCaoCap(){
        List<sanPham> ds = sanPhamDAO.timTheoGiaTren10Trieu();
        return  ds;
    }
}
