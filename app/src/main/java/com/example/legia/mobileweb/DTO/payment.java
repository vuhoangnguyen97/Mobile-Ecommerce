package com.example.legia.mobileweb.DTO;

import android.widget.ImageView;

public class payment {
    private int hinhPayment;
    private int maHinhThuc;

    public payment(int maHinhThuc, int hinhPayment) {
        this.maHinhThuc = maHinhThuc;
        this.hinhPayment = hinhPayment;
    }

    public int getMaHinhThuc() {
        return maHinhThuc;
    }

    public void setMaHinhThuc(int maHinhThuc) {
        this.maHinhThuc = maHinhThuc;
    }

    public int getHinhPayment() {
        return hinhPayment;
    }

    public void setHinhPayment(int hinhPayment) {
        this.hinhPayment = hinhPayment;
    }
}
