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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.legia.mobileweb.DAO.sanPhamDAO;
import com.example.legia.mobileweb.DAO.themVaoGioHang;
import com.example.legia.mobileweb.DTO.sanPham;
import com.example.legia.mobileweb.Detail;
import com.example.legia.mobileweb.R;
import com.example.legia.mobileweb.sosanh;

import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class ListViewSanPhamTimKiem extends BaseAdapter implements Filterable {
    private Context context;
    private List<sanPham> dsSanPhamTimKiem, dsTemp;

    public ListViewSanPhamTimKiem(Context context, List<sanPham> dsSanPhamTimKiem) {
        this.context = context;
        this.dsSanPhamTimKiem = dsSanPhamTimKiem;
    }

    @Override
    public int getCount() {
        return dsSanPhamTimKiem.size();
    }

    @Override
    public Object getItem(int position) {
        return dsSanPhamTimKiem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_timkiem,parent,false);
        }

        final sanPham sanPham = (sanPham) this.getItem(position);
        DecimalFormat df = new DecimalFormat("###,###.##");

        TextView txtTenSanPhamTimKiem = convertView.findViewById(R.id.txtTenSanPhamTimKiem);
        TextView txtGiaSanPhamTimKiem = convertView.findViewById(R.id.txtGiaSanPhamTimKiem);
        ImageView imgSanPhamHinhTimKiem = convertView.findViewById(R.id.imgHinhSPTimKiem);

        //BIND
        try {
            txtTenSanPhamTimKiem.setText(sanPham.getTenSanPham());
            //brand.setText("Hãng : " + sanPham.getHangSanXuat());
            txtGiaSanPhamTimKiem.setText("Giá : " + df.format(sanPham.getGiaSanPham()) + " VNĐ");
            Blob b = sanPham.getHinh_dai_dien();

            int blobLength = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength);
            Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes,0,blobAsBytes.length);
            imgSanPhamHinhTimKiem.setImageBitmap(btm );
            final themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // chọn spham so sánh, chuyển activity sang sosanh
                    int maSanPham = sanPham.getMa_san_pham();
                    int soLuongMua = gioHang.countSoLuongMua();

                    Intent i = new Intent(context, Detail.class);
                    Bundle b = new Bundle();

                    b.putInt("MaSanPham", maSanPham);
                    b.putInt("soLuongMua", soLuongMua);
                    i.putExtra("SanPhamChon", b);

                    context.startActivity(i);

                }
            });

        }
        catch (SQLException e){

        }


        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }
}
