package com.example.legia.mobileweb.TouchID;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.AuthenticationCallback;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.legia.mobileweb.AdapterSanPham.SanPhamAdapterNew;
import com.example.legia.mobileweb.DAO.themVaoGioHang;
import com.example.legia.mobileweb.Encryption.encrypt;
import com.example.legia.mobileweb.R;
import com.example.legia.mobileweb.TyGia.DocTyGia;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.Arrays;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerPrintPayPal extends AuthenticationCallback {
    private Context context;
    String paypalClientid = "AUto2kIFoFUBohXTAbmnQICEOAPxW3MZGCilm3LV9A6Yd9JUN-Gd2m_p0kWZTVlsKiE0b3N4N0wAt7Uw";
    int paypalCode = 999;
    PayPalConfiguration configure;
    Intent m_service;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    String diaChi, quan, phuong = "";
    int soDienThoai = 0;

    themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;

    // Constructor
    public FingerPrintPayPal(Context mContext) {
        context = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
        }
    }


    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString, false);
    }


    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString, false);
    }

    final SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Fingerprint Authentication succeeded.", true);
        //Intent i = new Intent(context, HomeActivity.class);
        // Paypal
        configure = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(paypalClientid);
        m_service = new Intent(context, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configure);
        context.startService(m_service);


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bạn có muốn thanh toán ?");
        builder.setMessage("Bạn có thanh toán bây giờ không?");
        builder.setCancelable(false);
        // if choose no
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        // if choose yes, save into db
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Xác thực lại thông tin cá nhân:
                LayoutInflater li = LayoutInflater.from(context);
                final View formXacNhan = li.inflate(R.layout.layout_formxacnhan, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(formXacNhan);


                // set dialog message
                alertDialogBuilder
                        .setTitle("Nhập thông tin giao hàng")
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,int id) {
                                        try{
                                            final EditText txtDiaChi = formXacNhan.findViewById(R.id.txtDiaChiXacNhan);
                                            final EditText txtPhuong = formXacNhan.findViewById(R.id.txtPhuongXacNhan);
                                            final EditText txtQuan = formXacNhan.findViewById(R.id.txtQuanXacNhan);
                                            final EditText txtSoDienThoai = formXacNhan.findViewById(R.id.txtSoDienThoaiXacNhan);

                                            diaChi = txtDiaChi.getText().toString();
                                            phuong = txtPhuong.getText().toString();
                                            quan = txtQuan.getText().toString();
                                            soDienThoai = Integer.parseInt(txtSoDienThoai.getText().toString());

                                            if(diaChi.length()==0){
                                                Toast.makeText(context, "Bạn không được bỏ trống", Toast.LENGTH_SHORT).show();
                                            }else if(phuong.length()==0){
                                                Toast.makeText(context, "Bạn không được bỏ trống", Toast.LENGTH_SHORT).show();
                                            }else if(quan.length()==0){
                                                Toast.makeText(context, "Bạn không được bỏ trống", Toast.LENGTH_SHORT).show();
                                            }else if(txtSoDienThoai.getText().toString().length() ==0){
                                                Toast.makeText(context, "Bạn không được bỏ trống", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                SharedPreferences sharedPreferencesInfo = context.getSharedPreferences("info_khach_hang", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferencesInfo.edit();
                                                try {
                                                    byte[] diaChiMaHoa = encrypt.encrypt(diaChi);
                                                    byte[] phuongMaHoa = encrypt.encrypt(phuong);
                                                    byte[] quanMaHoa = encrypt.encrypt(quan);
                                                    byte[] soDienThoaiMaHoa = encrypt.encrypt(String.valueOf(soDienThoai));

                                                    editor.putString("diaChi", Arrays.toString(diaChiMaHoa));
                                                    editor.putString("phuong", Arrays.toString(phuongMaHoa));
                                                    editor.putString("quan", Arrays.toString(quanMaHoa));
                                                    editor.putString("sdt", Arrays.toString(soDienThoaiMaHoa));
                                                    editor.commit();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                                PayPalPayment cart = new PayPalPayment(new BigDecimal(ConvertToUSD(gioHang.tongTien())),"USD","Cart",
                                                        PayPalPayment.PAYMENT_INTENT_SALE);

                                                Intent intent = new Intent(context, PaymentActivity.class);
                                                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configure);
                                                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, cart);
                                                ((Activity) context).startActivityForResult(intent, paypalCode);
                                            }
                                        }
                                        catch (NumberFormatException e){
                                            Toast.makeText(context, "Bạn phải nhập số!", Toast.LENGTH_SHORT).show();

                                        }

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
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }


    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.", false);
    }

    public void update(String e, Boolean success){
        //TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
        //textView.setText(e);
        if(success){
            // textView.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
            Toast.makeText(context, "SUCCESS", Toast.LENGTH_LONG).show();
        }
    }

    private double ConvertToUSD(double vnd){
        return vnd/ DocTyGia.giaBan();
    }
}
