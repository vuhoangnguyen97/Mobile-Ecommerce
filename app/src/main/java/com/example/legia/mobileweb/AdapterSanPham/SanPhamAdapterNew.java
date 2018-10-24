package com.example.legia.mobileweb.AdapterSanPham;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legia.mobileweb.DAO.sanPhamDAO;
import com.example.legia.mobileweb.DAO.themVaoGioHang;
import com.example.legia.mobileweb.DTO.sanPham;
import com.example.legia.mobileweb.Detail;
import com.example.legia.mobileweb.MainActivity;
import com.example.legia.mobileweb.R;

import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

public class SanPhamAdapterNew extends RecyclerView.Adapter<SanPhamAdapterNew.SanPhamViewHolder> {
    private List<sanPham> listSanPham;
    private Context context;
    private RecyclerView recyclerView;
    public static themVaoGioHang gioHang = new themVaoGioHang();

    public SanPhamAdapterNew(Context context,List<sanPham> listSanPham, RecyclerView recyclerView) {
        this.context = context;
        this.listSanPham = listSanPham;
        this.recyclerView = recyclerView;
    }

    @Override
    public SanPhamAdapterNew.SanPhamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_san_pham, parent, false);
        SanPhamAdapterNew.SanPhamViewHolder holder = new SanPhamAdapterNew.SanPhamViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, final int position) {
        if(holder instanceof SanPhamViewHolder){
            try {

                Blob b = listSanPham.get(position).getHinh_dai_dien();

                int blobLength = (int) b.length();
                byte[] blobAsBytes = b.getBytes(1, blobLength);


                Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes,0,blobAsBytes.length);

                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                options.inScaled = true;
                BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length, options);
                options.inMutable = true;
                options.inBitmap = btm;
                options.inJustDecodeBounds = false;
                Bitmap bitmapTwo = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length, options);
                //imageView.setImageBitmap(bitmapTwo);

                DecimalFormat df = new DecimalFormat("###,###.##");
                holder.imgSanPham.setImageBitmap(bitmapTwo);
                holder.name.setText(listSanPham.get(position).getTenSanPham());
                holder.price.setText(df.format(listSanPham.get(position).getGiaSanPham())+"đ");


                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        LayoutInflater li = LayoutInflater.from(context);
                        final View view3DTouch = li.inflate(R.layout.layout_infosp, null);

                        ImageView imgHinhInfo = view3DTouch.findViewById(R.id.imgInfo);
                        TextView txtTenSanPhamInfo = view3DTouch.findViewById(R.id.txtTenSanPhamInfo);
                        TextView txtHangInfo = view3DTouch.findViewById(R.id.txtHangInfo);
                        TextView txtGiaInfo = view3DTouch.findViewById(R.id.txtGiaInfo);

                        int maSP = listSanPham.get(position).getMa_san_pham();
                        sanPham sanPham = sanPhamDAO.docTheoID(maSP);

                        Blob b = sanPham.getHinh_dai_dien();

                        int blobLength = 0;
                        try {
                            blobLength = (int) b.length();
                            byte[] blobAsBytes = b.getBytes(1, blobLength);
                            Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes,0,blobAsBytes.length);
                            imgHinhInfo.setImageBitmap(btm);
                            DecimalFormat df = new DecimalFormat("###,###.##");
                            txtGiaInfo.setText("Giá: " + df.format(sanPham.getGiaSanPham())+"VNĐ");
                            txtHangInfo.setText("Hãng: " + sanPham.getHangSanXuat());
                            txtTenSanPhamInfo.setText(sanPham.getTenSanPham());

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);


                        builder.setView(view3DTouch);
                        builder.setPositiveButton("Quay về", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });



                        android.support.v7.app.AlertDialog dialog=builder.create();
                        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                        dialog.show();


                        return true;
                    }
                });




                /*Sự kiện click vào item*/
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int maSanPham = listSanPham.get(position).getMa_san_pham();
                        int soLuongMua = gioHang.countSoLuongMua();

                        Intent i = new Intent(context, Detail.class);
                        Bundle b = new Bundle();

                        b.putInt("MaSanPham", maSanPham);
                        b.putInt("soLuongMua", soLuongMua);
                        i.putExtra("SanPhamChon", b);

                        context.startActivity(i);
                    }

                });



                holder.btnMua.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        int maSanPhamMua, soLuongMua = 1;
                        maSanPhamMua = listSanPham.get(position).getMa_san_pham();

                        gioHang.them(maSanPhamMua, soLuongMua);
                        BottomNavigationView menuBar = ((Activity) context).findViewById(R.id.menuBar);
                        BottomNavigationMenuView bottomNavigationMenuView =
                                (BottomNavigationMenuView) menuBar.getChildAt(0);
                        View view = bottomNavigationMenuView.getChildAt(3); // number of menu from left
                        if(gioHang.countSoLuongMua() > 0){
                            new QBadgeView(context).bindTarget(view).setShowShadow(false).setBadgeNumber(gioHang.countSoLuongMua());
                        }
                    }


                });
            }
            catch (SQLException e){

            }
        }
        else{
            Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public int getItemCount() {
        return listSanPham.size();
    }

    public class SanPhamViewHolder extends  RecyclerView.ViewHolder {
        ImageView imgSanPham;
        TextView name;
        TextView price;
        Button btnMua;

        public SanPhamViewHolder(View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            name = itemView.findViewById(R.id.txtTenSanPham);
            price = itemView.findViewById(R.id.txtGiaSanPham);
            btnMua = itemView.findViewById(R.id.btnMua);
        }
    }


}
