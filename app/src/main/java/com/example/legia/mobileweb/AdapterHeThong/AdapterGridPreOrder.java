package com.example.legia.mobileweb.AdapterHeThong;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legia.mobileweb.DAO.userPreOrderDAO;
import com.example.legia.mobileweb.DTO.sanPham;
import com.example.legia.mobileweb.DTO.sanPhamPreOrder;
import com.example.legia.mobileweb.DTO.userPreOrder;
import com.example.legia.mobileweb.Database.Firebase.DAO.countOrder;
import com.example.legia.mobileweb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.CountryCodePicker;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
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

public class AdapterGridPreOrder extends BaseAdapter {
    private Context context;
    private List<sanPhamPreOrder> dsSanPhamPreOrder;
    static String phoneTest = "+16505554567";
    static String testCode = "123456";

    public AdapterGridPreOrder(Context context, List<sanPhamPreOrder> dsSanPhamPreOrder) {
        this.context = context;
        this.dsSanPhamPreOrder = dsSanPhamPreOrder;
    }

    @Override
    public int getCount() {
        return dsSanPhamPreOrder.size();
    }

    @Override
    public Object getItem(int position) {
        return dsSanPhamPreOrder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_newproduct,parent,false);
        }
        final sanPhamPreOrder sanPhamPreOrder = (sanPhamPreOrder) this.getItem(position);

        ImageView img =  convertView.findViewById(R.id.imgSanPhamNew);
        TextView price =  convertView.findViewById(R.id.txtGiaSanPhamNew);
        TextView name =  convertView.findViewById(R.id.txtTenSanPhamNew);
        TextView releaseDay = convertView.findViewById(R.id.txtNgayRaMatNew);
        Button btnOrder = convertView.findViewById(R.id.btnOrder);

        //BIND
        try {
            Timestamp release = sanPhamPreOrder.getNgay_du_kien();
            SimpleDateFormat monthDayYearformatter = new SimpleDateFormat(
                    "dd-MM-yyyy");
            String ngayRaMat = monthDayYearformatter.format(release);

            DecimalFormat df = new DecimalFormat("###,###.##");
            // a
            price.setText(df.format(sanPhamPreOrder.getGia_san_pham())+" VNĐ");
            name.setText(sanPhamPreOrder.getTen_san_pham());
            releaseDay.setText("Ngày dự kiến: "+"\n"+ngayRaMat.toString());

            Blob b = sanPhamPreOrder.getHinh_san_pham();

            int blobLength = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength);
            Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes,0,blobAsBytes.length);
            img.setImageBitmap(btm);

            btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Verify OTP
                    LayoutInflater liVerify = LayoutInflater.from(context);
                    final View formXacThuc = liVerify.inflate(R.layout.layout_captcha, null);

                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                            context);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(formXacThuc);
                    //final CountryCodePicker ccp = formXacThuc.findViewById(R.id.ccp);
                    final EditText txtSDT = formXacThuc.findViewById(R.id.txtSDT_OTP);
                    final EditText txtOTP = formXacThuc.findViewById(R.id.txtOTP);
                    final LinearLayout layoutSDT = formXacThuc.findViewById(R.id.layoutSDT);
                    final LinearLayout layoutInput = formXacThuc.findViewById(R.id.layoutInput);
                    final LinearLayout layoutXacThuc = formXacThuc.findViewById(R.id.layoutXacThuc);
                    final Button btnSendOTP = formXacThuc.findViewById(R.id.btnSendOTP);
                    final Button btnReSendOTP = formXacThuc.findViewById(R.id.btnResend);
                    final Button btnVerify = formXacThuc.findViewById(R.id.btnXacThuc);
                    final List<String> mVerificationId = new ArrayList<>();

                    layoutInput.setVisibility(View.INVISIBLE);
                    layoutXacThuc.setVisibility(View.INVISIBLE);

                    final String sdt = txtSDT.getText().toString();

                    final PhoneAuthProvider.OnVerificationStateChangedCallbacks[] mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks[1];
                    mCallbacks[0] = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(PhoneAuthCredential credential) {
                            // Get OTP Code and verify
                            final String code = credential.getSmsCode();
                            String id = "";
                            for(String s : mVerificationId){
                                id = s;
                            }
                            //Toast.makeText(context,id, Toast.LENGTH_LONG).show();
                            if(code!=null){
                                txtOTP.setText(code);

                                final String finalId = id;
                                btnVerify.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(finalId, code);
                                        FirebaseAuth.getInstance().signInWithCredential(credential)
                                                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(context, sanPhamPreOrder.getTen_san_pham(), Toast.LENGTH_SHORT).show();
                                                            LayoutInflater li;
                                                            android.support.v7.app.AlertDialog.Builder build;
                                                            android.support.v7.app.AlertDialog alertDialogOrder;
                                                            final View view;

                                                            li = LayoutInflater.from(context);
                                                            view = li.inflate(R.layout.layout_preorder, null);

                                                            final TextView txtCount = view.findViewById(R.id.txtCount);
                                                            final TextView txtHoUser = view.findViewById(R.id.txtHoUser);
                                                            final TextView txtTenUser = view.findViewById(R.id.txtTenUser);
                                                            final TextView txtEmail = view.findViewById(R.id.txtEmail);
                                                            final TextView txtSDTOrder = view.findViewById(R.id.txtSDT);
                                                            txtSDTOrder.setText(txtSDT.getText().toString());
                                                            txtSDTOrder.setEnabled(false);
                                                            final TextView txtDay = view.findViewById(R.id.txtDay);
                                                            final TextView txtHour = view.findViewById(R.id.txtHour);
                                                            final TextView txtMinute = view.findViewById(R.id.txtMinute);
                                                            final TextView txtSecond = view.findViewById(R.id.txtSecond);

                                                            CountDownTimer cTimer = null;
                                                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                                            Timestamp expire = dsSanPhamPreOrder.get(position).getNgay_du_kien();
                                                            long countDown = expire.getTime() - timestamp.getTime();
                                                            if(countDown<=0){
                                                                txtHoUser.setText("Đã hết thời gian cho phép");
                                                                txtEmail.setEnabled(false);
                                                                txtSDTOrder.setEnabled(false);
                                                                txtTenUser.setEnabled(false);
                                                                txtHoUser.setEnabled(false);
                                                            }
                                                            else {
                                                                cTimer = new CountDownTimer(countDown, 1000) {
                                                                    public void onTick(long millisUntilFinished) {
                                                                        long Days = millisUntilFinished / (24 * 60 * 60 * 1000);
                                                                        long Hours = millisUntilFinished / (60 * 60 * 1000) % 24;
                                                                        long Minutes = millisUntilFinished / (60 * 1000) % 60;
                                                                        long Seconds = millisUntilFinished / 1000 % 60;

                                                                        txtDay.setText(String.format("%02d", Days));
                                                                        txtHour.setText(String.format("%02d", Hours));
                                                                        txtMinute.setText(String.format("%02d", Minutes));
                                                                        txtSecond.setText(String.format("%02d", Seconds));

                                                                    }
                                                                    public void onFinish() {
                                                                        txtHoUser.setText("Đã hết thời gian cho phép");
                                                                        txtEmail.setEnabled(false);
                                                                        txtSDTOrder.setEnabled(false);
                                                                        txtTenUser.setEnabled(false);
                                                                        txtHoUser.setEnabled(false);
                                                                    }
                                                                };
                                                                cTimer.start();
                                                            }

                                                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("countOrder");
                                                            final DatabaseReference getValue = db.child("count"+sanPhamPreOrder.getHang_san_xuat()).child(sanPhamPreOrder.getTen_san_pham());
                                                            getValue.addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    int count = dataSnapshot.getValue(Integer.class);
                                                                    txtCount.setText(String.valueOf(count));
                                                                    if(count>=100){
                                                                        txtHoUser.setText("Đã đủ số lần đặt sản phẩm");
                                                                        txtEmail.setEnabled(false);
                                                                        txtSDTOrder.setEnabled(false);
                                                                        txtTenUser.setEnabled(false);
                                                                        txtHoUser.setEnabled(false);
                                                                    }
                                                                    else{
                                                                        txtEmail.setEnabled(true);
                                                                        txtSDTOrder.setEnabled(true);
                                                                        txtTenUser.setEnabled(true);
                                                                        txtHoUser.setEnabled(true);
                                                                        txtHoUser.setText("");
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });

                                                            build = new android.support.v7.app.AlertDialog.Builder(context);
                                                            build.setView(view);
                                                            build.setCancelable(false);
                                                            build.setNegativeButton("Quay về", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    return;
                                                                }
                                                            });
                                                            build.setPositiveButton("Đặt hàng", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    String ho_user = txtHoUser.getText().toString();
                                                                    String ten_user = txtTenUser.getText().toString();
                                                                    String email = txtEmail.getText().toString();
                                                                    int sdt = Integer.parseInt(txtSDTOrder.getText().toString().replaceAll("[^0-9]+",""));
                                                                    if(ho_user.length()==0){
                                                                        Toast.makeText(context, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                                                                        return;
                                                                    }
                                                                    else if(ten_user.length()==0){
                                                                        Toast.makeText(context, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                                                                        return;
                                                                    }
                                                                    else if(email.length()==0){
                                                                        Toast.makeText(context, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                                                                        return;
                                                                    }else if(txtSDTOrder.getText().toString().length()==0){
                                                                        Toast.makeText(context, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                                                                        return;
                                                                    }
                                                                    else{
                                                                        userPreOrder userPreOrder = userPreOrderDAO.checkUser(sdt);
                                                                        if(userPreOrder==null){
                                                                            int preorder =0;
                                                                            userPreOrder.setHo_user(ho_user);
                                                                            userPreOrder.setTen_user(ten_user);
                                                                            userPreOrder.setSdt(sdt);
                                                                            userPreOrder.setEmail(email);

                                                                            preorder = userPreOrderDAO.preOrder(userPreOrder);
                                                                            if(preorder!=0){
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
                                                                                            InternetAddress.parse(userPreOrder.getEmail()));
                                                                                    message.setHeader("Content-Type", "text/plain; charset=UTF-8");
                                                                                    message.setSubject("Thông tin đơn đặt hàng");
                                                                                    //String path = Environment.getExternalStorageDirectory().toString() + "/Image-" + s + ".jpg";

                                                                                    Multipart multipart = new MimeMultipart();
                                                                                    MimeBodyPart attachementPart = new MimeBodyPart();
                                                                                    //attachementPart.attachFile(new File(String.valueOf(file)));
                                                                                    //multipart.addBodyPart(attachementPart);

                                                                                    MimeBodyPart textPart = new MimeBodyPart();
                                                                                    String noiDung = "Chào bạn, " + userPreOrder.getTen_user()
                                                                                            + "<br/> Cảm ơn bạn đã đặt hàng với chúng tôi"
                                                                                            + "<br/> Sau đây là chi tiết đơn hàng bạn đã đặt: "
                                                                                            + "<br/> <br/>" + " <strong> " + dsSanPhamPreOrder.get(position).getTen_san_pham() + "</strong>"
                                                                                            + "<br/> " + " <strong> Ngày dự kiến có hàng: " + dsSanPhamPreOrder.get(position).getNgay_du_kien() + "</strong>"
                                                                                            + "<br/> <br/>" + " <strong> Chúng tôi sẽ liên hệ với bạn trong thời gian sớm nhất.</strong>"
                                                                                            + "<br/> <br/> Ban Quản Lý, \nVHN!";
                                                                                    textPart.setText(noiDung);
                                                                                    textPart.setContent(noiDung, "text/html; charset=utf-8");
                                                                                    multipart.addBodyPart(textPart);

                                                                                    //message.setText(noiDung);
                                                                                    message.setContent(multipart, "multipart/*; charset=utf-8");
                                                                                    Transport.send(message);

                                                                                    countOrder.updateValue(sanPhamPreOrder.getHang_san_xuat(), sanPhamPreOrder.getTen_san_pham());

                                                                                } catch (MessagingException e) {

                                                                                    throw new RuntimeException(e);
                                                                                }
                                                                            }
                                                                        }
                                                                        else {
                                                                            // Already
                                                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                                            builder.setMessage("Bạn đã đặt hàng sản phẩm này trước đó.");
                                                                            builder.setCancelable(false);
                                                                            builder.setNegativeButton("Quay về", new DialogInterface.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                                    dialogInterface.dismiss();
                                                                                }
                                                                            });
                                                                            AlertDialog alertDialog = builder.create();
                                                                            alertDialog.show();
                                                                        }
                                                                    }


                                                                }
                                                            });

                                                            alertDialogOrder = build.create();
                                                            alertDialogOrder.show();
                                                        }
                                                        else{
                                                            String message = "Somthing is wrong, we will fix it soon...";

                                                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                                                message = "Invalid code entered...";
                                                            }
                                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                                        }

                                                    }
                                                });
                                    }
                                });

                            }

                        }

                        @Override
                        public void onVerificationFailed(FirebaseException e) {

                        }

                        @Override
                        public void onCodeSent(String verificationId,
                                               PhoneAuthProvider.ForceResendingToken token) {
                            super.onCodeSent(verificationId, token);
                            Toast.makeText(context, "Code sent", Toast.LENGTH_LONG).show();
                            Log.i("otp:", verificationId + " - " + token );
                            mVerificationId.add(verificationId);
                            txtSDT.setEnabled(false);
                            btnSendOTP.setEnabled(false);
                            txtSDT.setFocusable(false);
                            layoutInput.setVisibility(View.VISIBLE);
                            layoutXacThuc.setVisibility(View.VISIBLE);
                        }
                    };

                    btnReSendOTP.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //final String codeCCP = ccp.getSelectedCountryCode().toString();
                            Log.i("test", txtSDT.getText().toString());
                            FirebaseAuth.getInstance().useAppLanguage();
                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    txtSDT.getText().toString(),        // Phone number to verify
                                    60,                 // Timeout duration
                                    TimeUnit.SECONDS,   // Unit of timeout
                                    (Activity) context,               // Activity (for callback binding)
                                    mCallbacks[0]
                            );
                        }
                    });

                    btnSendOTP.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseAuth.getInstance().useAppLanguage();
                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    txtSDT.getText().toString(),        // Phone number to verify
                                    60,                 // Timeout duration
                                    TimeUnit.SECONDS,   // Unit of timeout
                                    (Activity) context,               // Activity (for callback binding)
                                    mCallbacks[0]
                            );

                        }
                    });



                    // create alert dialog
                    android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    //alertDialog.getWindow().getAttributes().windowAnimations = R.anim.slide_left; //style id

                    // show it
                    alertDialog.show();
                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Details
                    //countOrder.updateValue(sanPhamPreOrder.getHang_san_xuat(), sanPhamPreOrder.getTen_san_pham());
                    String url = "";
                    switch (position){
                        case 0:
                            url = "https://youtu.be/uJkOP1-v9B4";
                            break;
                        case 1:
                            url = "https://youtu.be/uJkOP1-v9B4";
                            break;
                        case 2:
                            url = "https://youtu.be/9m_K2Yg7wGQ";
                            break;
                        case 3:
                            url = "https://youtu.be/6EiI5_-7liQ";
                            break;
                        case 4:
                            url = "https://youtu.be/nrfz0nG06Ss";
                            break;

                    }
                    LayoutInflater li = LayoutInflater.from(context);
                    final View content = li.inflate(R.layout.layout_trailer, null);
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                            context);
                    alertDialogBuilder.setView(content);
                    final WebView myWebView = content.findViewById(R.id.trailer);
                    myWebView.setWebViewClient(new WebViewClient());
                    myWebView.getSettings().setJavaScriptEnabled(true);
                    myWebView.setWebChromeClient(new WebChromeClient());

                    try {
                        Log.e("Status", "tried url");
                        myWebView.loadUrl(url);
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("Status", "In Run():");
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Status", "exception");
                    }
                    // create alert dialog
                    android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    //alertDialog.getWindow().getAttributes().windowAnimations = R.anim.slide_left; //style id

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
