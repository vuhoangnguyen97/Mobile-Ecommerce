package com.example.legia.mobileweb.TouchID;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.legia.mobileweb.AdapterSanPham.SanPhamAdapterNew;
import com.example.legia.mobileweb.CheckInternet.Utils;
import com.example.legia.mobileweb.DAO.themVaoGioHang;
import com.example.legia.mobileweb.PaymentAPI.onePayPayment;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {


    private Context context;


    // Constructor
    public FingerprintHandler(Context mContext) {
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


    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.", false);
    }

    //final SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Fingerprint Authentication succeeded.", true);
        //Intent i = new Intent(context, HomeActivity.class);

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


    public void update(String e, Boolean success){
        //TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
        //textView.setText(e);
        if(success){
           // textView.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
            Toast.makeText(context, "SUCCESS", Toast.LENGTH_LONG).show();
        }
    }
}