package com.example.legia.mobileweb;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sdsmdg.tastytoast.TastyToast;

public class scancode extends AppCompatActivity {
    Button btnQRCode, btnBarcode;
    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scancode);
        btnBarcode = findViewById(R.id.btnBarcode);
        btnQRCode = findViewById(R.id.btnQRCode);


        btnQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan = new IntentIntegrator(scancode.this);
                qrScan.initiateScan();
            }
        });

        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan = new IntentIntegrator(scancode.this);
                qrScan.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            // show result
            final String stringResult = scanningResult.getContents();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Kết quả");
            builder.setNegativeButton("Thử lại", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            builder.setPositiveButton("Đến url", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(URLUtil.isValidUrl(stringResult)){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringResult));
                        startActivity(browserIntent);
                    }
                    else {
                        TastyToast.makeText(scancode.this, "Kết quả không phải đường dẫn web", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                    }

                }
            });
            builder.setMessage(stringResult);
            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Không nhận được kết quả", Toast.LENGTH_SHORT);
            toast.show();

        }
    }
}
