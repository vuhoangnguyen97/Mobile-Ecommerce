package com.example.legia.mobileweb.AdapterSanPham;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.legia.mobileweb.DTO.sanPham;
import com.example.legia.mobileweb.R;
import com.example.legia.mobileweb.sosanh;

import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class ListViewSanPhamSoSanh extends BaseAdapter {

    private Context context;
    private List<sanPham> sp;
    private int ma_san_pham;

    public ListViewSanPhamSoSanh(Context context, List<sanPham> sp, int ma_san_pham){
        this.context = context;
        this.sp = sp;
        this.ma_san_pham = ma_san_pham;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_danh_sach_so_sanh,parent,false);
        }
        final sanPham sanPham = (sanPham) this.getItem(position);

        ImageView img = convertView.findViewById(R.id.imgSanPhamCanSoSanh);
        TextView name =  convertView.findViewById(R.id.txtTenSanPhamSoSanh);
        TextView brand =  convertView.findViewById(R.id.txtHangSanPhamSoSanh);
        TextView price =  convertView.findViewById(R.id.txtGiaSanPhamSoSanh);

        DecimalFormat df = new DecimalFormat("###,###.##");

        //BIND
        try {
            name.setText(sanPham.getTenSanPham());
            brand.setText("Hãng : " + sanPham.getHangSanXuat());
            price.setText("Giá : " + df.format(sanPham.getGiaSanPham()) + " VNĐ");
            Blob b = sanPham.getHinh_dai_dien();

            int blobLength = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength);
            Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes,0,blobAsBytes.length);
            final BitmapFactory.Options options = new BitmapFactory.Options();

            BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length, options);
            options.inMutable = true;
            options.inBitmap = btm;
            options.inJustDecodeBounds = false;
            Bitmap bitmapTwo = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length, options);

            img.setImageBitmap(bitmapTwo);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // chọn spham so sánh, chuyển activity sang sosanh
                    int maSanPhamSoSanh = sanPham.getMa_san_pham();
                    int maSanPham = ma_san_pham;
                    Log.i("test", "Mã ban đầu: " + maSanPham + " Mã so sánh: " + maSanPhamSoSanh);

                    Intent i = new Intent(context, sosanh.class);
                    Bundle b = new Bundle();

                    b.putInt("MaSanPhamChinh", maSanPham);
                    b.putInt("MaSanPhamSoSanh", maSanPhamSoSanh);
                    i.putExtra("soSanh", b);

                    context.startActivity(i);

                }
            });

        }
        catch (SQLException e){

        }
        return convertView;
    }
}
