package com.example.legia.mobileweb.AdapterSanPham;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legia.mobileweb.DTO.gopY;
import com.example.legia.mobileweb.R;

import java.util.List;

public class ListGopY extends BaseAdapter {
    private Context context;
    private List<gopY> danhSachGopY;

    public ListGopY(Context context, List<gopY> danhSachGopY) {
        this.context = context;
        this.danhSachGopY = danhSachGopY;
    }

    @Override
    public int getCount() {
        return danhSachGopY.size();
    }

    @Override
    public Object getItem(int position) {
        return danhSachGopY.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_option_report, parent, false);
        }
        TextView txtGopY = convertView.findViewById(R.id.txtReport);
        ImageView imgGopY = convertView.findViewById(R.id.imgReport);

        txtGopY.setText(danhSachGopY.get(position).getTenGopY());
        imgGopY.setImageResource(danhSachGopY.get(position).getHinhGopY());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int viTri = danhSachGopY.get(position).getIdGopY();
                switch (viTri) {
                    case 1: // Facebook Mess
                        String facebookID = "2044607315780037";
                        try {
                            Uri uri = Uri.parse("fb-messenger://user/");
                            uri = ContentUris.withAppendedId(uri, Long.parseLong(facebookID));
                            Intent toMessenger = new Intent(Intent.ACTION_VIEW, uri);
                            context.startActivity(toMessenger);

                        } catch (android.content.ActivityNotFoundException ex) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Bạn chưa cài ứng dụng Facebook messeger?");
                            builder.setMessage("Bạn có cài ngay bây giờ không?");
                            builder.setCancelable(false);
                            // if choose no go back.
                            builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            // if choose yes go to CH Play.
                            builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.facebook.orca")));
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        break;
                    case 2: // Call
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "1234567890"));
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                        context.startActivity(intent);
                        break;
                    case 3: // SMS
                        String number = "123456789";  // sdt
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
                        break;
                    case 4: // Viber
                        addViberNumber(context, "123456789");
                        break;

                }
            }
        });
        return convertView;
    }

    public void addViberNumber(Context context,String phone) {
        String viberPackageName = "com.viber.voip";

        try {
            context.startActivity(new
                            Intent(Intent.ACTION_VIEW,
                            Uri.parse("viber://add?number="+phone)
                    )
            );
        } catch (ActivityNotFoundException ex) {
            try {
                context.startActivity
                        (new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=" + viberPackageName))
                        );
            } catch (ActivityNotFoundException exe) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=" + viberPackageName)
                        )
                );
            }
        }
    }
}
