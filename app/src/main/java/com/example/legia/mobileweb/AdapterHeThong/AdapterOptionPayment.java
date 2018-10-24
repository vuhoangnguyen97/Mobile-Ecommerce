package com.example.legia.mobileweb.AdapterHeThong;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitpay.sdk.android.BitPayAndroid;
import com.bitpay.sdk.android.InvoiceActivity;
import com.bitpay.sdk.android.interfaces.BitpayPromiseCallback;
import com.bitpay.sdk.android.interfaces.InvoicePromiseCallback;
import com.bitpay.sdk.controller.BitPayException;
import com.bitpay.sdk.model.Invoice;
import com.example.legia.mobileweb.AdapterSanPham.SanPhamAdapterNew;
import com.example.legia.mobileweb.CheckInternet.Utils;
import com.example.legia.mobileweb.DAO.hoaDonDAO;
import com.example.legia.mobileweb.DAO.sanPhamDAO;
import com.example.legia.mobileweb.DAO.theTichDiemDAO;
import com.example.legia.mobileweb.DAO.themVaoGioHang;
import com.example.legia.mobileweb.DAO.userDAO;
import com.example.legia.mobileweb.DTO.User;
import com.example.legia.mobileweb.DTO.hoaDon;
import com.example.legia.mobileweb.DTO.payment;
import com.example.legia.mobileweb.DTO.sanPhamMua;
import com.example.legia.mobileweb.DTO.theKhachHang;
import com.example.legia.mobileweb.Encryption.encrypt;
import com.example.legia.mobileweb.MainActivity;
import com.example.legia.mobileweb.PaymentAPI.onePayPayment;
import com.example.legia.mobileweb.R;
import com.example.legia.mobileweb.TouchID.FingerprintHandler;
import com.example.legia.mobileweb.TouchID.touchIDDAO;
import com.example.legia.mobileweb.TyGia.DocTyGia;
import com.example.legia.mobileweb.login;
import com.example.legia.mobileweb.register;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.lib.vtcpay.sdk.ICallBackPayment;
import com.lib.vtcpay.sdk.InitModel;
import com.lib.vtcpay.sdk.PaymentModel;
import com.lib.vtcpay.sdk.VTCPaySDK;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.stripe.android.model.Token;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.security.KeyStore;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.*;
import javax.crypto.Cipher;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import vn.mservice.MoMo_Partner.MoMoPayment;


public class AdapterOptionPayment extends BaseAdapter implements OnActivityResult {
    private Context context;
    private List<payment> listPayment;
    private KeyStore keyStore;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "androidHive";
    private Cipher cipher;
    private Invoice bitpayInvoice;
    private BitPayAndroid bitpay;

    // mobile-shop: AUto2kIFoFUBohXTAbmnQICEOAPxW3MZGCilm3LV9A6Yd9JUN-Gd2m_p0kWZTVlsKiE0b3N4N0wAt7Uw
    // mobile-shop-ou: AevuvWLKP241Ekc5leS6juaCvZ6fG4BYCeKvXgSdjNPVoGkTbOdA0dM_niKXbAEwVXtD1K2kmRVB4HCC

    String paypalClientid = "AUto2kIFoFUBohXTAbmnQICEOAPxW3MZGCilm3LV9A6Yd9JUN-Gd2m_p0kWZTVlsKiE0b3N4N0wAt7Uw";
    int paypalCode = 999;
    PayPalConfiguration configure;
    Intent m_service;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    String diaChi, quan, phuong = "";
    int soDienThoai = 0;

    themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;

    public AdapterOptionPayment(Context context, List<payment> listPayment) {
        this.context = context;
        this.listPayment = listPayment;
    }

    @Override
    public int getCount() {
        return listPayment.size();
    }

    @Override
    public Object getItem(int position) {
        return listPayment.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_payment_option,parent,false);
        }
        ImageView imgPayment = convertView.findViewById(R.id.imgPaymentOption);
        imgPayment.setImageResource(listPayment.get(position).getHinhPayment());

        final int maHinhThuc = listPayment.get(position).getMaHinhThuc();
        final SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        final SharedPreferences loginWithFacebook = context.getSharedPreferences("loginWithFb", Context.MODE_PRIVATE);
        final SharedPreferences loginWithGoogle = context.getSharedPreferences("loginWithGoogle", Context.MODE_PRIVATE);

        convertView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                switch(maHinhThuc){
                    case 1:
                        // Paypal
                        configure = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(paypalClientid);
                        m_service = new Intent(context, PayPalService.class);
                        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configure);
                        context.startService(m_service);

                        if(loginWithFacebook.getBoolean("isLoginWithFB", false)){
                            //Facebook
                            AlertDialog.Builder messageBoxFacebook = new AlertDialog.Builder(context);
                            messageBoxFacebook.setTitle("Đăng ký thành viên?");
                            messageBoxFacebook.setMessage("Bạn có đăng ký thành viên của hệ thống để được tích điểm giảm giá không ?");
                            messageBoxFacebook.setCancelable(false);
                            messageBoxFacebook.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    context.startActivity(new Intent(context, register.class));
                                }
                            });
                            messageBoxFacebook.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Pay

                                }
                            });
                            AlertDialog alertDialogFacebook = messageBoxFacebook.create();
                            alertDialogFacebook.show();
                        }
                        else if(loginWithGoogle.getBoolean("isLoginWithGoogle", false)){
                            //Google
                            AlertDialog.Builder messageBoxGoogle = new AlertDialog.Builder(context);
                            messageBoxGoogle.setTitle("Đăng ký thành viên?");
                            messageBoxGoogle.setMessage("Bạn có đăng ký thành viên của hệ thống để được tích điểm giảm giá không ?");
                            messageBoxGoogle.setCancelable(false);
                            messageBoxGoogle.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    context.startActivity(new Intent(context, register.class));
                                }
                            });
                            messageBoxGoogle.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Pay

                                }
                            });
                            AlertDialog alertDialogGoogle = messageBoxGoogle.create();
                            alertDialogGoogle.show();
                        }
                        //check user login
                        else if(sp.getBoolean("isLogin", false)){
                            //
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
                                                                    String key = "Bar12345Bar12345"; // 128 bit key
                                                                    String initVector = "RandomInitVector"; // 16 bytes IV
                                                                    SharedPreferences sharedPreferencesInfo = context.getSharedPreferences("info_khach_hang", Context.MODE_PRIVATE);
                                                                    SharedPreferences.Editor editor = sharedPreferencesInfo.edit();
                                                                    try {
                                                                        String diaChiMaHoa = encrypt.encryptAES(key, initVector, diaChi);
                                                                        String phuongMaHoa = encrypt.encryptAES(key, initVector, phuong);
                                                                        String quanMaHoa = encrypt.encryptAES(key, initVector, quan);
                                                                        //String soDienThoaiMaHoa = encrypt.encryptString.valueOf(soDienThoai));

                                                                        editor.putString("diaChi", diaChiMaHoa);
                                                                        editor.putString("phuong", phuongMaHoa);
                                                                        editor.putString("quan", quanMaHoa);
                                                                        editor.putString("sdt", String.valueOf(soDienThoai));
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
                        else{
                            // chưa login
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Bạn chưa đăng nhập?");
                            builder.setMessage("Bạn có đăng nhập bây giờ không?");
                            builder.setCancelable(false);
                            // if choose no go back.
                            builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            // if choose yes go to login.
                            builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(context, login.class);
                                    intent.putExtra("loginGioHang", "gioHang");
                                    context.startActivity(intent);
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }


                        break;
                    case 2:
                        // Onepay
                        // Trên Android 6.0 có Hỗ trợ Touch ID
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            if(loginWithFacebook.getBoolean("isLoginWithFB", false)){
                                //Facebook
                                AlertDialog.Builder messageBoxFacebook = new AlertDialog.Builder(context);
                                messageBoxFacebook.setTitle("Đăng ký thành viên?");
                                messageBoxFacebook.setMessage("Bạn có đăng ký thành viên của hệ thống để được tích điểm giảm giá không ?");
                                messageBoxFacebook.setCancelable(false);
                                messageBoxFacebook.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        context.startActivity(new Intent(context, register.class));
                                    }
                                });
                                messageBoxFacebook.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // Pay

                                    }
                                });
                                AlertDialog alertDialogFacebook = messageBoxFacebook.create();
                                alertDialogFacebook.show();
                            }
                            else if(loginWithGoogle.getBoolean("isLoginWithGoogle", false)){
                                //Google
                                AlertDialog.Builder messageBoxGoogle = new AlertDialog.Builder(context);
                                messageBoxGoogle.setTitle("Đăng ký thành viên?");
                                messageBoxGoogle.setMessage("Bạn có đăng ký thành viên của hệ thống để được tích điểm giảm giá không ?");
                                messageBoxGoogle.setCancelable(false);
                                messageBoxGoogle.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        context.startActivity(new Intent(context, register.class));
                                    }
                                });
                                messageBoxGoogle.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // Pay

                                    }
                                });
                                AlertDialog alertDialogGoogle = messageBoxGoogle.create();
                                alertDialogGoogle.show();
                            }
                            //check user login
                            else if(sp.getBoolean("isLogin", false)){
                                LayoutInflater li = LayoutInflater.from(context);
                                final View viewTouchID = li.inflate(R.layout.layout_touch_id, null);
                                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);

                                TextView txtError = viewTouchID.findViewById(R.id.errorText);

                                builder.setView(viewTouchID);
                                KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                                FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
                                android.support.v7.app.AlertDialog dialog=builder.create();

                                if(!fingerprintManager.isHardwareDetected()){
                                    txtError.setText("Your Device does not have a Fingerprint Sensor");
                                }
                                else{
                                    // Check whether at least one fingerprint is registered
                                    if (!fingerprintManager.hasEnrolledFingerprints()) {
                                        txtError.setText("Register at least one fingerprint in Settings");
                                    }else{
                                        // Checks whether lock screen security is enabled or not
                                        if (!keyguardManager.isKeyguardSecure()) {
                                            txtError.setText("Lock screen security not enabled in Settings");
                                        }else{
                                            touchIDDAO touchID = new touchIDDAO();
                                            touchID.generateKey();
                                            if (touchID.cipherInit()) {
                                                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                                                FingerprintHandler helper = new FingerprintHandler(context);
                                                helper.startAuth(fingerprintManager, cryptoObject);

                                            }
                                        }
                                    }
                                }
                                dialog.show();
                            }
                            else{
                                // chưa login
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Bạn chưa đăng nhập?");
                                builder.setMessage("Bạn có đăng nhập bây giờ không?");
                                builder.setCancelable(false);
                                // if choose no go back.
                                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                                // if choose yes go to login.
                                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(context, login.class);
                                        intent.putExtra("loginGioHang", "gioHang");
                                        context.startActivity(intent);
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }

                        }
                        else {
                            // Dưới Android 6.0, không hỗ trợ TouchID.
                            //
                            if(loginWithFacebook.getBoolean("isLoginWithFB", false)){
                                //Facebook
                                AlertDialog.Builder messageBoxFacebook = new AlertDialog.Builder(context);
                                messageBoxFacebook.setTitle("Đăng ký thành viên?");
                                messageBoxFacebook.setMessage("Bạn có đăng ký thành viên của hệ thống để được tích điểm giảm giá không ?");
                                messageBoxFacebook.setCancelable(false);
                                messageBoxFacebook.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        context.startActivity(new Intent(context, register.class));
                                    }
                                });
                                messageBoxFacebook.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // Pay
                                        themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
                                        double tongtien = gioHang.tongTien();
                                        DecimalFormat df = new DecimalFormat("###,###.##");
                                        Toast.makeText(context, "Tổng tiền: " + df.format(tongtien), Toast.LENGTH_SHORT).show();

                                        // Info card owner.
                                        String vpc_Version = "2";
                                        String vpc_Currency = "VND";
                                        String vpc_Command = "pay";
                                        String vpc_AccessCode = "D67342C2";
                                        String vpc_Merchant = "ONEPAY";
                                        String vpc_Locale = "vn";
                                        String vpc_ReturnURL = "http://192.168.1.67:8081/test-onepay/dr.jsp";

                                        double amout = tongtien * 100;
                                        // Info bill
                                        // Get IP android:
                                        String vpc_TicketNo = Utils.getIPAddress(true);
                                        String AgainLink = "test.htm";
                                        String vpc_OrderInfo = "HÓA ĐƠN THANH TOÁN:";
                                        String vpc_Amount = String.valueOf(amout);

                                        Random random = new Random();
                                        String vpc_MerchTxnRef = String.valueOf(random.nextInt(1000000));

                                        Map<String, String> fields = new HashMap<>();
                                        fields.put("vpc_Version", vpc_Version);
                                        fields.put("vpc_Currency", vpc_Currency);
                                        fields.put("vpc_Command", vpc_Command);
                                        fields.put("vpc_OrderInfo", vpc_OrderInfo);
                                        fields.put("vpc_AccessCode", vpc_AccessCode);
                                        fields.put("vpc_Merchant", vpc_Merchant);
                                        fields.put("vpc_Locale", vpc_Locale);
                                        fields.put("vpc_MerchTxnRef", vpc_MerchTxnRef);
                                        fields.put("vpc_TicketNo", vpc_TicketNo);
                                        fields.put("AgainLink", AgainLink);
                                        fields.put("vpc_ReturnURL", vpc_ReturnURL);

                                        fields.put("vpc_Amount", vpc_Amount);

                                        String vpcURL = "https://mtf.onepay.vn/onecomm-pay/vpc.op?";
                                        String secureHash = onePayPayment.hashAllFields(fields);
                                        fields.put("vpc_SecureHash", secureHash);

                                        //StringBuffer buf = new StringBuffer();
                                        //buf.append(vpcURL).append('?');

                                        //appendQueryFields(buf, fields);
                                        String parameter = "vpc_Amount=" + vpc_Amount + "&vpc_Version=2&vpc_OrderInfo=" + vpc_OrderInfo + "&vpc_Command=pay&vpc_Currency=VND&vpc_Merchant=ONEPAY&Title=" + vpc_OrderInfo + "&vpc_ReturnURL=" + vpc_ReturnURL + "&AgainLink=http%3A%2F%2Flocalhost%3A8080%2Ftest-onepay%2F&vpc_SecureHash=" + fields.get("vpc_SecureHash") + "&vpc_AccessCode=D67342C2&vpc_MerchTxnRef=" + vpc_MerchTxnRef + "&vpc_TicketNo=" + vpc_TicketNo + "&vpc_Locale=vn";

                                        for (Map.Entry<String, String> param : fields.entrySet()) {
                                            Log.i("Test ", param.getKey() + " : " + param.getValue());
                                            //parameter += param.getKey()+"="+param.getValue()+"&&";
                                        }


                                        String url = vpcURL + parameter;
                                        Log.i("testurl", "url: " + url);
                                        Intent facebook = new Intent(Intent.ACTION_VIEW);
                                        facebook.setData(Uri.parse(url));
                                        context.startActivity(facebook);

                                    }
                                });
                                AlertDialog alertDialogFacebook = messageBoxFacebook.create();
                                alertDialogFacebook.show();
                            }
                            else if(loginWithGoogle.getBoolean("isLoginWithGoogle", false)){
                                //Google
                                AlertDialog.Builder messageBoxGoogle = new AlertDialog.Builder(context);
                                messageBoxGoogle.setTitle("Đăng ký thành viên?");
                                messageBoxGoogle.setMessage("Bạn có đăng ký thành viên của hệ thống để được tích điểm giảm giá không ?");
                                messageBoxGoogle.setCancelable(false);
                                messageBoxGoogle.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        context.startActivity(new Intent(context, register.class));
                                    }
                                });
                                messageBoxGoogle.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // Pay
                                        themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
                                        double tongtien = gioHang.tongTien();
                                        DecimalFormat df = new DecimalFormat("###,###.##");
                                        Toast.makeText(context, "Tổng tiền: " + df.format(tongtien), Toast.LENGTH_SHORT).show();

                                        // Info card owner.
                                        String vpc_Version = "2";
                                        String vpc_Currency = "VND";
                                        String vpc_Command = "pay";
                                        String vpc_AccessCode = "D67342C2";
                                        String vpc_Merchant = "ONEPAY";
                                        String vpc_Locale = "vn";
                                        String vpc_ReturnURL = "http://192.168.1.67:8081/test-onepay/dr.jsp";

                                        double amout = tongtien * 100;
                                        // Info bill
                                        // Get IP android:
                                        String vpc_TicketNo = Utils.getIPAddress(true);
                                        String AgainLink = "test.htm";
                                        String vpc_OrderInfo = "HÓA ĐƠN THANH TOÁN:";
                                        String vpc_Amount = String.valueOf(amout);

                                        Random random = new Random();
                                        String vpc_MerchTxnRef = String.valueOf(random.nextInt(1000000));

                                        Map<String, String> fields = new HashMap<>();
                                        fields.put("vpc_Version", vpc_Version);
                                        fields.put("vpc_Currency", vpc_Currency);
                                        fields.put("vpc_Command", vpc_Command);
                                        fields.put("vpc_OrderInfo", vpc_OrderInfo);
                                        fields.put("vpc_AccessCode", vpc_AccessCode);
                                        fields.put("vpc_Merchant", vpc_Merchant);
                                        fields.put("vpc_Locale", vpc_Locale);
                                        fields.put("vpc_MerchTxnRef", vpc_MerchTxnRef);
                                        fields.put("vpc_TicketNo", vpc_TicketNo);
                                        fields.put("AgainLink", AgainLink);
                                        fields.put("vpc_ReturnURL", vpc_ReturnURL);

                                        fields.put("vpc_Amount", vpc_Amount);

                                        String vpcURL = "https://mtf.onepay.vn/onecomm-pay/vpc.op?";
                                        String secureHash = onePayPayment.hashAllFields(fields);
                                        fields.put("vpc_SecureHash", secureHash);

                                        //StringBuffer buf = new StringBuffer();
                                        //buf.append(vpcURL).append('?');

                                        //appendQueryFields(buf, fields);
                                        String parameter = "vpc_Amount=" + vpc_Amount + "&vpc_Version=2&vpc_OrderInfo=" + vpc_OrderInfo + "&vpc_Command=pay&vpc_Currency=VND&vpc_Merchant=ONEPAY&Title=" + vpc_OrderInfo + "&vpc_ReturnURL=" + vpc_ReturnURL + "&AgainLink=http%3A%2F%2Flocalhost%3A8080%2Ftest-onepay%2F&vpc_SecureHash=" + fields.get("vpc_SecureHash") + "&vpc_AccessCode=D67342C2&vpc_MerchTxnRef=" + vpc_MerchTxnRef + "&vpc_TicketNo=" + vpc_TicketNo + "&vpc_Locale=vn";

                                        for (Map.Entry<String, String> param : fields.entrySet()) {
                                            Log.i("Test ", param.getKey() + " : " + param.getValue());
                                            //parameter += param.getKey()+"="+param.getValue()+"&&";
                                        }


                                        String url = vpcURL + parameter;
                                        Log.i("testurl", "url: " + url);
                                        Intent google = new Intent(Intent.ACTION_VIEW);
                                        google.setData(Uri.parse(url));
                                        context.startActivity(google);

                                    }
                                });
                                AlertDialog alertDialogGoogle = messageBoxGoogle.create();
                                alertDialogGoogle.show();
                            }
                            //check user login
                            else if(sp.getBoolean("isLogin", false)){
                                themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
                                double tongtien = gioHang.tongTien();
                                DecimalFormat df = new DecimalFormat("###,###.##");
                                Toast.makeText(context, "Tổng tiền: " + df.format(tongtien), Toast.LENGTH_SHORT).show();

                                // Info card owner.
                                String vpc_Version = "2";
                                String vpc_Currency = "VND";
                                String vpc_Command = "pay";
                                String vpc_AccessCode = "D67342C2";
                                String vpc_Merchant = "ONEPAY";
                                String vpc_Locale = "vn";
                                String vpc_ReturnURL = "http://192.168.1.67:8081/test-onepay/dr.jsp";

                                double amout = tongtien * 100;
                                // Info bill
                                // Get IP android:
                                String vpc_TicketNo = Utils.getIPAddress(true);
                                String AgainLink = "test.htm";
                                String vpc_OrderInfo = "HÓA ĐƠN THANH TOÁN:";
                                String vpc_Amount = String.valueOf(amout);

                                Random random = new Random();
                                String vpc_MerchTxnRef = String.valueOf(random.nextInt(1000000));

                                Map<String, String> fields = new HashMap<>();
                                fields.put("vpc_Version", vpc_Version);
                                fields.put("vpc_Currency", vpc_Currency);
                                fields.put("vpc_Command", vpc_Command);
                                fields.put("vpc_OrderInfo", vpc_OrderInfo);
                                fields.put("vpc_AccessCode", vpc_AccessCode);
                                fields.put("vpc_Merchant", vpc_Merchant);
                                fields.put("vpc_Locale", vpc_Locale);
                                fields.put("vpc_MerchTxnRef", vpc_MerchTxnRef);
                                fields.put("vpc_TicketNo", vpc_TicketNo);
                                fields.put("AgainLink", AgainLink);
                                fields.put("vpc_ReturnURL", vpc_ReturnURL);

                                fields.put("vpc_Amount", vpc_Amount);

                                String vpcURL = "https://mtf.onepay.vn/onecomm-pay/vpc.op?";
                                String secureHash = onePayPayment.hashAllFields(fields);
                                fields.put("vpc_SecureHash", secureHash);

                                //StringBuffer buf = new StringBuffer();
                                //buf.append(vpcURL).append('?');

                                //appendQueryFields(buf, fields);
                                String parameter = "vpc_Amount=" + vpc_Amount + "&vpc_Version=2&vpc_OrderInfo=" + vpc_OrderInfo + "&vpc_Command=pay&vpc_Currency=VND&vpc_Merchant=ONEPAY&Title=" + vpc_OrderInfo + "&vpc_ReturnURL=" + vpc_ReturnURL + "&AgainLink=http%3A%2F%2Flocalhost%3A8080%2Ftest-onepay%2F&vpc_SecureHash=" + fields.get("vpc_SecureHash") + "&vpc_AccessCode=D67342C2&vpc_MerchTxnRef=" + vpc_MerchTxnRef + "&vpc_TicketNo=" + vpc_TicketNo + "&vpc_Locale=vn";

                                for (Map.Entry<String, String> param : fields.entrySet()) {
                                    Log.i("Test ", param.getKey() + " : " + param.getValue());
                                    //parameter += param.getKey()+"="+param.getValue()+"&&";
                                }


                                String url = vpcURL + parameter;
                                Log.i("testurl", "url: " + url);
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                context.startActivity(i);
                            }
                            else{
                                // chưa login
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Bạn chưa đăng nhập?");
                                builder.setMessage("Bạn có đăng nhập bây giờ không?");
                                builder.setCancelable(false);
                                // if choose no go back.
                                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                                // if choose yes go to login.
                                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(context, login.class);
                                        intent.putExtra("loginGioHang", "gioHang");
                                        context.startActivity(intent);
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }

                        }
                        break;
                    /*
                    case 3: //VNPAY

                        String vnp_Version = "2.0.0";
                        String vnp_Command = "pay";
                        String vnp_OrderInfo = "hoadon";
                        //String orderType = req.getParameter("ordertype");
                        String vnp_Merchant = "DEMO";
                        String vnp_TxnRef = ConfigVNPay.getRandomNumber(8);
                        String vnp_IpAddr = Utils.getIPAddress(true);
                        String vnp_ReturnUrl = "http://sandbox.vnpayment.vn/tryitnow/Home/VnPayReturn";
                        String vnp_TmnCode = ConfigVNPay.vnp_TmnCode;

                        String vnp_TransactionNo = vnp_TxnRef;
                        String vnp_hashSecret = ConfigVNPay.vnp_HashSecret;

                        themVaoGioHang gioHang1 = SanPhamAdapter.gioHang;
                        double amount = gioHang1.tongTien()*100;

                        Map<String, String> vnp_Params = new HashMap<>();
                        vnp_Params.put("vnp_Version", vnp_Version);
                        vnp_Params.put("vnp_hashSecret", vnp_hashSecret);
                        vnp_Params.put("vnp_Merchant", vnp_Merchant);
                        vnp_Params.put("vnp_Command", vnp_Command);
                        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
                        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
                        vnp_Params.put("vnp_TransactionNo", vnp_TransactionNo);
                        vnp_Params.put("vnp_Amount", String.valueOf(amount));
                        vnp_Params.put("vnp_CurrCode", "VND");
                        vnp_Params.put("vnp_BankCode", "NCB");
                        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
                        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
                        vnp_Params.put("vnp_OrderType", "topup");
                        vnp_Params.put("vnp_Locale", "vn");
                        vnp_Params.put("vnp_ReturnUrl", ConfigVNPay.vnp_Returnurl);
                        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

                        Date dt = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                        String dateString = formatter.format(dt);
                        String vnp_CreateDate = dateString;
                        String vnp_TransDate = vnp_CreateDate;
                        vnp_Params.put("vnp_CreateDate", vnp_TransDate);

                        //HASHING
                        //Build data to hash and querystring
                        List fieldNames = new ArrayList(vnp_Params.keySet());
                        Collections.sort(fieldNames);
                        Charset.forName("UTF-8");
                        StringBuilder hashData = new StringBuilder();
                        StringBuilder query = new StringBuilder();
                        Iterator itr = fieldNames.iterator();
                        while (itr.hasNext()) {
                            String fieldName = (String) itr.next();
                            String fieldValue = (String) vnp_Params.get(fieldName);
                            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                                //Build hash data
                                hashData.append(fieldName);
                                hashData.append('=');
                                hashData.append(fieldValue);
                                //Build query
                                query.append(fieldName);
                                query.append('=');
                                query.append(fieldValue);

                                if (itr.hasNext()) {
                                    query.append('&');
                                    hashData.append('&');
                                }

                            }
                        }
                        /*String queryUrl = query.toString();
                        String vnp_SecureHash = ConfigVNPay.md5(ConfigVNPay.vnp_HashSecret + hashData.toString());
                        //System.out.println("HashData=" + hashData.toString());
                        queryUrl += "&vnp_SecureHashType=MD5&vnp_SecureHash=" + vnp_SecureHash;
                        String paymentUrl = ConfigVNPay.vnp_PayUrl + "?" + queryUrl;
                        */
                        /*String vnp_SecureHash = ConfigVNPay.hashAllFields(vnp_Params);
                        String param = "vnp_Amount="+vnp_Params.get("vnp_Amount")+"&vnp_BankCode=NCB&" +
                                "vnp_Command=pay&vnp_CreateDate="+vnp_Params.get("vnp_CreateDate")+"&vnp_CurrCode=VND&" +
                                "vnp_IpAddr="+vnp_Params.get("vnp_IpAddr")+"&vnp_Locale=vn&vnp_Merchant=DEMO&" +
                                "vnp_OrderInfo="+vnp_Params.get("vnp_OrderInfo")+"&" +
                                "vnp_OrderType=topup&vnp_ReturnUrl=http%3a%2f%2fsandbox.vnpayment.vn%2ftryitnow%2fHome%2fVnPayReturn&" +
                                "vnp_TmnCode=2QXUI4J4&vnp_TxnRef="+vnp_Params.get("vnp_TxnRef")+"&vnp_Version=2&" +
                                "vnp_SecureHashType=MD5&vnp_SecureHash="+vnp_SecureHash;
                        String paymentUrl = "http://sandbox.vnpayment.vn/paymentv2/vpcpay.html?" + param;

                        String queryUrl = query.toString();
                        String vnp_SecureHash = ConfigVNPay.hashAllFields(vnp_Params);
                        queryUrl += "&vnp_SecureHashType=MD5&vnp_SecureHash=" + vnp_SecureHash;
                        String paymentUrl = ConfigVNPay.vnp_PayUrl + "?" + queryUrl;
                        Log.i("testurl", "url vnpay: " + paymentUrl);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(paymentUrl));
                        context.startActivity(intent);

                        break;
                    */
                    case 4: //COD
                        // Pop up
                        // Xác thực lại thông tin cá nhân:
                        if(loginWithFacebook.getBoolean("isLoginWithFb", false)){
                            //Facebook
                            AlertDialog.Builder messageBoxFacebook = new AlertDialog.Builder(context);
                            messageBoxFacebook.setTitle("Đăng ký thành viên?");
                            messageBoxFacebook.setMessage("Bạn có đăng ký thành viên của hệ thống để được tích điểm giảm giá không ?");
                            messageBoxFacebook.setCancelable(false);
                            messageBoxFacebook.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    context.startActivity(new Intent(context, register.class));
                                }
                            });
                            messageBoxFacebook.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Pay

                                }
                            });
                            AlertDialog alertDialogFacebook = messageBoxFacebook.create();
                            alertDialogFacebook.show();
                        }
                        else if(loginWithGoogle.getBoolean("isLoginWithGoogle", false)){
                            //Google
                            AlertDialog.Builder messageBoxGoogle = new AlertDialog.Builder(context);
                            messageBoxGoogle.setTitle("Đăng ký thành viên?");
                            messageBoxGoogle.setMessage("Bạn có đăng ký thành viên của hệ thống để được tích điểm giảm giá không ?");
                            messageBoxGoogle.setCancelable(false);
                            messageBoxGoogle.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    context.startActivity(new Intent(context, register.class));
                                }
                            });
                            messageBoxGoogle.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Pay

                                }
                            });
                            AlertDialog alertDialogGoogle = messageBoxGoogle.create();
                            alertDialogGoogle.show();
                        }
                        else if(sp.getBoolean("isLogin", false)) {

                            LayoutInflater liGiaoHang = LayoutInflater.from(context);
                            final View formXacNhanGiaoHang = liGiaoHang.inflate(R.layout.layout_formxacnhan, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    context);

                            // set prompts.xml to alertdialog builder
                            alertDialogBuilder.setView(formXacNhanGiaoHang);


                            // set dialog message
                            alertDialogBuilder
                                    .setTitle("Nhập thông tin giao hàng")
                                    .setCancelable(true)
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int id) {

                                                    final EditText txtDiaChiGiaoHang = formXacNhanGiaoHang.findViewById(R.id.txtDiaChiXacNhan);
                                                    final EditText txtPhuongGiaoHang = formXacNhanGiaoHang.findViewById(R.id.txtPhuongXacNhan);
                                                    final EditText txtQuanGiaoHang = formXacNhanGiaoHang.findViewById(R.id.txtQuanXacNhan);
                                                    final EditText txtSoDienThoaiGiaoHang = formXacNhanGiaoHang.findViewById(R.id.txtSoDienThoaiXacNhan);

                                                    String diaChiGiaoHang = txtDiaChiGiaoHang.getText().toString();
                                                    String phuongGiaoHang = txtPhuongGiaoHang.getText().toString();
                                                    String quanGiaoHang = txtQuanGiaoHang.getText().toString();
                                                    int soDienThoaiGiaoHang = Integer.parseInt(txtSoDienThoaiGiaoHang.getText().toString());

                                                    if (diaChiGiaoHang.length() == 0) {
                                                        Toast.makeText(context, "Bạn không được bỏ trống", Toast.LENGTH_SHORT).show();
                                                    } else if (phuongGiaoHang.length() == 0) {
                                                        Toast.makeText(context, "Bạn không được bỏ trống", Toast.LENGTH_SHORT).show();
                                                    } else if (quanGiaoHang.length() == 0) {
                                                        Toast.makeText(context, "Bạn không được bỏ trống", Toast.LENGTH_SHORT).show();
                                                    } else if (txtSoDienThoaiGiaoHang.getText().toString().length() == 0) {
                                                        Toast.makeText(context, "Bạn không được bỏ trống", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        // insert to db
                                                        final SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
                                                        final themVaoGioHang themVaoGioHang = SanPhamAdapterNew.gioHang;
                                                        String details = "";
                                                        String sp_mua = "";
                                                        final DecimalFormat df = new DecimalFormat("###,###.##");
                                                        for (sanPhamMua spm : themVaoGioHang.danhSachSanPhamMua()) {
                                                            details += "Tên sản phẩm: " + spm.getTenSanPham() + "- Số lượng : " + spm.getSoLuongMua() + "\n" + "Giá 1 cái: " + df.format(spm.getGiaSanPham());
                                                            sp_mua += "Tên sản phẩm: " + spm.getTenSanPham() + " - Số lượng : " + spm.getSoLuongMua() + " - " + "Giá 1 cái: " + df.format(spm.getGiaSanPham()) + "đ<br/>";
                                                        }
                                                        int iduser = sp.getInt("idUser", 0);
                                                        User u = userDAO.readUser(iduser);
                                                        try {
                                                        /* Triple Des
                                                        byte[] diaChiMaHoa = encrypt.encrypt(diaChiGiaoHang);
                                                        byte[] phuongMaHoa = encrypt.encrypt(phuongGiaoHang);
                                                        byte[] quanMaHoa = encrypt.encrypt(quanGiaoHang);
                                                        byte[] chiTietMaHoa = encrypt.encrypt(details);*/

                                                            int id_the_tich_diem = u.getId_the_tich_diem();

                                                            double tongTien = gioHang.tongTien();

                                                            theKhachHang theKhachHang = theTichDiemDAO.theKhachHang(id_the_tich_diem);
                                                            int diem = theKhachHang.getDiem();

                                                            diem+= tongTien/100000;
                                                            if(diem>=100 && diem <400){
                                                                // Silver
                                                                int updateThe = theTichDiemDAO.nangCapThe(id_the_tich_diem, 2);
                                                            }
                                                            else if(diem >=400 && diem<700){
                                                                // Gold
                                                                int updateThe = theTichDiemDAO.nangCapThe(id_the_tich_diem, 3);

                                                            }
                                                            else if(diem>=700 && diem <1000){
                                                                // Plantium
                                                                int updateThe = theTichDiemDAO.nangCapThe(id_the_tich_diem, 4);

                                                            }else if(diem>=1000){
                                                                // Diamond
                                                                int updateThe = theTichDiemDAO.nangCapThe(id_the_tich_diem, 5);

                                                            }

                                                            int tichDiem = theTichDiemDAO.tichDiem(id_the_tich_diem, diem);

                                                            String key = "Bar12345Bar12345"; // 128 bit key
                                                            String initVector = "RandomInitVector"; // 16 bytes IV
                                                            String diaChiMaHoa = encrypt.encryptAES(key, initVector, diaChiGiaoHang);
                                                            String phuongMaHoa = encrypt.encryptAES(key, initVector, phuongGiaoHang);
                                                            String quanMaHoa = encrypt.encryptAES(key, initVector, quanGiaoHang);
                                                            String chiTietMaHoa = encrypt.encryptAES(key, initVector, details);

                                                            int updateInfo = userDAO.updateInfoUser(iduser, diaChiMaHoa, phuongMaHoa, quanMaHoa, soDienThoaiGiaoHang);

                                                            final hoaDon hd = new hoaDon();
                                                            hd.setId_user(iduser);
                                                            hd.setTen_user(u.getTen_user());
                                                            hd.setHo_user(u.getHo_user());
                                                            hd.setEmail(u.getEmail());
                                                            hd.setDiaChi(diaChiMaHoa.toString());
                                                            hd.setSdt(soDienThoaiGiaoHang);
                                                            hd.setThanhPho(u.getThanh_pho());
                                                            hd.setPhuong(phuongMaHoa.toString());
                                                            hd.setQuan(quanMaHoa.toString());
                                                            hd.setChiTiet(chiTietMaHoa.toString());
                                                            hd.setHinhThucThanhToan("Thanh toán qua COD");
                                                            int themHoaDon = hoaDonDAO.themHoaDon(hd);
                                                            if (themHoaDon != 0) {
                                                                // Clear all of memories
                                                                context.startActivity(new Intent(context, MainActivity.class));
                                                                Toast.makeText(context, "Mua thành công!", Toast.LENGTH_SHORT).show();
                                                                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                                                try {
                                                                    String data = "";
                                                                    byte ptext[] = details.getBytes("UTF8");
                                                                    for (int i = 0; i < ptext.length; i++) {
                                                                        //System.out.print(ptext[i]);
                                                                        data += ptext[i];
                                                                    }
                                                                    BitMatrix bitMatrix = multiFormatWriter.encode(removeAccent(details), BarcodeFormat.QR_CODE, 200, 200);
                                                                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                                                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                                                    //imageView.setImageBitmap(bitmap);

                                                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                                                    byte[] byteData = baos.toByteArray();
                                                                    final String[] url = {""};
                                                                    /*String alphabet = "abcdefghijklmnopqrstuvwxyz";
                                                                    String s = "";
                                                                    Random random = new Random();
                                                                    int randomLen = 1 + random.nextInt(9);
                                                                    for (int i = 0; i < randomLen; i++) {
                                                                        char c = alphabet.charAt(random.nextInt(26));
                                                                        s += c;
                                                                    }*/

                                                                    int idFile = hoaDonDAO.lastID()+1;
                                                                    // Create PDF
                                                                    Document document = new Document();

                                                                    final String path = context.getFilesDir().getAbsolutePath()+"/Dir";
                                                                    File dir = new File(path);
                                                                    if(!dir.exists())
                                                                        dir.mkdirs();

                                                                    String title = idFile+"_"+removeAccent(hd.getTen_user())+".pdf";

                                                                    final File file = new File(dir, title);
                                                                    FileOutputStream fOut = new FileOutputStream(file);

                                                                    PdfWriter.getInstance(document, fOut);
                                                                    document.open();

                                                                    DottedLineSeparator separatorTop = new DottedLineSeparator();
                                                                    separatorTop.setPercentage(59500f / 523f);
                                                                    Chunk linebreakTop = new Chunk(separatorTop);
                                                                    document.add(linebreakTop);

                                                                    Paragraph p1 = new Paragraph("Cua hang dien thoai online");
                                                                    p1.setAlignment(Element.ALIGN_CENTER);
                                                                    document.add(p1);

                                                                    Paragraph p2 = new Paragraph("SDT: (+84)028-38123-456");
                                                                    p2.setAlignment(Element.ALIGN_CENTER);
                                                                    document.add(p2);

                                                                    Paragraph p3 = new Paragraph("Email : chamsockhachhangdtonline@gmail.com");
                                                                    p3.setAlignment(Element.ALIGN_CENTER);
                                                                    document.add(p3);


                                                                    DottedLineSeparator separator = new DottedLineSeparator();
                                                                    separator.setPercentage(59500f / 523f);
                                                                    Chunk linebreak = new Chunk(separator);
                                                                    document.add(linebreak);

                                                                    Paragraph paragraph = new Paragraph("Ma hoa don: " + idFile);
                                                                    paragraph.setAlignment(Element.ALIGN_LEFT);
                                                                    document.add(paragraph);

                                                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                                                    String billCreated = sdf.format(new Date());

                                                                    paragraph = new Paragraph("Ngay tao hoa don: " + billCreated );
                                                                    paragraph.setAlignment(Element.ALIGN_LEFT);
                                                                    document.add(paragraph);

                                                                    paragraph = new Paragraph("Nguoi nhan: " + removeAccent(hd.getTen_user()));
                                                                    paragraph.setAlignment(Element.ALIGN_LEFT);
                                                                    document.add(paragraph);

                                                                    paragraph = new Paragraph("SDT: " + hd.getSdt());
                                                                    paragraph.setAlignment(Element.ALIGN_LEFT);
                                                                    document.add(paragraph);

                                                                    paragraph = new Paragraph("Dia chi giao hang: " + removeAccent(encrypt.decryptAES(key, initVector, hd.getDiaChi())));
                                                                    paragraph.setAlignment(Element.ALIGN_LEFT);
                                                                    document.add(paragraph);

                                                                    paragraph = new Paragraph("Hinh thuc thanh toan: " + removeAccent(hd.getHinhThucThanhToan()));
                                                                    paragraph.setAlignment(Element.ALIGN_LEFT);
                                                                    document.add(paragraph);

                                                                    DottedLineSeparator separator2 = new DottedLineSeparator();
                                                                    separator2.setPercentage(59500f / 523f);
                                                                    Chunk linebreak2 = new Chunk(separator2);
                                                                    document.add(linebreak2);

                                                                    Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);


                                                                    PdfPTable table = new PdfPTable(5);
                                                                    table.setTotalWidth(300f);
                                                                    table.setHorizontalAlignment(Element.ALIGN_CENTER);

                                                                    PdfPCell cell1 = new PdfPCell(new Phrase("STT", boldFont));
                                                                    cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
                                                                    cell1.setCellEvent(new DottedCell());
                                                                    cell1.setBorder(Rectangle.NO_BORDER);
                                                                    table.addCell(cell1);

                                                                    PdfPCell cell2 = new PdfPCell(new Phrase("Ten san pham", boldFont));
                                                                    cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                                                                    cell2.setCellEvent(new DottedCell());
                                                                    cell2.setBorder(Rectangle.NO_BORDER);
                                                                    table.addCell(cell2);

                                                                    PdfPCell cell3 = new PdfPCell(new Phrase("Gia 1 cai", boldFont));
                                                                    cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                                    cell3.setCellEvent(new DottedCell());
                                                                    cell3.setBorder(Rectangle.NO_BORDER);
                                                                    table.addCell(cell3);

                                                                    PdfPCell cell4 = new PdfPCell(new Phrase("So luong", boldFont));
                                                                    cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                                    cell4.setCellEvent(new DottedCell());
                                                                    cell4.setBorder(Rectangle.NO_BORDER);
                                                                    table.addCell(cell4);

                                                                    PdfPCell cell5 = new PdfPCell(new Phrase("Tong", boldFont));
                                                                    cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                                    cell5.setCellEvent(new DottedCell());
                                                                    cell5.setBorder(Rectangle.NO_BORDER);
                                                                    table.addCell(cell5);
                                                                    //table.setHeaderRows(1);

                                                                    final themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
                                                                    List<sanPhamMua> dsSanPhamMua = gioHang.danhSachSanPhamMua();

                                                                    int i = 0;
                                                                    for(sanPhamMua sanPhamMua : dsSanPhamMua){

                                                                        cell1 = new PdfPCell(new Phrase(String.valueOf(i + 1)));
                                                                        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
                                                                        cell1.setBorder(Rectangle.NO_BORDER);
                                                                        table.addCell(cell1);

                                                                        cell2 = new PdfPCell(new Phrase(sanPhamMua.getTenSanPham()));
                                                                        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                                                                        cell2.setBorder(Rectangle.NO_BORDER);
                                                                        table.addCell(cell2);

                                                                        cell3 = new PdfPCell(new Phrase(df.format(sanPhamMua.getGiaSanPham())+" đ"));
                                                                        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                                        cell3.setBorder(Rectangle.NO_BORDER);
                                                                        table.addCell(cell3);

                                                                        cell4 = new PdfPCell(new Phrase(String.valueOf(sanPhamMua.getSoLuongMua())));
                                                                        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                                        cell4.setBorder(Rectangle.NO_BORDER);
                                                                        table.addCell(cell4);

                                                                        cell5 = new PdfPCell(new Phrase(df.format(sanPhamMua.getThanhTien())+" đ"));
                                                                        cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                                        cell5.setBorder(Rectangle.NO_BORDER);
                                                                        table.addCell(cell5);
                                                                        //c.moveToNext();
                                                                        i++;
                                                                    }

                                                                    cell1 = new PdfPCell(new Phrase(""));
                                                                    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                                    cell1.setCellEvent(new DottedCell());
                                                                    cell1.setBorder(Rectangle.NO_BORDER);
                                                                    table.addCell(cell1);

                                                                    cell2 = new PdfPCell(new Phrase(""));
                                                                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                                    cell2.setCellEvent(new DottedCell());
                                                                    cell2.setBorder(Rectangle.NO_BORDER);
                                                                    table.addCell(cell2);

                                                                    cell3 = new PdfPCell(new Phrase(""));
                                                                    cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                                    cell3.setCellEvent(new DottedCell());
                                                                    cell3.setBorder(Rectangle.NO_BORDER);
                                                                    table.addCell(cell3);

                                                                    cell4 = new PdfPCell(new Phrase("Tong cong: ", boldFont));
                                                                    cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                                                                    cell4.setCellEvent(new DottedCell());
                                                                    cell4.setBorder(Rectangle.NO_BORDER);
                                                                    table.addCell(cell4);

                                                                    cell5 = new PdfPCell(new Phrase(df.format(gioHang.tongTien())+" đ", boldFont));
                                                                    cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                                                    cell5.setCellEvent(new DottedCell());
                                                                    cell5.setBorder(Rectangle.NO_BORDER);
                                                                    table.addCell(cell5);

                                                                    document.add(table);

                                                                    Paragraph p = new Paragraph();
                                                                    p.add("Scan me\n");
                                                                    p.setAlignment(Element.ALIGN_RIGHT);
                                                                    //document.add(p);

                                                                    Image imgQRCode = Image.getInstance(byteData);
                                                                    imgQRCode.setAlignment(Image.RIGHT);
                                                                    imgQRCode.scaleAbsolute(64, 64);
                                                                    p.add(imgQRCode);
                                                                    document.add(p);


                                                                    document.close();
                                                                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                                                    String date = dateFormat.format(Calendar.getInstance().getTime());
                                                                    // Upload to Firebase Storage Server.
                                                                    final List<String> linkPDF = new ArrayList<>();
                                                                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                                                    mAuth.signInAnonymously();
                                                                    FirebaseStorage storage = FirebaseStorage.getInstance("gs://mobile-shop-1535131843934.appspot.com");
                                                                    StorageReference storageRef = storage.getReferenceFromUrl("gs://mobile-shop-1535131843934.appspot.com");
                                                                    StorageReference imagesRef = storageRef.child("images_QRCode/"+"[iduser-"+hd.getId_user()+"]_"+hd.getTen_user()+"/"+date+"/"+idFile+"_"+removeAccent(hd.getTen_user())+".jpg");
                                                                    StorageReference pdf_upload = storageRef.child("pdf_bill/"+"[iduser-"+hd.getId_user()+"]_"+hd.getTen_user()+"/"+date+"/"+idFile+"_"+removeAccent(hd.getTen_user())+".pdf");

                                                                    UploadTask uploadTask = imagesRef.putBytes(byteData);
                                                                    UploadTask uploadTaskPDF = pdf_upload.putFile(Uri.fromFile(file));
                                                                    uploadTaskPDF.addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(context, "PDF Failed", Toast.LENGTH_LONG).show();
                                                                        }
                                                                    })
                                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                        @Override
                                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                            //Toast.makeText(context, "PDF Success", Toast.LENGTH_LONG).show();
                                                                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                            while (!urlTask.isSuccessful());
                                                                            Uri downloadUrl = urlTask.getResult();
                                                                            String urlPDF = String.valueOf(downloadUrl);

                                                                            linkPDF.add(urlPDF);
                                                                        }
                                                                    });
                                                                    final String finalSp_mua = sp_mua;

                                                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception exception) {
                                                                            // Handle unsuccessful uploads
                                                                        }
                                                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                        @Override
                                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                                                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                            while (!urlTask.isSuccessful());
                                                                            Uri downloadUrl = urlTask.getResult();

                                                                            final String username = "chamsockhachhangdtonline@gmail.com";
                                                                            final String password = "Tuminhhau";

                                                                            Properties props = new Properties();
                                                                            props.put("mail.smtp.auth", "true");
                                                                            props.put("mail.smtp.starttls.enable", "true");
                                                                            props.put("mail.smtp.host", "smtp.gmail.com");
                                                                            props.put("mail.smtp.port", "587");

                                                                            Session sessions = Session.getInstance(props,
                                                                                    new javax.mail.Authenticator() {
                                                                                        protected PasswordAuthentication getPasswordAuthentication() {
                                                                                            return new PasswordAuthentication(username, password);
                                                                                        }
                                                                                    });

                                                                            try {
                                                                                MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
                                                                                mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
                                                                                mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
                                                                                mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
                                                                                mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
                                                                                mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
                                                                                CommandMap.setDefaultCommandMap(mc);

                                                                                MimeMessage message = new MimeMessage(sessions);


                                                                                message.setFrom(new InternetAddress("chamsockhachhangdtonline@gmail.com"));
                                                                                message.setRecipients(Message.RecipientType.TO,
                                                                                        InternetAddress.parse(hd.getEmail()));
                                                                                message.setHeader("Content-Type", "text/plain; charset=UTF-8");
                                                                                message.setSubject("Thông tin đơn hàng");
                                                                                //String path = Environment.getExternalStorageDirectory().toString() + "/Image-" + s + ".jpg";

                                                                                Multipart multipart = new MimeMultipart();
                                                                                MimeBodyPart attachementPart = new MimeBodyPart();
                                                                                attachementPart.attachFile(new File(String.valueOf(file)));
                                                                                multipart.addBodyPart(attachementPart);

                                                                                MimeBodyPart textPart = new MimeBodyPart();
                                                                                String noiDung = "Chào bạn, " + hd.getTen_user()
                                                                                        + "<br/> Cảm ơn bạn đã mua hàng của chúng tôi"
                                                                                        + "<br/> Sau đây là chi tiết đơn hàng bạn đã mua: "
                                                                                        + "<br/> <br/>" + " <strong> " + finalSp_mua + "</strong>"
                                                                                        + "<br/> <br/>" + " <strong> Tổng tiền: " + df.format(themVaoGioHang.tongTien()) + " VNĐ</strong>"
                                                                                        + "<br/> Mã QR Code hóa đơn này, tham khảo tại link: " + " <a href='"+String.valueOf(downloadUrl)+"'> Click vào đây.</a>"
                                                                                        + "<br/> File PDF vui lòng tham khảo tập tin đính kèm phía dưới."
                                                                                        + "<br/> <br/> Ban Quản Lý, \nVHN!";
                                                                                textPart.setText(noiDung);
                                                                                textPart.setContent(noiDung, "text/html; charset=utf-8");
                                                                                multipart.addBodyPart(textPart);

                                                                                //message.setText(noiDung);
                                                                                message.setContent(multipart, "multipart/*; charset=utf-8");
                                                                                Transport.send(message);

                                                                                gioHang.clearGioHang();
                                                                                SharedPreferences sharedPreferences = context.getSharedPreferences("shareGioHang", Context.MODE_PRIVATE);
                                                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                                editor.clear();
                                                                                editor.commit();


                                                                            } catch (MessagingException e) {

                                                                                throw new RuntimeException(e);
                                                                            } catch (IOException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        }
                                                                    });





                                                                } catch (WriterException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }

                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }

                                                    }

                                                }
                                            });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            //alertDialog.getWindow().getAttributes().windowAnimations = R.anim.slide_left; //style id

                            // show it
                            alertDialog.show();
                        }
                        else{
                            // chưa login
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Bạn chưa đăng nhập?");
                            builder.setMessage("Bạn có đăng nhập bây giờ không?");
                            builder.setCancelable(false);
                            // if choose no go back.
                            builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            // if choose yes go to login.
                            builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(context, login.class);
                                    intent.putExtra("loginGioHang", "gioHang");
                                    context.startActivity(intent);
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        break;
                    case 5: // VTC
                        themVaoGioHang gioHang2 = SanPhamAdapterNew.gioHang;
                        InitModel initModel = new InitModel();
                        initModel.setSandbox(true);//[Required] set enviroment test, default is true
                        initModel.setAmount(gioHang2.tongTien()); //[Required] your amount
                        initModel.setOrderCode("HOA-DON-THANH-TOAN");//[Required] your order code
                        initModel.setAppID(500002825); //[Required] your AppID that registered with VTC
                        initModel.setSecretKey("Vuhoangnguyen1997"); //[Required] your secret key that registered with VTC
                        initModel.setReceiverAccount("0971844698");//[Required] your account
                        initModel.setDescription("Thanh toán hóa đơn"); //[Option] description
                        initModel.setCurrency(VTCPaySDK.VND);//[Option] set currency, default is VND
                        initModel.setDrawableLogoMerchant(R.mipmap.ic_launcher); //[Option] Your logo
                        //initModel.setHiddenForeignBank(cbIsHiddenPaymentForeignBank.isChecked());//[Option] hidden foreign bank
                        //initModel.setHiddenPayVTC(cbIsHiddenPaymentVTCPay.isChecked());//[Option] hidden pay vtc
                        //initModel.setHiddenDomesticBank(cbIsHiddenPaymentDomesticBank.isChecked());//[Option] hidden domestic bank
                        VTCPaySDK.getInstance().setInitModel(initModel); //init model

                        VTCPaySDK.getInstance().payment(context,
                                new ICallBackPayment() {
                                    @Override
                                    public void onPaymentSuccess(PaymentModel paymentModel) {
                                        // Pay success.

                                    }

                                    @Override
                                    public void onPaymentError(int errorCode, String errorMessage, String bankName) {
                                        Toast.makeText(context,
                                                "Payment error " + errorMessage, Toast.LENGTH_SHORT)
                                                .show();
                                    }

                                    @Override
                                    public void onPaymentCancel() {
                                        Toast.makeText(context, "Payment cancel ",
                                                Toast.LENGTH_SHORT).show();
                                    }

                					/*@Override
                					public void onPaymentError(String error) {
                                        Toast.makeText(context,
                                                    "Payment error " + error, Toast.LENGTH_SHORT)
                                                    .show();
                					}*/
                                });

                        break;
                    case 6: // Momo
                        themVaoGioHang gioHangMoMo = SanPhamAdapterNew.gioHang;
                        MoMoPayment.requestToken((Activity) context, (int)gioHangMoMo.tongTien(), 0, "Hóa đơn thanh toán", "Nguyen", "12345698", "Điện thoại Online", "Nguyên Admin", null);
                        break;
                    case 7:
                        sendInvoice();
                        break;
                }
            }
        });

        return convertView;
    }

    private void sendInvoice() {
        String token = "81tZV654CZXdWfNvJadsJMzjHBgpsbkVg573tSYm5ANG";
        final themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
        final double usd = ConvertToUSD(gioHang.tongTien());
        final ProgressDialog dialog = new ProgressDialog (context);
        dialog.setCancelable(false);
        dialog.setMessage("Vui lòng đợi...");
        dialog.show();
        BitPayAndroid.withToken(token , "https://test.bitpay.com/").then(new BitpayPromiseCallback() {

            @Override
            public void onSuccess(final BitPayAndroid bitpay) {
                AdapterOptionPayment.this.bitpay = bitpay;
                bitpay.createNewInvoice(new com.bitpay.sdk.model.Invoice(usd, "USD")).then(new InvoicePromiseCallback() {
                    @Override
                    public void onSuccess(com.bitpay.sdk.model.Invoice invoice) {

                        bitpayInvoice = invoice;
                        Intent invoiceIntent = new Intent(context, InvoiceActivity.class);
                        Log.d("Invoice", invoice.getPaymentUrls().getBIP21());
                        invoiceIntent.putExtra(InvoiceActivity.INVOICE, invoice);
                        invoiceIntent.putExtra(InvoiceActivity.CLIENT, bitpayInvoice);
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        });
                        ((Activity)context).startActivityForResult(invoiceIntent, 12);
                    }

                    @Override
                    public void onError(BitPayException e) {
                        dialog.dismiss();
                        Log.i("test", e.toString());
                        List<String> url = extractUrls(e.toString());
                        String urlBTC = url.get(0);
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(urlBTC));
                        context.startActivity(i);
                        //AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Creating Invoice").setMessage("Unable to create an invoice. Check your connection and token and try again.").create();
                        //dialog.show();
                    }
                });
            }

            @Override
            public void onError(BitPayException e) {
                dialog.dismiss();
                e.printStackTrace();

                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Creating BitPay Client").setMessage("Unable to connect to the server. Check your connection and token and try again.").create();
                dialog.show();
            }
        });
    }

    private double ConvertToUSD(double vnd){
        return vnd/ DocTyGia.giaBan();
    }

    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            PaymentConfirmation confirm = data.getParcelableExtra(
                    PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null){
                try {
                    Log.i("test", confirm.toJSONObject().toString(4));
                    final  SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);

                    int iduser = sp.getInt("idUser", 0);
                    String details = "", sp_mua = "";
                    final DecimalFormat df = new DecimalFormat("###,###.##");

                    for (sanPhamMua spm : gioHang.danhSachSanPhamMua()){
                        details += "Tên sản phẩm: "+ spm.getTenSanPham() + "- Số lượng : " + spm.getSoLuongMua() + "\n" + "Giá 1 cái: " + df.format(spm.getGiaSanPham()) ;
                        sp_mua += "Tên sản phẩm: "+ spm.getTenSanPham() + "- Số lượng : " + spm.getSoLuongMua() +"<br/>" + "Giá 1 cái: " + df.format(spm.getGiaSanPham());
                    }

                    User u = userDAO.readUser(iduser);

                    final hoaDon hd = new hoaDon();
                    hd.setId_user(iduser);
                    hd.setTen_user(u.getTen_user());
                    hd.setHo_user(u.getHo_user());
                    hd.setEmail(u.getEmail());
                    hd.setDiaChi(diaChi);
                    hd.setSdt(soDienThoai);
                    hd.setThanhPho(u.getThanh_pho());
                    hd.setPhuong(phuong);
                    hd.setQuan(quan);
                    hd.setChiTiet(details);
                    hd.setHinhThucThanhToan("Thanh toán PayPal");

                    int themHoaDon = hoaDonDAO.themHoaDon(hd);
                    if (themHoaDon != 0) {
                        List<sanPhamMua> updateSoLuotMua = gioHang.danhSachSanPhamMua();
                        for(sanPhamMua spm: updateSoLuotMua){
                            int id = spm.getMa_san_pham();
                            Log.i("maSP: ", ""+id);
                            int update = sanPhamDAO.updateSoLuotMua(id);
                        }
                        // Clear all of memories
                        context.startActivity(new Intent(context, MainActivity.class));
                        Toast.makeText(context, "Mua thành công!", Toast.LENGTH_SHORT).show();
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        try {
                            String data1 = "";
                            byte ptext[] = details.getBytes("UTF-8");
                            for (int i = 0; i < ptext.length; i++) {
                                //System.out.print(ptext[i]);
                                data1 += ptext[i];
                            }
                            BitMatrix bitMatrix = multiFormatWriter.encode(details, BarcodeFormat.QR_CODE, 200, 200);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            //imageView.setImageBitmap(bitmap);

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] byteData = baos.toByteArray();
                            final String[] url = {""};
                            /*String alphabet = "abcdefghijklmnopqrstuvwxyz";
                            String s = "";
                            Random random = new Random();
                            int randomLen = 1 + random.nextInt(9);
                            for (int i = 0; i < randomLen; i++) {
                                char c = alphabet.charAt(random.nextInt(26));
                                s += c;
                            }*/

                            int idFile = hoaDonDAO.lastID()+1;
                            // Create PDF
                            Document document = new Document();

                            final String path = context.getFilesDir().getAbsolutePath()+"/Dir";
                            File dir = new File(path);
                            if(!dir.exists())
                                dir.mkdirs();

                            String title = idFile+"_"+removeAccent(hd.getTen_user())+".pdf";

                            final File file = new File(dir, title);
                            FileOutputStream fOut = new FileOutputStream(file);

                            PdfWriter.getInstance(document, fOut);
                            document.open();

                            DottedLineSeparator separatorTop = new DottedLineSeparator();
                            separatorTop.setPercentage(59500f / 523f);
                            Chunk linebreakTop = new Chunk(separatorTop);
                            document.add(linebreakTop);

                            Paragraph p1 = new Paragraph("Cua hang dien thoai online");
                            p1.setAlignment(Element.ALIGN_CENTER);
                            document.add(p1);

                            Paragraph p2 = new Paragraph("Phone: 9943123999");
                            p2.setAlignment(Element.ALIGN_CENTER);
                            document.add(p2);

                            Paragraph p3 = new Paragraph("Email : chamsockhachhangdtonline@gmail.com");
                            p3.setAlignment(Element.ALIGN_CENTER);
                            document.add(p3);


                            DottedLineSeparator separator = new DottedLineSeparator();
                            separator.setPercentage(59500f / 523f);
                            Chunk linebreak = new Chunk(separator);
                            document.add(linebreak);

                            Paragraph paragraph = new Paragraph("Ma hoa don: " + idFile);
                            paragraph.setAlignment(Element.ALIGN_LEFT);
                            document.add(paragraph);

                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            String billCreated = sdf.format(new Date());

                            paragraph = new Paragraph("Ngay tao hoa don: " + billCreated );
                            paragraph.setAlignment(Element.ALIGN_LEFT);
                            document.add(paragraph);

                            paragraph = new Paragraph("Nguoi nhan: " + removeAccent(hd.getTen_user()));
                            paragraph.setAlignment(Element.ALIGN_LEFT);
                            document.add(paragraph);

                            paragraph = new Paragraph("SDT: " + hd.getSdt());
                            paragraph.setAlignment(Element.ALIGN_LEFT);
                            document.add(paragraph);

                            paragraph = new Paragraph("Dia chi giao hang: " + diaChi);
                            paragraph.setAlignment(Element.ALIGN_LEFT);
                            document.add(paragraph);

                            paragraph = new Paragraph("Hinh thuc thanh toan: " + removeAccent(hd.getHinhThucThanhToan()));
                            paragraph.setAlignment(Element.ALIGN_LEFT);
                            document.add(paragraph);

                            DottedLineSeparator separator2 = new DottedLineSeparator();
                            separator2.setPercentage(59500f / 523f);
                            Chunk linebreak2 = new Chunk(separator2);
                            document.add(linebreak2);

                            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);


                            PdfPTable table = new PdfPTable(5);
                            table.setTotalWidth(300f);
                            table.setHorizontalAlignment(Element.ALIGN_CENTER);

                            PdfPCell cell1 = new PdfPCell(new Phrase("STT", boldFont));
                            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell1.setCellEvent(new DottedCell());
                            cell1.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell1);

                            PdfPCell cell2 = new PdfPCell(new Phrase("Ten san pham", boldFont));
                            cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell2.setCellEvent(new DottedCell());
                            cell2.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell2);

                            PdfPCell cell3 = new PdfPCell(new Phrase("Gia 1 cai", boldFont));
                            cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            cell3.setCellEvent(new DottedCell());
                            cell3.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell3);

                            PdfPCell cell4 = new PdfPCell(new Phrase("So luong", boldFont));
                            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell4.setCellEvent(new DottedCell());
                            cell4.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell4);

                            PdfPCell cell5 = new PdfPCell(new Phrase("Tong", boldFont));
                            cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            cell5.setCellEvent(new DottedCell());
                            cell5.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell5);
                            //table.setHeaderRows(1);

                            final themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
                            List<sanPhamMua> dsSanPhamMua = gioHang.danhSachSanPhamMua();

                            int i = 0;
                            for(sanPhamMua sanPhamMua : dsSanPhamMua){

                                cell1 = new PdfPCell(new Phrase(String.valueOf(i + 1)));
                                cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
                                cell1.setBorder(Rectangle.NO_BORDER);
                                table.addCell(cell1);

                                cell2 = new PdfPCell(new Phrase(sanPhamMua.getTenSanPham()));
                                cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                                cell2.setBorder(Rectangle.NO_BORDER);
                                table.addCell(cell2);

                                cell3 = new PdfPCell(new Phrase(df.format(sanPhamMua.getGiaSanPham())+" đ"));
                                cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                cell3.setBorder(Rectangle.NO_BORDER);
                                table.addCell(cell3);

                                cell4 = new PdfPCell(new Phrase(String.valueOf(sanPhamMua.getSoLuongMua())));
                                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell4.setBorder(Rectangle.NO_BORDER);
                                table.addCell(cell4);

                                cell5 = new PdfPCell(new Phrase(df.format(sanPhamMua.getThanhTien())+" đ"));
                                cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                cell5.setBorder(Rectangle.NO_BORDER);
                                table.addCell(cell5);
                                //c.moveToNext();
                                i++;
                            }

                            cell1 = new PdfPCell(new Phrase(""));
                            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell1.setCellEvent(new DottedCell());
                            cell1.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell1);

                            cell2 = new PdfPCell(new Phrase(""));
                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell2.setCellEvent(new DottedCell());
                            cell2.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell2);

                            cell3 = new PdfPCell(new Phrase(""));
                            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell3.setCellEvent(new DottedCell());
                            cell3.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell3);

                            cell4 = new PdfPCell(new Phrase("Tong cong: ", boldFont));
                            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell4.setCellEvent(new DottedCell());
                            cell4.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell4);

                            cell5 = new PdfPCell(new Phrase(df.format(gioHang.tongTien())+" đ", boldFont));
                            cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            cell5.setCellEvent(new DottedCell());
                            cell5.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell5);

                            document.add(table);

                            Paragraph p = new Paragraph();
                            p.add("Scan me\n");
                            p.setAlignment(Element.ALIGN_RIGHT);
                            //document.add(p);

                            Image imgQRCode = Image.getInstance(byteData);
                            imgQRCode.setAlignment(Image.RIGHT);
                            imgQRCode.scaleAbsolute(64, 64);
                            p.add(imgQRCode);
                            document.add(p);


                            document.close();

                            // Upload to Firebase Storage Server.
                            final List<String> linkPDF = new ArrayList<>();
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            mAuth.signInAnonymously();
                            FirebaseStorage storage = FirebaseStorage.getInstance("gs://mobile-shop-1535131843934.appspot.com");
                            StorageReference storageRef = storage.getReferenceFromUrl("gs://mobile-shop-1535131843934.appspot.com");
                            StorageReference imagesRef = storageRef.child("images_QRCode/"+"[iduser-"+hd.getId_user()+"]_"+hd.getTen_user()+"/"+idFile+"_"+removeAccent(hd.getTen_user())+".jpg");
                            StorageReference pdf_upload = storageRef.child("pdf_bill/"+"[iduser-"+hd.getId_user()+"]_"+hd.getTen_user()+"/"+idFile+"_"+removeAccent(hd.getTen_user())+".pdf");

                            UploadTask uploadTask = imagesRef.putBytes(byteData);
                            UploadTask uploadTaskPDF = pdf_upload.putFile(Uri.fromFile(file));
                            uploadTaskPDF.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "PDF Failed", Toast.LENGTH_LONG).show();
                                }
                            })
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            //Toast.makeText(context, "PDF Success", Toast.LENGTH_LONG).show();
                                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                            while (!urlTask.isSuccessful());
                                            Uri downloadUrl = urlTask.getResult();
                                            String urlPDF = String.valueOf(downloadUrl);

                                            linkPDF.add(urlPDF);
                                        }
                                    });
                            final String finalSp_mua = sp_mua;

                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful());
                                    Uri downloadUrl = urlTask.getResult();

                                    final String username = "chamsockhachhangdtonline@gmail.com";
                                    final String password = "Tuminhhau";

                                    Properties props = new Properties();
                                    props.put("mail.smtp.auth", "true");
                                    props.put("mail.smtp.starttls.enable", "true");
                                    props.put("mail.smtp.host", "smtp.gmail.com");
                                    props.put("mail.smtp.port", "587");

                                    Session sessions = Session.getInstance(props,
                                            new javax.mail.Authenticator() {
                                                protected PasswordAuthentication getPasswordAuthentication() {
                                                    return new PasswordAuthentication(username, password);
                                                }
                                            });

                                    try {
                                        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
                                        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
                                        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
                                        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
                                        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
                                        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
                                        CommandMap.setDefaultCommandMap(mc);

                                        MimeMessage message = new MimeMessage(sessions);


                                        message.setFrom(new InternetAddress("chamsockhachhangdtonline@gmail.com"));
                                        message.setRecipients(Message.RecipientType.TO,
                                                InternetAddress.parse(hd.getEmail()));
                                        message.setHeader("Content-Type", "text/plain; charset=UTF-8");
                                        message.setSubject("[MobileShop] Thông tin đơn hàng");
                                        //String path = Environment.getExternalStorageDirectory().toString() + "/Image-" + s + ".jpg";

                                        Multipart multipart = new MimeMultipart();
                                        MimeBodyPart attachementPart = new MimeBodyPart();
                                        attachementPart.attachFile(new File(String.valueOf(file)));
                                        multipart.addBodyPart(attachementPart);

                                        MimeBodyPart textPart = new MimeBodyPart();
                                        String noiDung = "Chào bạn, " + hd.getTen_user()
                                                + "<br/> Cảm ơn bạn đã mua hàng của chúng tôi"
                                                + "<br/> Sau đây là chi tiết đơn hàng bạn đã mua: "
                                                + "<br/> <br/>" + " <strong> " + finalSp_mua + "</strong>"
                                                + "<br/> <br/>" + " <strong> Tổng tiền: " + df.format(gioHang.tongTien()) + " VNĐ</strong>"
                                                + "<br/> Mã QR Code hóa đơn này, tham khảo tại link: " + " <a href='"+String.valueOf(downloadUrl)+"'> Click vào đây.</a>"
                                                + "<br/> File PDF vui lòng tham khảo tập tin đính kèm phía dưới."
                                                + "<br/> <br/> Ban Quản Lý, \nVHN!";
                                        textPart.setText(noiDung);
                                        textPart.setContent(noiDung, "text/html; charset=utf-8");
                                        multipart.addBodyPart(textPart);

                                        //message.setText(noiDung);
                                        message.setContent(multipart, "multipart/*; charset=utf-8");
                                        Transport.send(message);

                                        gioHang.clearGioHang();
                                        SharedPreferences sharedPreferences = context.getSharedPreferences("shareGioHang", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.clear();
                                        editor.commit();


                                    } catch (MessagingException e) {

                                        throw new RuntimeException(e);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });





                        } catch (WriterException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (BadElementException e) {
                            e.printStackTrace();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                    }


                } catch (JSONException e) {
                    Log.e("test", "no confirmation data: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("test", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("test", "Invalid payment / config set");
        }
    }

    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static class DottedCell implements PdfPCellEvent {
        public void cellLayout(PdfPCell cell, Rectangle position,
                               PdfContentByte[] canvases) {
            PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
            canvas.setLineDash(3f, 3f);
            canvas.moveTo(position.getLeft(), position.getTop());
            canvas.lineTo(position.getRight(), position.getTop());
            canvas.moveTo(position.getLeft(), position.getBottom());
            canvas.lineTo(position.getRight(), position.getBottom());
            canvas.stroke();
        }
    }



}
