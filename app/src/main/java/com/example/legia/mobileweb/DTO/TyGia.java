package com.example.legia.mobileweb.DTO;

public class TyGia {
    private String maTyGia;
    private double giaMua;
    private double giaBan;

    public TyGia() {

    }

    public TyGia(String maTyGia, double giaMua, double giaBan) {
        this.maTyGia = maTyGia;
        this.giaMua = giaMua;
        this.giaBan = giaBan;
    }

    public String getMaTyGia() {
        return maTyGia;
    }

    public void setMaTyGia(String maTyGia) {
        this.maTyGia = maTyGia;
    }

    public double getGiaMua() {
        return giaMua;
    }

    public void setGiaMua(double giaMua) {
        this.giaMua = giaMua;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }
}
