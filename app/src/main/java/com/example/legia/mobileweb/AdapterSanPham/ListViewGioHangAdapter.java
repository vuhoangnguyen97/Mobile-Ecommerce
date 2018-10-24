package com.example.legia.mobileweb.AdapterSanPham;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.legia.mobileweb.DTO.sanPhamMua;
import com.example.legia.mobileweb.MainActivity;
import com.example.legia.mobileweb.R;

import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class ListViewGioHangAdapter extends BaseAdapter {
    private Context context;
    private List<sanPhamMua> sp;

    public ListViewGioHangAdapter(Context context, List<sanPhamMua> sp){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_cart,parent,false);
        }
        final sanPhamMua sanPhamMua = (sanPhamMua) this.getItem(position);

        ImageView img = convertView.findViewById(R.id.imgSanPhamMua);
        TextView name =  convertView.findViewById(R.id.txtTenSanPhamMua);
        TextView count =  convertView.findViewById(R.id.txtSoLuongMua);
        TextView price =  convertView.findViewById(R.id.txtGiaMotCai);
        Button delete = convertView.findViewById(R.id.btnXoa);
        DecimalFormat df = new DecimalFormat("###,###.##");

        //BIND
        try {
            name.setText(sanPhamMua.getTenSanPham());
            count.setText("Số lượng mua : " + sanPhamMua.getSoLuongMua());
            price.setText("Giá 1 cái : " + df.format(sanPhamMua.getGiaSanPham()) + " VNĐ");

            Blob b = sanPhamMua.getHinh_dai_dien();

            int blobLength = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength);
            Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes,0,blobAsBytes.length);

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length, options);
            options.inMutable = true;
            options.inBitmap = btm;
            options.inJustDecodeBounds = false;
            Bitmap bitmapTwo = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length, options);
            //imageView.setImageBitmap(bitmapTwo);


            img.setImageBitmap(bitmapTwo);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
                    gioHang.xoa(sanPhamMua.getMa_san_pham());

                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("count", gioHang.countSoLuongMua());
                    context.startActivity(i);
                }
            });


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xác thực lại thông tin cá nhân:

                    LayoutInflater li = LayoutInflater.from(context);
                    final View detail = li.inflate(R.layout.layout_chitiet_giohang, null);
                    final TextView txtTenSanPham = detail.findViewById(R.id.txtDetailProduct);
                    final TextView txtHangSanPham = detail.findViewById(R.id.txtBrandProduct);
                    final TextView txtSoLuongMua = detail.findViewById(R.id.txtSoLuongMuaChiTiet);
                    final TextView txtTotalPrice = detail.findViewById(R.id.txtTongTienUpdate);
                    final ImageView imgHinhChiTiet = detail.findViewById(R.id.imgChiTietGioHang);
                    final Button btnTang = detail.findViewById(R.id.btnTang);
                    final Button btnGiam = detail.findViewById(R.id.btnGiam);

                    Blob b = sanPhamMua.getHinh_dai_dien();

                    int blobLength = 0;
                    try {
                        blobLength = (int) b.length();
                        byte[] blobAsBytes = b.getBytes(1, blobLength);
                        Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes,0,blobAsBytes.length);
                        imgHinhChiTiet.setImageBitmap(btm );
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    final DecimalFormat df = new DecimalFormat("###,###.##");
                    themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;

                    txtTenSanPham.setText(sanPhamMua.getTenSanPham());
                    txtHangSanPham.setText(sanPhamMua.getHangSanXuat());
                    txtSoLuongMua.setText(sanPhamMua.getSoLuongMua()+"");
                    txtTotalPrice.setText(df.format(sanPhamMua.getThanhTien())+"đ");

                    btnTang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int slm = Integer.parseInt(txtSoLuongMua.getText().toString());
                            slm+=1;
                            double gia1Cai = sanPhamMua.getGiaSanPham();
                            double giaTotal = gia1Cai*slm;
                            txtSoLuongMua.setText(slm+"");
                            txtTotalPrice.setText(df.format(giaTotal)+"đ");

                        }
                    });

                    btnGiam.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int slm = Integer.parseInt(txtSoLuongMua.getText().toString());
                            slm-=1;
                            if(slm==0){
                                Toast.makeText(context, "Số lượng mua phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            double gia1Cai = sanPhamMua.getGiaSanPham();
                            double giaTotal = gia1Cai*slm;
                            txtSoLuongMua.setText(String.valueOf(slm));
                            txtTotalPrice.setText(df.format(giaTotal)+"đ");
                        }
                    });

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(detail);


                    // set dialog message
                    alertDialogBuilder
                            .setTitle("Chi tiết sản phẩm mua")
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.N)
                                        public void onClick(DialogInterface dialog, int id) {
                                            int slm = Integer.parseInt(txtSoLuongMua.getText().toString());
                                            int update = slm - sanPhamMua.getSoLuongMua();
                                            int maSanPham = sanPhamMua.getMa_san_pham();
                                            themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
                                            gioHang.them(maSanPham, update);
                                            Intent intent = new Intent(context, MainActivity.class);
                                            intent.putExtra("count", gioHang.countSoLuongMua());
                                            context.startActivity(intent);
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                }
            });

        }
        catch (SQLException e){

        }
        return convertView;
    }
}
