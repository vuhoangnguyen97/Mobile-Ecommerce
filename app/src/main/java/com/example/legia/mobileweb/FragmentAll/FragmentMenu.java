package com.example.legia.mobileweb.FragmentAll;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.legia.mobileweb.AdapterHeThong.AdapterMenuMain;
import com.example.legia.mobileweb.AdapterSanPham.ListGopY;
import com.example.legia.mobileweb.DAO.userDAO;
import com.example.legia.mobileweb.DTO.User;
import com.example.legia.mobileweb.DTO.gopY;
import com.example.legia.mobileweb.DTO.menuMain;
import com.example.legia.mobileweb.R;
import com.example.legia.mobileweb.Shaker.shaker;
import com.facebook.Profile;

import java.util.ArrayList;
import java.util.List;

public class FragmentMenu extends Fragment {
    ListView listMenuMain;
    FloatingActionButton btnCall;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_menu, container, false);
        btnCall = view.findViewById(R.id.btnCall);
        listMenuMain = view.findViewById(R.id.listMenuMain);

        final SharedPreferences sp = container.getContext().getSharedPreferences("userLogin", Context.MODE_PRIVATE);

        int id_user = sp.getInt("idUser", 0);
        User u = userDAO.readInfo(id_user);


        List<menuMain> listMenu = new ArrayList<>();
        SharedPreferences isLoginFacebook = container.getContext().getSharedPreferences("loginWithFb", Context.MODE_PRIVATE);
        SharedPreferences isLoginGoogle = container.getContext().getSharedPreferences("loginWithGoogle", Context.MODE_PRIVATE);

        if(isLoginFacebook.getBoolean("isLoginWithFB", false)){
            Profile profile = Profile.getCurrentProfile();
            listMenu.add(new menuMain(13,"Chào bạn, " + profile.getName()));
        }else if(isLoginGoogle.getBoolean("isLoginWithGoogle", false)){
            listMenu.add(new menuMain(14,"Chào bạn, " + isLoginGoogle.getString("name", null)));

        }
        else if(sp.getBoolean("isLogin", false)){
            listMenu.add(new menuMain(8,"Chào bạn, " + sp.getString("username", null)));
        }
        listMenu.add(new menuMain(15, "Đặt hàng sản phẩm mới"));
        listMenu.add(new menuMain(3,"Hệ thống cửa hàng"));
        listMenu.add(new menuMain(4,"Thẻ tích điểm"));
        listMenu.add(new menuMain(5,"Chính sách hậu mãi"));
        listMenu.add(new menuMain(9, "Quét mã QR Code, Barcode"));
        listMenu.add(new menuMain(10, "Ủng hộ chúng tôi"));
        listMenu.add(new menuMain(2,"Góp ý"));
        // If login with facebook
        SharedPreferences loginWithFB = container.getContext().getSharedPreferences("loginWithFb", Context.MODE_PRIVATE);
        SharedPreferences loginWithGoogle = container.getContext().getSharedPreferences("loginWithGoogle", Context.MODE_PRIVATE);
        if(loginWithGoogle.getBoolean("isLoginWithGoogle", false)){
            listMenu.add(new menuMain(12, "Đăng xuất khỏi Google"));
        }
        else if(loginWithFB.getBoolean("isLoginWithFB", false)){
            // Is login with facebook
            listMenu.add(new menuMain(11, "Đăng xuất khỏi Facebook"));
        }
        else{
            if(sp.getBoolean("isLogin", false)){
                listMenu.add(new menuMain(7,"Đăng xuất"));
            }
            else{
                listMenu.add(new menuMain(6,"Đăng nhập"));
            }
        }


        AdapterMenuMain adapterMenuMain = new AdapterMenuMain(container.getContext(), listMenu);
        listMenuMain.setAdapter(adapterMenuMain);

        shaker shaker = new shaker(btnCall, -15, 15, Color.GRAY , Color.WHITE);
        shaker.shake();


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<gopY> danhSachGopY = new ArrayList<>();
                danhSachGopY.add(new gopY(1, "Facebook Messenger", R.drawable.fb_chat));
                danhSachGopY.add(new gopY(2, "Gọi cho chúng tôi", R.drawable.call));
                danhSachGopY.add(new gopY(3, "Nhắn tin qua SMS", R.drawable.sms));
                danhSachGopY.add(new gopY(4, "Liên hệ qua Viber", R.drawable.viber));

                AlertDialog.Builder builder = new AlertDialog.Builder(container.getContext());

                // Set title
                builder.setTitle("Chọn hình thức tương tác");

                ListGopY adapterGopY = new ListGopY(container.getContext(), danhSachGopY);
                ListView lv = new ListView(container.getContext());

                lv.setAdapter(adapterGopY);

                builder.setView(lv);
                AlertDialog dialog=builder.create();

                dialog.show();
            }
        });

        return view;
    }


}
