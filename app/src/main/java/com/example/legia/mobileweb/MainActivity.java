package com.example.legia.mobileweb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.legia.mobileweb.AdapterHeThong.AdapterOptionPayment;
import com.example.legia.mobileweb.AdapterSanPham.BottomNavigationViewHelper;
import com.example.legia.mobileweb.AdapterSanPham.SanPhamAdapterNew;
import com.example.legia.mobileweb.AdapterSanPham.SlidingAdapter;
import com.example.legia.mobileweb.CheckInternet.CheckInternet;
import com.example.legia.mobileweb.DAO.hoaDonDAO;
import com.example.legia.mobileweb.DAO.sanPhamDAO;
import com.example.legia.mobileweb.DAO.theTichDiemDAO;
import com.example.legia.mobileweb.DAO.themVaoGioHang;
import com.example.legia.mobileweb.DAO.userDAO;
import com.example.legia.mobileweb.DTO.User;
import com.example.legia.mobileweb.DTO.hoaDon;
import com.example.legia.mobileweb.DTO.sanPham;
import com.example.legia.mobileweb.DTO.sanPhamMua;
import com.example.legia.mobileweb.DTO.theKhachHang;
import com.example.legia.mobileweb.Encryption.encrypt;
import com.example.legia.mobileweb.FragmentAll.FragmentCart;
import com.example.legia.mobileweb.FragmentAll.FragmentHome;
import com.example.legia.mobileweb.FragmentAll.FragmentMenu;
import com.example.legia.mobileweb.FragmentAll.FragmentNews;
import com.github.arturogutierrez.Badges;
import com.github.arturogutierrez.BadgesNotSupportedException;
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
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

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

import q.rorbin.badgeview.QBadgeView;
import shortbread.Shortbread;
import shortbread.Shortcut;

import static android.support.constraint.Constraints.TAG;
import static com.example.legia.mobileweb.AdapterSanPham.SanPhamAdapterNew.gioHang;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView menuBar;
    @Shortcut(id = "cart", icon = R.drawable.carticonbread, shortLabel = "Giỏ hàng")
    public void openCart(){
        themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
        int count = gioHang.countSoLuongMua();
        if(count>0){
//            startActivity(new Intent(this, cart.class));
        }
        else{
            TastyToast.makeText(this, "Chưa có sản phẩm trong giỏ hàng", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
        }
    }

    @Shortcut(id = "login", icon = R.drawable.loginbread, shortLabel = "Đăng nhập")
    public void openLogin(){
        SharedPreferences loginFacebook = getSharedPreferences("loginWithFb", MODE_PRIVATE);
        SharedPreferences loginGoogle = getSharedPreferences("loginWithGoogle", MODE_PRIVATE);
        SharedPreferences loginNormal = getSharedPreferences("userLogin", MODE_PRIVATE);
        if(loginFacebook.getBoolean("isLoginWithFB", false)){
            TastyToast.makeText(this, "Bạn đã đăng nhập trước đó", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
        }
        else if(loginGoogle.getBoolean("isLoginWithGoogle",false)){
            TastyToast.makeText(this, "Bạn đã đăng nhập trước đó", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
        }
        else if(loginNormal.getBoolean("isLogin",false)){
            TastyToast.makeText(this, "Bạn đã đăng nhập trước đó", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
        }
        else{
            startActivity(new Intent(this, login.class));
        }
    }

    @Shortcut(id = "scan", icon = R.drawable.scancode, shortLabel = "Quét mã QR/Barcode")
    public void openScan(){
        startActivity(new Intent(this, scancode.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        Shortbread.create(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        menuBar = findViewById(R.id.menuBar);

        BottomNavigationViewHelper.disableShiftMode(menuBar);

        if (this.getIntent().getExtras() != null &&
                this.getIntent().getExtras().containsKey("count")) {
            themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
            int count = gioHang.countSoLuongMua();
            BottomNavigationMenuView bottomNavigationMenuView =
                    (BottomNavigationMenuView) menuBar.getChildAt(0);

            MenuItem menuItem = menuBar.getMenu().getItem(3);
            menuItem.setChecked(true);

            View v = bottomNavigationMenuView.getChildAt(3); // number of menu from left
            if(count > 0){
                new QBadgeView(this).bindTarget(v).setBadgeNumber(count);
            }
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadFragment(new FragmentCart());
                        }
                    });
                }
            });
            t.start();        }
        else if(this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey("countGioHang")){
            themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;
            int count = gioHang.countSoLuongMua();
            BottomNavigationMenuView bottomNavigationMenuView =
                    (BottomNavigationMenuView) menuBar.getChildAt(0);
            View v = bottomNavigationMenuView.getChildAt(3); // number of menu from left
            if(count > 0){
                new QBadgeView(this).bindTarget(v).setBadgeNumber(count);
            }
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadFragment(new FragmentHome());
                        }
                    });
                }
            });
            t.start();        }
        else{
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadFragment(new FragmentHome());
                        }
                    });
                }
            });
            t.start();
        }
        menuBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.homePage:
                        fragment = new FragmentHome();
                        break;
                    case R.id.app_bar_search:
                        startActivity(new Intent(MainActivity.this, search.class));
                        //fragment = new FragmentSearch();
                        break;
                    case R.id.news:
                        fragment = new FragmentNews();
                        break;
                    case R.id.cart:
                        // if empty cart show notification.
                        fragment = new FragmentCart();
                        break;
                    case R.id.menu:
                        fragment = new FragmentMenu();
                        break;
                }
                return loadFragment(fragment);
            }
        });


    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_left,
                        R.anim.slide_out_left,
                        R.anim.slide_right,
                        R.anim.slide_out_right
                    )
                    .replace(R.id.frameMain, fragment)
                    .commit();
            return true;
        }
        return false;
    }



    /*
    PAYPAL CALLBACK
     */

    String diaChi, phuong, quan;
    int sdt;
    themVaoGioHang gioHang = SanPhamAdapterNew.gioHang;

    // After pay with PayPal, get result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            PaymentConfirmation confirm = data.getParcelableExtra(
                    PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null){
                try {
                    Log.i("test", confirm.toJSONObject().toString(4));
                    final  SharedPreferences sp = getSharedPreferences("userLogin", MODE_PRIVATE);

                    int iduser = sp.getInt("idUser", 0);
                    String details = "", sp_mua = "";
                    final DecimalFormat df = new DecimalFormat("###,###.##");

                    for (sanPhamMua spm : gioHang.danhSachSanPhamMua()){
                        details += "Tên sản phẩm: "+ spm.getTenSanPham() + "- Số lượng : " + spm.getSoLuongMua() + "\n" + "Giá 1 cái: " + df.format(spm.getGiaSanPham()) ;
                        sp_mua += "Tên sản phẩm: "+ spm.getTenSanPham() + "- Số lượng : " + spm.getSoLuongMua() +"<br/>" + "Giá 1 cái: " + df.format(spm.getGiaSanPham());
                    }

                    User u = userDAO.readUser(iduser);
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

                    String soDienThoai = "";
                    SharedPreferences sharedPreferencesInfo = getSharedPreferences("info_khach_hang", MODE_PRIVATE);
                    diaChi = sharedPreferencesInfo.getString("diaChi", "");
                    phuong = sharedPreferencesInfo.getString("phuong", "");
                    quan = sharedPreferencesInfo.getString("quan", "");
                    soDienThoai = sharedPreferencesInfo.getString("sdt", "");

                    final String key = "Bar12345Bar12345"; // 128 bit key
                    String initVector = "RandomInitVector"; // 16 bytes IV


                    // Save to db
                    final hoaDon hd = new hoaDon();
                    hd.setId_user(iduser);
                    hd.setTen_user(u.getTen_user());
                    hd.setHo_user(u.getHo_user());
                    hd.setEmail(u.getEmail());
                    hd.setDiaChi(diaChi);
                    hd.setSdt(Integer.parseInt(soDienThoai));
                    hd.setThanhPho(u.getThanh_pho());
                    hd.setPhuong(encrypt.encryptAES(key, initVector, phuong));
                    hd.setQuan(encrypt.encryptAES(key, initVector, quan));
                    hd.setChiTiet(encrypt.encryptAES(key, initVector, details));
                    hd.setHinhThucThanhToan("Thanh toán PayPal");
                    int themHoaDon = hoaDonDAO.themHoaDon(hd);
                    if (themHoaDon != 0) {
                        // Clear all of memories
                        startActivity(new Intent(this, MainActivity.class));
                        Toast.makeText(this, "Mua thành công!", Toast.LENGTH_SHORT).show();
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        try {
                            String data1 = "";
                            byte ptext[] = details.getBytes("UTF8");
                            for (int i = 0; i < ptext.length; i++) {
                                //System.out.print(ptext[i]);
                                data1 += ptext[i];
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

                            final String path = this.getFilesDir().getAbsolutePath()+"/Dir";
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
                            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);

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

                            paragraph = new Paragraph("Dia chi giao hang: " + encrypt.decryptAES(key, initVector, diaChi));
                            paragraph.setAlignment(Element.ALIGN_LEFT);
                            document.add(paragraph);

                            paragraph = new Paragraph("Hinh thuc thanh toan: " + removeAccent(hd.getHinhThucThanhToan()), boldFont);
                            paragraph.setAlignment(Element.ALIGN_LEFT);
                            document.add(paragraph);

                            DottedLineSeparator separator2 = new DottedLineSeparator();
                            separator2.setPercentage(59500f / 523f);
                            Chunk linebreak2 = new Chunk(separator2);
                            document.add(linebreak2);



                            PdfPTable table = new PdfPTable(5);
                            table.setTotalWidth(300f);
                            table.setHorizontalAlignment(Element.ALIGN_CENTER);

                            PdfPCell cell1 = new PdfPCell(new Phrase("STT", boldFont));
                            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell1.setCellEvent(new AdapterOptionPayment.DottedCell());
                            cell1.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell1);

                            PdfPCell cell2 = new PdfPCell(new Phrase("Ten san pham", boldFont));
                            cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell2.setCellEvent(new AdapterOptionPayment.DottedCell());
                            cell2.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell2);

                            PdfPCell cell3 = new PdfPCell(new Phrase("Gia 1 cai", boldFont));
                            cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            cell3.setCellEvent(new AdapterOptionPayment.DottedCell());
                            cell3.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell3);

                            PdfPCell cell4 = new PdfPCell(new Phrase("So luong", boldFont));
                            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell4.setCellEvent(new AdapterOptionPayment.DottedCell());
                            cell4.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell4);

                            PdfPCell cell5 = new PdfPCell(new Phrase("Tong", boldFont));
                            cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            cell5.setCellEvent(new AdapterOptionPayment.DottedCell());
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
                            cell1.setCellEvent(new AdapterOptionPayment.DottedCell());
                            cell1.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell1);

                            cell2 = new PdfPCell(new Phrase(""));
                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell2.setCellEvent(new AdapterOptionPayment.DottedCell());
                            cell2.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell2);

                            cell3 = new PdfPCell(new Phrase(""));
                            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell3.setCellEvent(new AdapterOptionPayment.DottedCell());
                            cell3.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell3);

                            cell4 = new PdfPCell(new Phrase("Tong cong: ", boldFont));
                            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell4.setCellEvent(new AdapterOptionPayment.DottedCell());
                            cell4.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell4);

                            cell5 = new PdfPCell(new Phrase(df.format(gioHang.tongTien())+" đ", boldFont));
                            cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            cell5.setCellEvent(new AdapterOptionPayment.DottedCell());
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
                                    Toast.makeText(getApplicationContext(), "PDF Failed", Toast.LENGTH_LONG).show();
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
                                        SharedPreferences sharedPreferences = getSharedPreferences("shareGioHang", Context.MODE_PRIVATE);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, PayPalService.class));
    }


    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

}
