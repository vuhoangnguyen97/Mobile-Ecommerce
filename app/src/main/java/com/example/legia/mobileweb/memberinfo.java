package com.example.legia.mobileweb;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.legia.mobileweb.AdapterHeThong.AdapterLichSuMua;
import com.example.legia.mobileweb.DAO.hoaDonDAO;
import com.example.legia.mobileweb.DAO.userDAO;
import com.example.legia.mobileweb.DTO.User;
import com.example.legia.mobileweb.DTO.hoaDon;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;

import java.util.List;

public class memberinfo extends AppCompatActivity {
    TextView txtTenUser, txtDiem, txtLoaiThe;
    StateProgressBar progressBarDiem;
    ListView listLichSuMuaHang;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberinfo);
        this.setTitle("Thông tin tài khoản");
        int max = 1000;

        txtTenUser = findViewById(R.id.txtTenKhachHang);
        txtDiem = findViewById(R.id.txtDiemUser);
        txtLoaiThe = findViewById(R.id.txtLoaiThe);
        progressBarDiem = findViewById(R.id.progressTichDiem);
        listLichSuMuaHang = findViewById(R.id.listLichSu);

        SharedPreferences sp = getSharedPreferences("userLogin", MODE_PRIVATE);
        int iduser = sp.getInt("idUser", 0);

        txtTenUser.setText("Tên : " + sp.getString("username", ""));
        User u = userDAO.readInfo(iduser);
        txtDiem.setText(u.getDiem()+"");
        txtLoaiThe.setText(u.getLoai_the());

        List<hoaDon> dsHoaDon = hoaDonDAO.readBill(iduser);
        AdapterLichSuMua adapterLichSuMua = new AdapterLichSuMua(this, dsHoaDon);
        listLichSuMuaHang.setAdapter(adapterLichSuMua);

        String[] descriptionData = {"Đồng", "Bạc", "Vàng", "Bạch\nKim", "Kim\nCương"};
        progressBarDiem.setStateDescriptionData(descriptionData);

        int loaiThe = userDAO.layLoaiTheUser(iduser);
        StateProgressBar.StateNumber stateNumber;
        switch (loaiThe){
            case 1:
                stateNumber = StateProgressBar.StateNumber.valueOf(StateNumber.ONE.toString());
                progressBarDiem.setCurrentStateNumber(stateNumber);
                break;
            case 2:
                stateNumber = StateProgressBar.StateNumber.valueOf(StateNumber.TWO.toString());
                progressBarDiem.setCurrentStateNumber(stateNumber);
                break;
            case 3:
                stateNumber = StateProgressBar.StateNumber.valueOf(StateNumber.THREE.toString());
                progressBarDiem.setCurrentStateNumber(stateNumber);
                break;
            case 4:
                stateNumber = StateProgressBar.StateNumber.valueOf(StateNumber.FOUR.toString());
                progressBarDiem.setCurrentStateNumber(stateNumber);
                break;
            case 5:
                stateNumber = StateProgressBar.StateNumber.valueOf(StateNumber.FIVE.toString());
                progressBarDiem.setCurrentStateNumber(stateNumber);
                break;

        }

        progressBarDiem.enableAnimationToCurrentState(true);
        progressBarDiem.setOnStateItemClickListener(new OnStateItemClickListener() {
            @Override
            public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState) {
                ImageView imgCard;
                TextView txtInfo;
                LayoutInflater li;
                AlertDialog.Builder builder;
                AlertDialog alertDialog;
                final View viewCard;

                switch (stateNumber){
                   case 1: // Đồng
                       li = LayoutInflater.from(memberinfo.this);
                       viewCard = li.inflate(R.layout.layout_cardinfo, null);

                       imgCard = viewCard.findViewById(R.id.imgCard);
                       txtInfo = viewCard.findViewById(R.id.txtInfo);

                       txtInfo.setText("- Mỗi khách hàng khi đến và mua hàng tại hệ thống cửa hàng " +
                               "Điện Thoại Online đều được cấp miễn phí thẻ thành viên. " +
                               "Thẻ thành viên ban đầu sẽ là loại thẻ Đồng. \n - Khi khách hàng tiến hành mua hàng " +
                               "tại hệ thống thì thẻ sẽ được tích điểm vào thẻ cho đến khi đến đủ hạn mức" +
                               " sẽ được đổi thẻ. Thẻ Đồng sẽ tích điểm từ 0 đến 99 điểm.");
                       imgCard.setImageResource(R.drawable.bronze);

                       builder = new AlertDialog.Builder(memberinfo.this);
                       builder.setView(viewCard);
                       builder.setCancelable(false);
                       builder.setPositiveButton("Quay về", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               return;
                           }
                       });

                       alertDialog = builder.create();
                       alertDialog.show();
                       break;
                   case 2: // Bạc
                       li = LayoutInflater.from(memberinfo.this);
                       viewCard = li.inflate(R.layout.layout_cardinfo, null);

                       imgCard = viewCard.findViewById(R.id.imgCard);
                       txtInfo = viewCard.findViewById(R.id.txtInfo);

                       txtInfo.setText("- Khi đã đủ 100 điểm trong thẻ thì khách hàng có thể tiến hành " +
                               "đổi thẻ thành viên từ mức Đồng lên mức Bạc mà không hề tốn " +
                               "bất kỳ chi phí nào cả. \n - Với thẻ thành viên ở mức Bạc này thì khách hàng" +
                               " sẽ được giảm 2% giá trị tổng bill cho mỗi lần mua hàng tại hệ thống " +
                               "cửa hàng. Thẻ Bạc sẽ được tích điểm từ 100 đến 399 điểm. ");
                       imgCard.setImageResource(R.drawable.silver);
                       builder = new AlertDialog.Builder(memberinfo.this);
                       builder.setView(viewCard);
                       builder.setCancelable(false);
                       builder.setPositiveButton("Quay về", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               return;
                           }
                       });

                       alertDialog = builder.create();
                       alertDialog.show();
                       break;
                   case 3: // Vàng
                       li = LayoutInflater.from(memberinfo.this);
                       viewCard = li.inflate(R.layout.layout_cardinfo, null);

                       imgCard = viewCard.findViewById(R.id.imgCard);
                       txtInfo = viewCard.findViewById(R.id.txtInfo);
                       txtInfo.setText("- Khi đã đủ 400 điểm trong thẻ thì khách hàng có thể " +
                               "tiến hành đổi thẻ thành viên từ mức Bạc lên mức Vàng mà không " +
                               "hề tốn bất kỳ chi phí nào cả.\n - Với thẻ thành viên ở mức Vàng này" +
                               " thì khách hàng sẽ được giảm 5% giá trị tổng bill cho mỗi lần mua " +
                               "hàng tại hệ thống cửa hàng. Thẻ Vàng sẽ được tích điểm từ 400 đến 699 điểm. ");
                       imgCard.setImageResource(R.drawable.gold);
                       builder = new AlertDialog.Builder(memberinfo.this);
                       builder.setView(viewCard);
                       builder.setCancelable(false);
                       builder.setPositiveButton("Quay về", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               return;
                           }
                       });

                       alertDialog = builder.create();
                       alertDialog.show();
                       break;
                   case 4: // Bạch kim
                       li = LayoutInflater.from(memberinfo.this);
                       viewCard = li.inflate(R.layout.layout_cardinfo, null);

                       imgCard = viewCard.findViewById(R.id.imgCard);
                       txtInfo = viewCard.findViewById(R.id.txtInfo);
                       txtInfo.setText("- Khi đã đủ 700 điểm trong thẻ thì khách hàng " +
                               "có thể tiến hành đổi thẻ thành viên từ mức Vàng lên mức Bạch Kim" +
                               " mà không hề tốn bất kỳ chi phí nào cả.\n - Với thẻ thành viên" +
                               " ở mức Bạch Kim này thì khách hàng sẽ được giảm 7% giá trị " +
                               "tổng bill cho mỗi lần mua hàng tại hệ thống cửa hàng. Thẻ Bạch Kim " +
                               "sẽ được tích điểm từ 700 đến 999 điểm. ");
                       imgCard.setImageResource(R.drawable.platinum);
                       builder = new AlertDialog.Builder(memberinfo.this);
                       builder.setView(viewCard);
                       builder.setCancelable(false);
                       builder.setPositiveButton("Quay về", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               return;
                           }
                       });

                       alertDialog = builder.create();
                       alertDialog.show();
                       break;
                   case 5: // Kim cương
                       li = LayoutInflater.from(memberinfo.this);
                       viewCard = li.inflate(R.layout.layout_cardinfo, null);

                       imgCard = viewCard.findViewById(R.id.imgCard);
                       txtInfo = viewCard.findViewById(R.id.txtInfo);
                       txtInfo.setText("- Khi đã đủ 1000 điểm trong thẻ thì khách hàng" +
                               " có thể tiến hành đổi thẻ thành viên từ mức Bạch Kim lên mức " +
                               "Kim Cương mà không hề tốn bất kỳ chi phí nào cả. \n - Với thẻ thành viên" +
                               " ở mức Kim Cương này thì khách hàng sẽ được giảm " +
                               " 10% giá trị tổng bill cho mỗi lần mua hàng tại hệ thống cửa hàng. ");
                       imgCard.setImageResource(R.drawable.diamond);
                       builder = new AlertDialog.Builder(memberinfo.this);
                       builder.setView(viewCard);
                       builder.setCancelable(false);
                       builder.setPositiveButton("Quay về", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               return;
                           }
                       });

                       alertDialog = builder.create();
                       alertDialog.show();
                       break;
               }
            }
        });

    }

    public enum StateNumber {
        ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
        private int value;

        StateNumber(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


}
