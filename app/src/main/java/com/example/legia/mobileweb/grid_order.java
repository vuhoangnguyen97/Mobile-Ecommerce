package com.example.legia.mobileweb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.legia.mobileweb.AdapterHeThong.AdapterGridPreOrder;
import com.example.legia.mobileweb.DAO.sanPhamPreOrderDAO;
import com.example.legia.mobileweb.DTO.sanPhamPreOrder;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

public class grid_order extends AppCompatActivity {
    GridView gridProductOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_order);
        this.setTitle("Pre-Order");

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        gridProductOrder = findViewById(R.id.gridProductOrder);
        List<sanPhamPreOrder> dsSanPhamPreOrder = sanPhamPreOrderDAO.dsSanPhamPreOrder();
        AdapterGridPreOrder adapterGridPreOrder = new AdapterGridPreOrder(this, dsSanPhamPreOrder);

        gridProductOrder.setAdapter(adapterGridPreOrder);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return true;
    }
}
