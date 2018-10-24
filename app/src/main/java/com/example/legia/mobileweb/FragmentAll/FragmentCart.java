package com.example.legia.mobileweb.FragmentAll;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.legia.mobileweb.AdapterHeThong.AdapterOptionPayment;
import com.example.legia.mobileweb.AdapterSanPham.ListViewGioHangAdapter;
import com.example.legia.mobileweb.AdapterSanPham.SanPhamAdapterNew;
import com.example.legia.mobileweb.DAO.themVaoGioHang;
import com.example.legia.mobileweb.DAO.userDAO;
import com.example.legia.mobileweb.DTO.payment;
import com.example.legia.mobileweb.DTO.sanPhamMua;
import com.example.legia.mobileweb.R;
import com.example.legia.mobileweb.TyGia.DocTyGia;
import com.paypal.android.sdk.payments.PayPalConfiguration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FragmentCart extends Fragment {
    ListView dsGioHang;
    ImageButton btnPay;
    Button btnOption;
    themVaoGioHang gioHang;
    TextView totalMoney;


    String paypalClientid = "AUto2kIFoFUBohXTAbmnQICEOAPxW3MZGCilm3LV9A6Yd9JUN-Gd2m_p0kWZTVlsKiE0b3N4N0wAt7Uw";
    int paypalCode = 999;
    PayPalConfiguration configure;
    Intent m_service;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    String diaChi, quan, phuong, soDienThoaiMaHoa = "";
    int soDienThoai = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_giohang, container, false);
        dsGioHang = view.findViewById(R.id.listCart);
        totalMoney = view.findViewById(R.id.lbTotal);
        btnOption = view.findViewById(R.id.btnOption);
        btnOption.setText(Html.fromHtml("<html>Thanh Toán \t&#x2794;</html>"));
        DecimalFormat df = new DecimalFormat("###,###.##");

        gioHang = SanPhamAdapterNew.gioHang;

        final SharedPreferences sharedPreferences = container.getContext().getSharedPreferences("userLogin", Context.MODE_PRIVATE);

        int iduser = sharedPreferences.getInt("idUser", 0);
        int loaiThe =0;
        double tongTien = gioHang.tongTien();
        double tongTienGiamGia = 0;
        loaiThe = userDAO.layLoaiTheUser(iduser);
        switch (loaiThe){
            case 1: // Copper, normal
                Log.i("test", "thẻ đồng");
                tongTien = tongTien;
                break;
            case 2: // Silver, discount 2%
                Log.i("test", "thẻ bạc");
                tongTienGiamGia = tongTien - tongTien*0.02;
                break;
            case 3: // Gold, discount 5%
                Log.i("test", "thẻ vàng");
                tongTienGiamGia = tongTien - tongTien*0.05;
                /*totalMoney.setText(Html.fromHtml(
                        "<html>Tổng tiền: " + df.format(tongTien) +"</strike><br/>"+
                                "Tiền giảm giá: "+ df.format(tongTienGiamGia) +
                                "</html>"
                ));*/
                break;
            case 4: // Plantium, discount 7%
                Log.i("test", "thẻ bạch kim");
                tongTienGiamGia = tongTien - tongTien*0.07;
                break;
            case 5: // Diamond, discount 10%
                Log.i("test", "thẻ kim cương");
                tongTienGiamGia = tongTien - tongTien*0.1;
                break;

        }

        //
        totalMoney.setText("Tổng tiền : "+ df.format(tongTien)+" VNĐ");

        Log.i("test", DocTyGia.giaBan()+"");

        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // option
                if(gioHang.countSoLuongMua()>=1){
                    List<payment> dsPayment = new ArrayList<>();
                    dsPayment.add(new payment(1, R.drawable.paypallogo));
                    dsPayment.add(new payment(2, R.drawable.onepaylogo));
                    dsPayment.add(new payment(6, R.drawable.momo));
                    dsPayment.add(new payment(4, R.drawable.cod));
                    dsPayment.add(new payment(5, R.drawable.vtc));
                    dsPayment.add(new payment(7, R.drawable.bitcoin));

                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(container.getContext());

                    // Set title value.
                    builder.setTitle("Chọn hình thức thanh toán: ");

                    AdapterOptionPayment adapterOptionPayment = new AdapterOptionPayment(container.getContext(), dsPayment);
                    ListView lv = new ListView(container.getContext());

                    lv.setAdapter(adapterOptionPayment);

                    builder.setView(lv);
                    android.support.v7.app.AlertDialog dialog=builder.create();

                    dialog.show();

                }
                else{
                    android.support.v7.app.AlertDialog.Builder dlgAlert  = new android.support.v7.app.AlertDialog.Builder(container.getContext());
                    dlgAlert.setMessage("Bạn chưa mua sản phẩm nào, vui lòng chọn sản phẩm !");
                    dlgAlert.setTitle("Giỏ hàng của bạn trống!");
                    dlgAlert.setPositiveButton("Quay lại", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }


            }
        });

        List<sanPhamMua> dsSanPhamMua = gioHang.danhSachSanPhamMua();
        ListViewGioHangAdapter gioHangAdapter = new ListViewGioHangAdapter(container.getContext(), dsSanPhamMua);
        dsGioHang.setAdapter(gioHangAdapter);

        return view;
    }
}
