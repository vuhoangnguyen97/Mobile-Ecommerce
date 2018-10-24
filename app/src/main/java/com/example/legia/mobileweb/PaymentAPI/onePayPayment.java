package com.example.legia.mobileweb.PaymentAPI;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static android.content.Context.WIFI_SERVICE;

public class onePayPayment {
    private Context context;
    private final String MERCHANT_ID = "ONEPAY";
    private final String ACCESS_CODE = "D67342C2";
    private static  String secure_code = "A3EFDFABA8653DF2342E8DAC29B51AF0";
    private String vnp_Version = "2.0.0";
    private String vnp_Command = "pay";
    private String vnp_OrderInfo = "Hóa đơn";

    Random random = new Random();
    String vnp_TxnRef = String.valueOf(random.nextInt(8));

    WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
    String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());





    static final char[] HEX_TABLE = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static byte[] decodeHexArray = new byte[103];

    static {
        int i = 0;
        for (byte b : new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'}) {
            decodeHexArray[b] = (byte) i++;
        }
        decodeHexArray['a'] = decodeHexArray['A'];
        decodeHexArray['b'] = decodeHexArray['B'];
        decodeHexArray['c'] = decodeHexArray['C'];
        decodeHexArray['d'] = decodeHexArray['D'];
        decodeHexArray['e'] = decodeHexArray['E'];
        decodeHexArray['f'] = decodeHexArray['F'];
    }

    public static String hashAllFields(Map fields) {
        List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        StringBuffer buf = new StringBuffer();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0) && fieldName.indexOf("vpc_") == 0) {
                buf.append(fieldName + "=" + fieldValue);
                if (itr.hasNext()) {
                    buf.append('&');
                }
            }
        }
        byte[] mac = null;
        try {
            byte[] b = decodeHexa(secure_code.getBytes());
            SecretKey key = new SecretKeySpec(b, "HMACSHA256");
            Mac m = Mac.getInstance("HMACSHA256");
            m.init(key);
            m.update(buf.toString().getBytes("UTF-8"));
            mac = m.doFinal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String hashValue = hex(mac);
        return hashValue;

    }

    public static byte[] decodeHexa(byte[] data) throws Exception {
        if (data == null) {
            return null;
        }
        if (data.length % 2 != 0) {
            throw new Exception("Invalid data length:" + data.length);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte b1, b2;
        int i = 0;
        while (i < data.length) {
            b1 = decodeHexArray[data[i++]];
            b2 = decodeHexArray[data[i++]];
            out.write((b1 << 4) | b2);
        }
        out.flush();
        out.close();
        return out.toByteArray();
    }

    static String hex(byte[] input) {
        StringBuffer sb = new StringBuffer(input.length * 2);
        for (int i = 0; i < input.length; i++) {
            sb.append(HEX_TABLE[(input[i] >> 4) & 0xf]);
            sb.append(HEX_TABLE[input[i] & 0xf]);
        }
        return sb.toString();
    }

    void appendQueryFields(StringBuffer buf, Map fields) {

        List fieldNames = new ArrayList(fields.keySet());
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // append the URL parameters
                buf.append(URLEncoder.encode(fieldName));
                buf.append('=');
                buf.append(URLEncoder.encode(fieldValue));
            }
            if (itr.hasNext()) {
                buf.append('&');
            }

        }

    }



}
