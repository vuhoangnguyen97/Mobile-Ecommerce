package com.example.legia.mobileweb.AdapterSanPham;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legia.mobileweb.DAO.themVaoGioHang;
import com.example.legia.mobileweb.DTO.sanPham;
import com.example.legia.mobileweb.R;

import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class GridViewAdapterSanPham extends BaseAdapter {

    private Context context;
    private List<sanPham> sp;


    public GridViewAdapterSanPham(Context context, List<sanPham> sp){
        this.context = context;
        this.sp = sp;
    }

    @Override
    public int getCount() {
        return sp.size();
    }

    @Override
    public Object getItem(int position) {
        return sp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_san_pham,parent,false);
        }

        final sanPham SanPham = (sanPham) this.getItem(position);

        ImageView img = (ImageView) convertView.findViewById(R.id.imgSanPham);
        TextView price = (TextView) convertView.findViewById(R.id.txtGiaSanPham);
        TextView name = (TextView) convertView.findViewById(R.id.txtTenSanPham);
        Button btnMua = convertView.findViewById(R.id.btnMua);

        //BIND
        try {
            DecimalFormat df = new DecimalFormat("###,###.##");
            // a
            price.setText(df.format(SanPham.getGiaSanPham())+"đ");
            name.setText(SanPham.getTenSanPham());

            Blob b = SanPham.getHinh_dai_dien();

            int blobLength = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength);
            Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes,0,blobAsBytes.length);
            img.setImageBitmap(btm );



            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, SanPham.getTenSanPham(), Toast.LENGTH_SHORT).show();
                }
            });

            btnMua.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    int id = SanPham.getMa_san_pham();
                    themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
                    gioHang.them(id, 1);
//                    context.startActivity(new Intent(context, cart.class));
                }
            });

        }
        catch (SQLException e){

        }
        return convertView;
    }
}
